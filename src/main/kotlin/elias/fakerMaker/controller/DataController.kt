package elias.fakerMaker.controller

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.generator.SwitchBoardService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

val switchBoardService = SwitchBoardService()

@RestController
@RequestMapping("/api/faker")
class DataController {

    @PostMapping("/schema")
    fun getFakeData(): List<List<DataTableItem>> {
        // todo: create the schema argument (which will determine the payload of the buildMeAnArmy method)

//        val fakers = FakerEnum.entries
//            .shuffled()
//            .take((1..FakerEnum.entries.size).random())
//        val makers = MakerEnum.entries
//            .shuffled()
//            .take((1..MakerEnum.entries.size).random())


        val fakers = listOf(FakerEnum.GRAVITY_FALLS, FakerEnum.KING_OF_THE_HILL, FakerEnum.HARRY_POTTER)
//        val makers = listOf(MakerEnum.EMAIL, MakerEnum.NAME_FIRST, MakerEnum.NAME_LAST, MakerEnum.NAME_COMPANY)
        val makers = listOf(MakerEnum.CITY, MakerEnum.ZIP, MakerEnum.STATE)

        // WIP... currently returns a random datatable payload
        return switchBoardService.buildMeAnArmy(200, fakers, makers)
    }

}
