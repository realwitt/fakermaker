package elias.fakerMaker.dto

import elias.fakerMaker.enums.NameEnum
import kotlinx.serialization.Serializable

@Serializable
class Name() : Maker<NameEnum>(
    nickName = "",
    isNullable = false,
    options = listOf(NameEnum.entries.random())
)