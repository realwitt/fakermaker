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

    // given faker and maker arrays, make n rows of data
    fun buildMeAnArmy(armySize: Int, fakers: List<FakerEnum>, makers: List<MakerEnum>): MutableList<MutableList<DataTableItem>> {
        val dataTableRows = mutableListOf<MutableList<DataTableItem>>()
        for (i in 0 until armySize ) {
            val dataTableRow = mutableListOf<DataTableItem>()
            // gotta have a value for each maker in the row
            for (maker in makers) {
                when (maker) {
                    MakerEnum.NAME_FIRST -> dataTableRow.add(nameGenerator.generateRandomFirstName(fakers))
                    MakerEnum.NAME_LAST -> dataTableRow.add(nameGenerator.generateRandomLastName(fakers))
                    MakerEnum.NAME_COMPANY -> dataTableRow.add(DataTableItem())
                    MakerEnum.EMAIL -> dataTableRow.add(DataTableItem())
                    MakerEnum.PHONE -> dataTableRow.add(DataTableItem())
                    MakerEnum.ADDRESS -> dataTableRow.add(DataTableItem())
                    MakerEnum.ADDRESS_2 -> dataTableRow.add(DataTableItem())
                    MakerEnum.STATE -> dataTableRow.add(DataTableItem())
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
