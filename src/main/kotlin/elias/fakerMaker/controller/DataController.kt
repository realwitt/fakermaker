package elias.fakerMaker.controller

import elias.fakerMaker.types.DataTableItem
import elias.fakerMaker.types.FakerMakers
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.types.model.Schema
import elias.fakerMaker.service.SwitchBoardService
import elias.fakerMaker.types.dto.DataTableDto
import kotlinx.coroutines.reactor.asFlux
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/fakermaker")
@CrossOrigin(origins = ["http://localhost:4321"])
class DataController(
    private val switchBoardService: SwitchBoardService
) {
    @PostMapping("/dataTable/")
    fun populateDataTable(
        // should we use a pageable object here?
        // we could just default to 200 rows of data then page client side
        @RequestBody schema: Schema
    ): Flux<DataTableDto> {
        val rowCount = 200
        println("parsed schema looks like:")
        println(schema)
        return switchBoardService
            .buildDataTable(
                rowCount,
                schema
            )
            .asFlux()
    }

    @PostMapping("/csv/{count}")
    suspend fun getFakeData(
        @PathVariable count: Int,
        @RequestBody schema: Schema
    ): ResponseEntity<String> {
        println("parsed schema looks like:")
        println(schema)
        val csvData = switchBoardService.buildCsv(
            count,
            schema
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fakeData.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(csvData)
    }

    @GetMapping("/fakermakers")
    fun getSchema(): FakerMakers {
        val fakers = FakerEnum.entries.map { faker -> faker.prettyName }
        val makers = MakerEnum.entries.map { maker -> maker.prettyName }

        return FakerMakers(fakers, makers)
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