package elias.fakerMaker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FakerMakerApplication

fun main(args: Array<String>) {
	runApplication<FakerMakerApplication>(*args)
}
