package elias.fakerMaker.dto

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import kotlinx.serialization.Serializable

@Serializable
data class SchemaRequest (
    var fakers: List<FakerEnum> = emptyList(),
    var makers: List<MakerEnum> = emptyList(),
    var sessionID: String = ""
)
