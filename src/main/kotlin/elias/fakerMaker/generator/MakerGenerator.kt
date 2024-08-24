package elias.fakerMaker.generator

import elias.fakerMaker.dto.Maker
import kotlin.random.Random

class MakerGenerator {
    private val nameDtoGenerator = NameDtoGenerator()

    fun generate() : Maker<Any> {
        // random int
        val rand = Random.nextInt()

        // list of dto classes
        // pick random dto index using the randInt
        return nameDtoGenerator.generate()
    }
}