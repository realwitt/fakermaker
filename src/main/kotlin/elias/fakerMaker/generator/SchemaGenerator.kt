package elias.fakerMaker.generator

import elias.fakerMaker.dto.SchemaRequestDto
import net.datafaker.Faker

class SchemaGenerator {
    val faker: Faker = Faker()

    fun generate() : SchemaRequestDto<Any> {
        val schemaRequestDto: SchemaRequestDto<Any> = SchemaRequestDto()
        schemaRequestDto.sessionID = faker.internet().uuid()
        schemaRequestDto.fakers = emptyList()
        schemaRequestDto.makers = emptyList()
//        val fakers: List<FakerLibraryEnums>,
//        val makers: List<Maker<out T>>,
//        val sessionID: String
        return schemaRequestDto
    }
}