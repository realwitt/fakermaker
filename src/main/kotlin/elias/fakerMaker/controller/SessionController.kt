package elias.fakerMaker.controller

import elias.fakerMaker.dto.SchemaRequestDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/faker")
class SessionController {

    @PostMapping("/schema")

    fun getFakeData(@RequestParam schemaRequestDto: String): String {
           val fakerSchemaDto =  schemaRequestDto
        return "blog"
    }

}