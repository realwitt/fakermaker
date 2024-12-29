package elias.fakerMaker.controller

import elias.fakerMaker.types.dto.DataTableItem
import elias.fakerMaker.types.SchemaOptions
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.types.model.Schema
import elias.fakerMaker.service.SwitchBoardService
import kotlinx.coroutines.reactor.asFlux
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/fakermaker")
class DataController(
    private val switchBoardService: SwitchBoardService
) {
    @PostMapping("/dataTable/{count}")
    fun populateDataTable(
        @PathVariable count: Int,
        @RequestBody schema: Schema
    ): Flux<List<DataTableItem>> {
        println("parsed schema looks like:")
        println(schema)
        return switchBoardService
            .buildDataTable(
                count,
                FakerEnum.entries.toList(),
                MakerEnum.entries.toList()
            )
            .asFlux()
    }

    @PostMapping("/download/{count}")
    suspend fun getFakeData(
        @PathVariable count: Int,
        @RequestBody schema: Schema
    ): ResponseEntity<String> {
        println("parsed schema looks like:")
        println(schema)
        val csvData = switchBoardService.buildCsv(
            count,
            MakerEnum.entries.toList(),
            FakerEnum.entries.toList(),
//            listOf(MakerEnum.ADDRESS, MakerEnum.PHONE, MakerEnum.NAME_FIRST),
//            listOf(FakerEnum.GRAVITY_FALLS, FakerEnum.KING_OF_THE_HILL, FakerEnum.HARRY_POTTER),
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fakeData.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(csvData)
    }

    @GetMapping("/all-options")
    fun getSchema(): SchemaOptions {
        val fakers = FakerEnum.entries.map { faker -> faker.prettyName }
        val makers = MakerEnum.entries.map { maker -> maker.prettyName }

        return SchemaOptions(fakers, makers)
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