package elias.fakerMaker.controller

import org.springframework.web.bind.annotation.PostMapping
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
