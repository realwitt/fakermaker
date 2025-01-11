package elias.fakerMaker.controller

import elias.fakerMaker.types.DataTableItem
import elias.fakerMaker.types.FakerMakers
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.types.model.Schema
import elias.fakerMaker.service.SwitchBoardService
import elias.fakerMaker.types.dto.DataTableDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.withContext
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
    suspend fun populateDataTable(
        @RequestBody schema: Schema
    ): DataTableDto = withContext(Dispatchers.IO) {
        val rowCount = 200
        println("parsed schema looks like:")
        println(schema)
        switchBoardService.buildDataTable(rowCount, schema)
    }

    @PostMapping("/csv/{count}")
    suspend fun getFakeData(
        @PathVariable count: Int,
        @RequestBody schema: Schema
    ): ResponseEntity<String> = withContext(Dispatchers.IO) {
        println("parsed schema looks like:")
        println(schema)
        val csvData = switchBoardService.buildCsv(
            count,
            schema
        )

        ResponseEntity.ok()
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