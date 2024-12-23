package elias.fakerMaker.controller

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.service.SwitchBoardService
import kotlinx.coroutines.reactor.asFlux
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

val switchBoardService = SwitchBoardService()

@RestController
@RequestMapping("/api/faker")
class DataController(
    private val switchBoardService: SwitchBoardService
) {
    @PostMapping("/schema/{count}")
    fun getFakeData(@PathVariable count: Int) = switchBoardService
        .buildMeAnArmy(
            count,
            listOf(FakerEnum.GRAVITY_FALLS, FakerEnum.KING_OF_THE_HILL, FakerEnum.HARRY_POTTER),
            listOf(MakerEnum.ADDRESS, MakerEnum.PHONE, MakerEnum.NAME_FIRST)
        )
        .asFlux()

    // For future use
    private fun getRandomFakers(): List<FakerEnum> {
        return FakerEnum.entries
            .shuffled()
            .take((1..FakerEnum.entries.size).random())
    }

    private fun getRandomMakers(): List<MakerEnum> {
        return MakerEnum.entries
            .shuffled()
            .take((1..MakerEnum.entries.size).random())
    }
}