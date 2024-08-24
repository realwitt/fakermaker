package elias.fakerMaker.dto

import kotlinx.serialization.Serializable

// superclass for all maker DTOs
@Serializable
open class Maker<T>(
    var nickName: String = "",
    var isNullable: Boolean = false,
    var options: List<T>? = null
)