package elias.fakerMaker.dto

import elias.fakerMaker.enums.MakerEnum
import kotlinx.serialization.Serializable

// superclass for all maker DTOs
@Serializable
open class Maker<T>(
    var enum: MakerEnum = MakerEnum.NAME_FIRST,
    var nickName: String = "",
    var isNullable: Boolean = false,
    var options: List<T>? = null
) {
    override fun toString(): String {
        return """
            |Maker(
            |    enum       = ${enum.name},
            |    nickName   = '$nickName',
            |    isNullable = $isNullable,
            |    options    = ${options?.toString() ?: "null"}
            |)
        """.trimMargin()
    }
}