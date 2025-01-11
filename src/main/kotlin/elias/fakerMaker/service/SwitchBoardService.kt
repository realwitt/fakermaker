package elias.fakerMaker.service

import de.siegmar.fastcsv.writer.CsvWriter
import elias.fakerMaker.types.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.generator.*
import elias.fakerMaker.types.MakerConfig
import elias.fakerMaker.types.dto.DataTableDto
import elias.fakerMaker.types.model.Schema
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.StringWriter
import java.time.ZoneOffset
import kotlin.coroutines.CoroutineContext

@Service
class SwitchBoardService(
    private val workerContext: CoroutineContext = Dispatchers.Default
) {
    private val logger = KotlinLogging.logger {}

    // Precompute maker order for better performance
    private val makerOrder = mapOf(
        MakerEnum.NAME_FIRST to 0,
        MakerEnum.NAME_LAST to 1,
        MakerEnum.NAME_COMPANY to 2,
        MakerEnum.STATE to 3,
        MakerEnum.CITY to 4,
        MakerEnum.ZIP to 5,
        MakerEnum.PHONE to 6,
        MakerEnum.CREDIT_CARD_NUMBER to 7,
        MakerEnum.CREDIT_CARD_CVV to 8,
    )


    // Buffer size for CSV writing
    val CSV_BUFFER_SIZE = 32768  // 32KB, internal buffer size used by FastCSV for writing to disk/files
    private val BATCH_SIZE = 5000 // how many rows we process together in memory before writing

    private fun initGenerators(
        fakers: List<FakerEnum>,
        makerConfigs: List<MakerConfig>
    ): List<(List<DataTableItem>) -> DataTableItem> = makerConfigs.map { config ->
        when (config.makerEnum) {
            MakerEnum.NUMBER_PRICE -> { _ ->
                NumberGenerator.priceRange(
                    min = config.priceRange?.first,  // Pass Double directly
                    max = config.priceRange?.second   // Pass Double directly
                )
            }
            MakerEnum.NUMBER_REGULAR -> { _ ->
                NumberGenerator.numRange(
                    config.numberRange?.first,
                    config.numberRange?.second
                )
            }
            MakerEnum.DATE -> { _ ->
                val startDate = config.dateRange?.first?.atStartOfDay()?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
                val endDate = config.dateRange?.second?.atStartOfDay()?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
                DateGenerator.dateRange(startDate, endDate)
            }
            MakerEnum.STATE -> { _ -> AmericaGenerator.state() }
            MakerEnum.CITY -> { items -> AmericaGenerator.city(items) }
            MakerEnum.ZIP -> { items -> AmericaGenerator.zip(items) }
            MakerEnum.PHONE -> { items -> AmericaGenerator.phone(items) }
            MakerEnum.EMAIL -> { items -> EmailGenerator.email(items) }
            MakerEnum.NAME_FIRST -> { _ -> NameGenerator.firstName(fakers) }
            MakerEnum.NAME_LAST -> { items -> NameGenerator.lastName(items, fakers) }
            MakerEnum.NAME_COMPANY -> { items -> NameGenerator.companyName(items, fakers) }
            MakerEnum.ADDRESS -> { _ -> AmericaGenerator.address() }
            MakerEnum.ADDRESS_2 -> { _ -> AmericaGenerator.address2() }
            MakerEnum.CREDIT_CARD_NUMBER -> { _ -> CreditCardGenerator.creditCard() }
                // todo add a parameter so we can explicitly tell it what type of credit cards we want
            MakerEnum.CREDIT_CARD_CVV -> { items -> CreditCardGenerator.cvv(items) }
                // todo add a parameter so we can explicitly tell it what type of IDs we want
            MakerEnum.ID -> { _ -> IdGenerator.id() }
            MakerEnum.BOOLEAN -> { _ -> BooleanGenerator.bool() }
        }
    }

    suspend fun buildDataTable(rowCount: Int, schema: Schema): DataTableDto {
        val startTime = System.nanoTime()
        val fakers = schema.fakers
        val makers = schema.makers.map { it.makerEnum }

        // Sort the maker configs
        val sortedMakerConfigs = schema.makers.sortedBy { maker ->
            makerOrder[maker.makerEnum] ?: Int.MAX_VALUE
        }

        val performanceTracker = PerformanceTracker(rowCount, makers.size, fakers, makers)
        val generators = initGenerators(fakers, sortedMakerConfigs)

        try {
            return withContext(workerContext) {
                // Create rows in batches
                val allRows = mutableListOf<DataTableItem>()

                (0 until rowCount).chunked(BATCH_SIZE).forEach { chunk ->
                    val rows = chunk.map {
                        async {
                            generateRow(sortedMakerConfigs, generators) { it }
                                .first() // Since we're only generating one item at a time now
                        }
                    }.awaitAll()
                    allRows.addAll(rows)
                }

                // Return single DataTableDto with all data
                DataTableDto(
                    headers = sortedMakerConfigs.map { it.nickname },
                    data = allRows
                )
            }
        } finally {
            NameGenerator.clearCache()
            EmailGenerator.clearCache()
            performanceTracker.logPerformance(startTime)
        }
    }
    suspend fun buildCsv(rowCount: Int, schema: Schema): String {
        val startTime = System.nanoTime()

        // Sort the makerConfigs using the makerOrder map
        val sortedMakerConfigs = schema.makers.sortedBy { config ->
            makerOrder[config.makerEnum] ?: Int.MAX_VALUE
        }

        val performanceTracker = PerformanceTracker(rowCount, schema.makers.size, schema.fakers, schema.makers.map { it.makerEnum })
        val generators = initGenerators(schema.fakers, sortedMakerConfigs)

        return try {
            StringWriter(CSV_BUFFER_SIZE).use { writer ->
                CsvWriter.builder()
                    .bufferSize(CSV_BUFFER_SIZE)
                    .build(writer)
                    .use { csv ->
                        // Write header - using nickName from MakerConfig instead of enum name
                        csv.writeRecord(sortedMakerConfigs.map { it.nickname })

                        // Generate and write data in batches
                        withContext(workerContext) {
                            (0 until rowCount).chunked(BATCH_SIZE).forEach { chunk ->
                                val rows = chunk.map {
                                    async {
                                        generateRow(sortedMakerConfigs, generators) { item -> item.derivedValue }
                                    }
                                }.awaitAll()

                                // Write batch of records efficiently
                                rows.forEach { row ->
                                    csv.writeRecord(*row.toTypedArray())
                                }
                            }
                        }
                    }
                writer.toString()
            }
        } finally {
            NameGenerator.clearCache()
            EmailGenerator.clearCache()
            performanceTracker.logPerformance(startTime)
        }
    }

    private fun <T> generateRow(
        makers: List<MakerConfig>,
        generators: List<(List<DataTableItem>) -> DataTableItem>,
        transform: (DataTableItem) -> T
    ): List<T> {
        val currentRowState = ArrayList<DataTableItem>(makers.size)
        val result = ArrayList<T>(makers.size)

        generators.forEach { generator ->
            val item = generator(currentRowState)
            currentRowState.add(item)
            result.add(transform(item))
        }
        return result
    }

    private inner class PerformanceTracker(
        private val totalRows: Int,
        private val makerCount: Int,
        private val fakers: List<FakerEnum>,
        private val makers: List<MakerEnum>
    ) {
        fun logPerformance(startTime: Long) {
            val totalTime = (System.nanoTime() - startTime) / 1_000_000 // Convert to milliseconds

            // Use lazy logging to reduce overhead
            logger.info {
                buildPerformanceLog(totalRows, makerCount, totalTime, fakers, makers)
            }
        }

        private fun buildPerformanceLog(
            armySize: Int,
            makerCount: Int,
            totalTime: Long,
            fakers: List<FakerEnum>,
            makers: List<MakerEnum>
        ): String {
            val seconds = totalTime / 1000.0
            val avgMillis = totalTime.toDouble() / armySize

            return """
${"\n"}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
${armySize.toString().reversed().chunked(3).joinToString(",").reversed()} rows using $makerCount makers

${let {
                val formatted = String.format("%.2f", seconds)
                if (formatted.toDouble() == seconds) "" else "~"
            }}${String.format("%.2f", totalTime / 1000.0)} seconds total time ($totalTime ms)
${let {
                val avgSeconds = totalTime.toFloat() / (1000.0 * armySize)
                val formatted = String.format("%.2f", avgSeconds)
                when {
                    avgSeconds < 0.01 -> "<0.01 second average row creation time (${String.format("%.3f", avgMillis)} ms)"
                    formatted.toDouble() == avgSeconds -> "$formatted second${if (avgSeconds > 1) "s" else ""} average row creation time (${String.format("%.3f", avgMillis)} ms)"
                    else -> "~${formatted} second${if (avgSeconds > 1) "s" else ""} average row creation time (${String.format("%.3f", avgMillis)} ms)"
                }
            }}
Fakers:${"\n"}${fakers.mapIndexed { index, faker ->
                "\t${index + 1}. ${faker.prettyName}${if (index != fakers.lastIndex) "\n" else ""}"
            }.joinToString("")}
Makers:${"\n"}${makers.mapIndexed { index, maker ->
                "\t${index + 1}. ${maker.prettyName}${if (index != makers.lastIndex) "\n" else ""}"
            }.joinToString("")}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
""".trimIndent()
        }
    }
}