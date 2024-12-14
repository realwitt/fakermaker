package elias.fakerMaker.dto

import elias.fakerMaker.enums.NameEnum
import elias.fakerMaker.utils.RandomEnum
import kotlinx.serialization.Serializable

@Serializable
class Name() : Maker<NameEnum>(
    nickName = "",
    isNullable = false,
    options = listOf(RandomEnum.randomEnum<NameEnum>())
)