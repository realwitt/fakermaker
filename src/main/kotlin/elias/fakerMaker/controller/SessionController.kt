package elias.fakerMaker.controller

import elias.fakerMaker.model.Session
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SessionController {

    @GetMapping("/api/faker")
    fun getFakeData(): String {
        return "blog"
    }

}