package elias.fakerMaker.controller

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.service.SwitchBoardService
import kotlinx.coroutines.reactor.asFlux
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fakermaker")
class DataController(
    private val switchBoardService: SwitchBoardService
) {
    @PostMapping("/dataTable/{count}")
    fun populateDataTable(@PathVariable count: Int) = switchBoardService
        // todo this will use a paging object later to return a default of maybe 20 records at a time
        .buildDataTable(
            count,
            listOf(FakerEnum.GRAVITY_FALLS, FakerEnum.KING_OF_THE_HILL, FakerEnum.HARRY_POTTER),
            listOf(MakerEnum.ADDRESS, MakerEnum.PHONE, MakerEnum.NAME_FIRST)
        )
        .asFlux()

    @PostMapping("/download/{count}")
    suspend fun getFakeData(@PathVariable count: Int) : ResponseEntity<String> {
        val csvData = switchBoardService.buildCsv(
            count,
            listOf(MakerEnum.ADDRESS, MakerEnum.PHONE, MakerEnum.NAME_FIRST),
            listOf(FakerEnum.GRAVITY_FALLS, FakerEnum.KING_OF_THE_HILL, FakerEnum.HARRY_POTTER),
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fakeData.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(csvData)
    }

    // for simulate random schemas
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