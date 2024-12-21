package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import net.datafaker.Faker
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class SwitchBoardService {
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
                else -> 4
            }
        }
        val dataTableRows = mutableListOf<MutableList<DataTableItem>>()
        for (i in 0 until armySize ) {
            val dataTableRow = mutableListOf<DataTableItem>()
            // gotta have a value for each maker in the row
            for (maker in sortedMakers) {
                when (maker) {
                    MakerEnum.NAME_FIRST -> dataTableRow.add(NameGenerator.firstName(fakers))
                    MakerEnum.NAME_LAST -> dataTableRow.add(NameGenerator.lastName(fakers))
                    MakerEnum.NAME_COMPANY -> dataTableRow.add(NameGenerator.companyName(fakers))
                    MakerEnum.EMAIL -> dataTableRow.add(EmailGenerator.generateRandomEmail(dataTableRow))
                    MakerEnum.PHONE -> dataTableRow.add(DataTableItem())
                    MakerEnum.ADDRESS -> dataTableRow.add(DataTableItem())
                    MakerEnum.ADDRESS_2 -> dataTableRow.add(DataTableItem())
                    MakerEnum.STATE -> dataTableRow.add(AmericaGenerator.state())
                    MakerEnum.CITY -> dataTableRow.add(AmericaGenerator.city(dataTableRow))
                    MakerEnum.ZIP -> dataTableRow.add(AmericaGenerator.zip(dataTableRow))
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
        return dataTableRows
    }
}
