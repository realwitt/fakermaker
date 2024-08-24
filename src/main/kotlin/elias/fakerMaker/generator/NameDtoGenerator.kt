package elias.fakerMaker.generator

import elias.fakerMaker.dto.NameDto
import elias.fakerMaker.enums.NameEnums
import elias.fakerMaker.generator_utils.NameGenerator
import elias.fakerMaker.generator_utils.RandomEnum
import kotlin.random.Random

class NameDtoGenerator {
    private val nameGenerator: NameGenerator = NameGenerator()
    private val rand: Random = Random

    fun generate() : NameDto {
        val name = NameDto()
        // todo make the name generator util work better
        name.nickName = "random" + rand.nextInt(100)
        name.isNullable = rand.nextBoolean()
        name.options = listOf(RandomEnum.randomEnum<NameEnums>().description)

        return name
    }
}