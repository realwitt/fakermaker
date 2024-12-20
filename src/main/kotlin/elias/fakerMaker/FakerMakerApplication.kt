package elias.fakerMaker

import elias.fakerMaker.generator.AmericaGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FakerMakerApplication

fun main(args: Array<String>) {
	runApplication<FakerMakerApplication>(*args)
}
