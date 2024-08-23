package elias.fakerMaker.dto

import elias.fakerMaker.FakerLibraryEnums


data class SchemaRequestDto(
    val fakers: List<FakerLibraryEnums>,
    val makers: List<Maker<*>>,
    val sessionID: String
)
