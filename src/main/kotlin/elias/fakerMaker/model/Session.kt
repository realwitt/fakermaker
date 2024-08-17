package elias.fakerMaker.model

import jakarta.persistence.*

data class Session(
    @Id
    val id: Long?,
    val text: String
)