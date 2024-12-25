package elias.fakerMaker.model

import jakarta.persistence.Id

data class Session(
    @Id
    val id: Long?,
    val text: String
)