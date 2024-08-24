package elias.fakerMaker.mapper

import elias.fakerMaker.dto.SchemaRequestDto

class SchemaMapper() {
    // use kotlinx.serialization to parse the json payload into our requestDto
    fun toSchemaRequestDto(schemaJson : SchemaRequestDto<Any>) {
        println(schemaJson)
    }
}