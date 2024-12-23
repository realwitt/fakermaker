package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import mu.KotlinLogging
import net.datafaker.Faker
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class SwitchBoardService {
    private val logger = KotlinLogging.logger {}
    val rand = Random
    val dataFaker = Faker()

    // given faker and maker arrays, make n rows of data
    fun buildMeAnArmy(armySize: Int, fakers: List<FakerEnum>, makers: List<MakerEnum>): MutableList<MutableList<DataTableItem>> {
        // sort by NAME first so we can use the names to riff off of for emails
        // sort by STATE -> CITY -> ZIP bc it follows the order of our json data structure
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
        val dataTableRows = mutableListOf<MutableList<DataTableItem>>()
        val startTime = System.currentTimeMillis()
        for (i in 0 until armySize ) {
            val dataTableRow = mutableListOf<DataTableItem>()
            // gotta have a value for each maker in the row

            for (maker in sortedMakers) {
                when (maker) {
                    MakerEnum.NAME_FIRST -> dataTableRow.add(NameGenerator.firstName(fakers))
                    MakerEnum.NAME_LAST -> dataTableRow.add(NameGenerator.lastName(fakers))
                    MakerEnum.NAME_COMPANY -> dataTableRow.add(NameGenerator.companyName(fakers))
                    MakerEnum.STATE -> dataTableRow.add(AmericaGenerator.state())
                    MakerEnum.CITY -> dataTableRow.add(AmericaGenerator.city(dataTableRow))
                    MakerEnum.ZIP -> dataTableRow.add(AmericaGenerator.zip(dataTableRow))
                    MakerEnum.PHONE -> dataTableRow.add(AmericaGenerator.phone(dataTableRow))
                    MakerEnum.ADDRESS -> dataTableRow.add(AmericaGenerator.address())
                    MakerEnum.ADDRESS_2 -> dataTableRow.add(AmericaGenerator.address2())
                    MakerEnum.EMAIL -> dataTableRow.add(EmailGenerator.email(dataTableRow))
                    MakerEnum.NUMBER_PRICE -> dataTableRow.add(DataTableItem())
                    MakerEnum.NUMBER_REGULAR -> dataTableRow.add(DataTableItem())
                    MakerEnum.DATE -> dataTableRow.add(DataTableItem())
                    MakerEnum.BOOLEAN -> dataTableRow.add(DataTableItem())
                    MakerEnum.ID -> dataTableRow.add(DataTableItem())
                    MakerEnum.CREDIT_CARD_NUMBER -> dataTableRow.add(DataTableItem())
                }
            }
            dataTableRows.add(dataTableRow)
        }
        val endTime = System.currentTimeMillis()
        val totalTime = endTime - startTime

        logger.info { """${"\n"}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
${armySize.toString().reversed().chunked(3).joinToString(",").reversed()} rows using ${makers.size} makers

${let {
            val seconds = totalTime / 1000.0
            val formatted = String.format("%.2f", seconds)
            if (formatted.toDouble() == seconds) "" else "~"
        }}${String.format("%.2f", totalTime / 1000.0)} seconds total time ($totalTime ms)
${let {
            val avgSeconds = totalTime.toFloat() / (1000.0 * armySize)
            val avgMillis = totalTime.toDouble() / armySize
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
""".trimIndent()}

        return dataTableRows
    }

}

