package elias.fakerMaker.dto

import elias.fakerMaker.enums.FakerEnum
import kotlinx.serialization.Serializable

@Serializable
data class SchemaRequestDto<Any>(
    var fakers: List<FakerEnum> = emptyList(),
    var makers: List<Maker<Map<String, Any>>> = emptyList(),
    var sessionID: String = ""
)
