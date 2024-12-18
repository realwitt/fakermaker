package elias.fakerMaker.controller

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.generator.DataService
import elias.fakerMaker.utils.RandomEnum.Companion.randomEnums
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

val dataService = DataService()

@RestController
@RequestMapping("/api/faker")
class DataController {

    @PostMapping("/schema")
    fun getFakeData(): List<List<DataTableItem>> {
        // todo: create the schema argument (which will determine the payload of the buildMeAnArmy method)

//        val fakers = randomEnums<FakerEnum>()
//        val makers = randomEnums<MakerEnum>()

        val fakers = listOf(FakerEnum.GRAVITY_FALLS, FakerEnum.KING_OF_THE_HILL, FakerEnum.HARRY_POTTER)
        val makers = listOf(MakerEnum.EMAIL, MakerEnum.NAME_FIRST, MakerEnum.NAME_LAST, MakerEnum.NAME_COMPANY)

        // WIP... currently returns a random datatable payload
        return dataService.buildMeAnArmy(40, fakers, makers)
    }

}
