package elias.fakerMaker.service

import de.siegmar.fastcsv.writer.CsvWriter
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.generator.AmericaGenerator
import elias.fakerMaker.generator.EmailGenerator
import elias.fakerMaker.generator.NameGenerator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.StringWriter
import kotlin.coroutines.CoroutineContext

@Service
class SwitchBoardService(
    private val workerContext: CoroutineContext = Dispatchers.Default
) {
    private val logger = KotlinLogging.logger {}

    // Precompute maker order for better performance
    private val makerOrder = mapOf(
        "NAME" to 0,
        "STATE" to 1,
        "CITY" to 2,
        "ZIP" to 3,
        "PHONE" to 4
    )

    // Buffer size for CSV writing
    val CSV_BUFFER_SIZE = 32768  // 32KB, internal buffer size used by FastCSV for writing to disk/files
    private val BATCH_SIZE = 5000 // how many rows we process together in memory before writing

    private fun initGenerators(fakers: List<FakerEnum>): Map<MakerEnum, (List<DataTableItem>) -> DataTableItem> = mapOf(
        MakerEnum.STATE to { _ -> AmericaGenerator.state() },
        MakerEnum.CITY to { items -> AmericaGenerator.city(items) },
        MakerEnum.ZIP to { items -> AmericaGenerator.zip(items) },
        MakerEnum.PHONE to { items -> AmericaGenerator.phone(items) },
        MakerEnum.EMAIL to { items -> EmailGenerator.email(items) },
        MakerEnum.NAME_FIRST to { _ -> NameGenerator.firstName(fakers) },
        MakerEnum.NAME_LAST to { _ -> NameGenerator.lastName(fakers) },
        MakerEnum.NAME_COMPANY to { _ -> NameGenerator.companyName(fakers) },
        MakerEnum.ADDRESS to { _ -> AmericaGenerator.address() },
        MakerEnum.ADDRESS_2 to { _ -> AmericaGenerator.address2() }
    )

    fun buildDataTable(armySize: Int, fakers: List<FakerEnum>, makers: List<MakerEnum>): Flow<List<DataTableItem>> = channelFlow {
        val startTime = System.nanoTime()
        val sortedMakers = makers.sortedBy { maker ->
            makerOrder.entries.find { maker.name.contains(it.key) }?.value ?: Int.MAX_VALUE
        }
        val performanceTracker = PerformanceTracker(armySize, makers.size, fakers, makers)
        val generators = initGenerators(fakers)

        try {
            withContext(workerContext) {
                (0 until armySize).chunked(BATCH_SIZE).forEach { chunk ->
                    val rows = chunk.map {
                        async { generateRow(sortedMakers, generators) { it } }
                    }.awaitAll()
                    rows.forEach { row -> send(row) }
                }
            }
        } finally {
            NameGenerator.clearCache()
            EmailGenerator.clearCache()
            performanceTracker.logPerformance(startTime)
        }
    }

    suspend fun buildCsv(armySize: Int, makers: List<MakerEnum>, fakers: List<FakerEnum>): String {
        val startTime = System.nanoTime()
        val sortedMakers = makers.sortedBy { maker ->
            makerOrder.entries.find { maker.name.contains(it.key) }?.value ?: Int.MAX_VALUE
        }
        val performanceTracker = PerformanceTracker(armySize, makers.size, fakers, makers)
        val generators = initGenerators(fakers)

        return try {
            StringWriter(CSV_BUFFER_SIZE).use { writer ->
                CsvWriter.builder()
                    .bufferSize(CSV_BUFFER_SIZE)
                    .build(writer)
                    .use { csv ->
                        // Write header
                        csv.writeRecord(sortedMakers.map { it.name })

                        // Generate and write data in batches
                        withContext(workerContext) {
                            (0 until armySize).chunked(BATCH_SIZE).forEach { chunk ->
                                val rows = chunk.map {
                                    async {
                                        generateRow(sortedMakers, generators) { item -> item.derivedValue }
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
        makers: List<MakerEnum>,
        generators: Map<MakerEnum, (List<DataTableItem>) -> DataTableItem>,
        transform: (DataTableItem) -> T
    ): List<T> {
        // Preallocate both arrays for better memory efficiency
        val currentRowState = ArrayList<DataTableItem>(makers.size)
        val result = ArrayList<T>(makers.size)

        for (maker in makers) {
            // Avoid map lookup for each iteration using computeIfAbsent pattern
            val generator = generators[maker] ?: EMPTY_GENERATOR
            val item = generator(currentRowState)
            currentRowState.add(item)
            result.add(transform(item))
        }
        return result
    }

    // Cache the empty generator
    private val EMPTY_GENERATOR: (List<DataTableItem>) -> DataTableItem = { DataTableItem() }

//    private fun <T> generateRow(
//        makers: List<MakerEnum>,
//        generators: Map<MakerEnum, (List<DataTableItem>) -> DataTableItem>,
//        transform: (DataTableItem) -> T
//    ): List<T> {
//        val currentRowState = ArrayList<DataTableItem>(makers.size)
//        return makers.map { maker ->
//            val generator = generators[maker] ?: { _: List<DataTableItem> -> DataTableItem() }
//            val item = generator(currentRowState)
//            currentRowState.add(item)
//            transform(item)
//        }
//    }

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