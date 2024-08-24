package elias.fakerMaker.dto

import elias.fakerMaker.enums.FakerLibraryEnums
import kotlinx.serialization.Serializable

@Serializable
data class SchemaRequestDto<Any>(
    var fakers: List<FakerLibraryEnums> = emptyList(),
    var makers: List<Maker<Any>> = emptyList(),
    var sessionID: String = ""
)
