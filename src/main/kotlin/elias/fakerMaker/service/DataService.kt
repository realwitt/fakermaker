package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import net.datafaker.Faker
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class DataService {
    val rand = Random
    val dataFaker = Faker()
    val nameGenerator = NameGenerator()
    val emailGenerator = EmailGenerator()
    val americaGenerator = AmericaGenerator()

    // given faker and maker arrays, make n rows of data
    fun buildMeAnArmy(armySize: Int, fakers: List<FakerEnum>, makers: List<MakerEnum>): MutableList<MutableList<DataTableItem>> {
        // sort makers by NAME first so we can use the names to riff off of for emails
        val sortedMakers = makers.sortedBy { !it.name.contains("NAME") }
        val dataTableRows = mutableListOf<MutableList<DataTableItem>>()
        for (i in 0 until armySize ) {
            val dataTableRow = mutableListOf<DataTableItem>()
            // gotta have a value for each maker in the row
            for (maker in sortedMakers) {
                when (maker) {
                    MakerEnum.NAME_FIRST -> dataTableRow.add(nameGenerator.firstName(fakers))
                    MakerEnum.NAME_LAST -> dataTableRow.add(nameGenerator.lastName(fakers))
                    MakerEnum.NAME_COMPANY -> dataTableRow.add(nameGenerator.companyName(fakers))
                    MakerEnum.EMAIL -> dataTableRow.add(emailGenerator.generateRandomEmail(dataTableRow))
                    MakerEnum.PHONE -> dataTableRow.add(DataTableItem())
                    MakerEnum.ADDRESS -> dataTableRow.add(DataTableItem())
                    MakerEnum.ADDRESS_2 -> dataTableRow.add(DataTableItem())
                    MakerEnum.STATE -> dataTableRow.add(americaGenerator.state())
                    MakerEnum.CITY -> dataTableRow.add(DataTableItem())
                    MakerEnum.ZIP -> dataTableRow.add(DataTableItem())
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
