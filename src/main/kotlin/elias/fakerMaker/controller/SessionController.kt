package elias.fakerMaker.controller

import elias.fakerMaker.dto.SchemaRequestDto
import elias.fakerMaker.dto.SchemaResponseDto
import elias.fakerMaker.mapper.SchemaMapper
import kotlinx.serialization.json.Json
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/faker")
class SessionController {
    private val schemaMapper = SchemaMapper()

    @PostMapping("/schema")
    fun getFakeData(@RequestBody schemaJson : SchemaRequestDto<Any>): SchemaResponseDto {
        val schemaRequestDto = schemaMapper.toSchemaRequestDto(schemaJson)
        val fakerSchemaDto =  schemaRequestDto
        return SchemaResponseDto()
    }

}