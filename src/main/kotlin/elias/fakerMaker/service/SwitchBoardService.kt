package elias.fakerMaker.service

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.generator.AmericaGenerator
import elias.fakerMaker.generator.EmailGenerator
import elias.fakerMaker.generator.NameGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import mu.KotlinLogging
import net.datafaker.Faker
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.roundToInt
import kotlin.random.Random

@Service
class SwitchBoardService {
    private val logger = KotlinLogging.logger {}

    // Cached generators for frequently used data types
    private val cachedGenerators = ConcurrentHashMap<MakerEnum, (List<DataTableItem>) -> DataTableItem>().apply {
        this[MakerEnum.STATE] = { AmericaGenerator.state() }
        this[MakerEnum.CITY] = { state -> AmericaGenerator.city(state) }
        this[MakerEnum.ZIP] = { state -> AmericaGenerator.zip(state) }
    }

    /**
     * Generates a specified number of data rows with given faker and maker configurations
     * @param armySize Number of rows to generate
     * @param fakers List of faker configurations
     * @param makers List of maker configurations
     * @return Flow of generated data rows
     */
    fun buildMeAnArmy(armySize: Int, fakers: List<FakerEnum>, makers: List<MakerEnum>): Flow<List<DataTableItem>> = channelFlow {
        val startTime = System.nanoTime()
        val performanceTracker = PerformanceTracker(armySize, makers.size, fakers, makers)

        // Sort makers to maintain consistent state generation
        val sortedMakers = makers.sortedBy { maker ->
            when {
                maker.name.contains("NAME") -> 0
                maker.name == "STATE" -> 1
                maker.name == "CITY" -> 2
                maker.name == "ZIP" -> 3
                maker.name == "PHONE" -> 4
                else -> 5
            }
        }

        coroutineScope {
            // Generate rows concurrently
            val generatedRows = (0 until armySize).map {
                async(Dispatchers.Default) {
                    generateRow(sortedMakers, fakers)
                }
            }.awaitAll()

            // Send rows to channel
            generatedRows.forEach { send(it) }
        }

        // Log performance metrics
        performanceTracker.logPerformance(startTime)
    }

    /**
     * Generates a single row of data
     * @param makers Sorted list of makers to generate data for
     * @param fakers List of faker configurations
     * @return List of generated DataTableItems
     */
    private fun generateRow(makers: List<MakerEnum>, fakers: List<FakerEnum>): List<DataTableItem> {
        val currentRowState = mutableListOf<DataTableItem>()

        return makers.map { maker ->
            val item = when (maker) {
                MakerEnum.NAME_FIRST -> NameGenerator.firstName(fakers)
                MakerEnum.NAME_LAST -> NameGenerator.lastName(fakers)
                MakerEnum.NAME_COMPANY -> NameGenerator.companyName(fakers)
                MakerEnum.STATE -> cachedGenerators[MakerEnum.STATE]?.invoke(currentRowState) ?: AmericaGenerator.state()
                MakerEnum.CITY -> cachedGenerators[MakerEnum.CITY]?.invoke(currentRowState) ?: AmericaGenerator.city(currentRowState)
                MakerEnum.ZIP -> cachedGenerators[MakerEnum.ZIP]?.invoke(currentRowState) ?: AmericaGenerator.zip(currentRowState)
                MakerEnum.PHONE -> AmericaGenerator.phone(currentRowState)
                MakerEnum.ADDRESS -> AmericaGenerator.address()
                MakerEnum.ADDRESS_2 -> AmericaGenerator.address2()
                MakerEnum.EMAIL -> EmailGenerator.email(currentRowState)
                MakerEnum.NUMBER_PRICE -> DataTableItem()
                MakerEnum.NUMBER_REGULAR -> DataTableItem()
                MakerEnum.DATE -> DataTableItem()
                MakerEnum.BOOLEAN -> DataTableItem()
                MakerEnum.ID -> DataTableItem()
                MakerEnum.CREDIT_CARD_NUMBER -> DataTableItem()
            }
            currentRowState.add(item)
            item
        }
    }

    /**
     * Performance tracking and logging utility
     * @param totalRows Total number of rows generated
     * @param makerCount Number of makers used
     * @param fakers List of faker configurations
     * @param makers List of maker configurations
     */
    private inner class PerformanceTracker(
        private val totalRows: Int,
        private val makerCount: Int,
        private val fakers: List<FakerEnum>,
        private val makers: List<MakerEnum>
    ) {
        private val mutex = Mutex()

        /**
         * Log performance metrics
         * @param startTime Nano time when generation started
         */
        fun logPerformance(startTime: Long) {
            val totalTime = (System.nanoTime() - startTime) / 1_000_000 // Convert to milliseconds

            // Use lazy logging to reduce overhead
            logger.info {
                buildPerformanceLog(totalRows, makerCount, totalTime, fakers, makers)
            }
        }

        /**
         * Construct performance log string
         * @param armySize Total number of rows generated
         * @param makers Number of makers used
         * @param totalTime Total generation time in milliseconds
         * @param fakers List of faker configurations
         * @param makers List of maker configurations
         * @return Formatted performance log string
         */
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