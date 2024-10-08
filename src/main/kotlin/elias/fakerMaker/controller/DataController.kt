package elias.fakerMaker.controller

import elias.fakerMaker.dto.SchemaRequestDto
import elias.fakerMaker.dto.SchemaResponseDto
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.generator.NameGenerator
import net.datafaker.Faker
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/faker")
class DataController {

    @PostMapping("/schema")
    fun getFakeData(): Any {
        // make service to return random schema response
        return ""

    }

}
