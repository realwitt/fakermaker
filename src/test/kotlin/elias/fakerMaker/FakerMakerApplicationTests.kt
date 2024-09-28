package elias.fakerMaker

import net.datafaker.Faker
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

class FakerMakerApplicationTests {
	private val faker: Faker = Faker()

	@Test
	fun contextLoads() {
		// for (i in 0..99) {
		// 	println(faker.context.locale.country.toString())
		// }
	}

}
