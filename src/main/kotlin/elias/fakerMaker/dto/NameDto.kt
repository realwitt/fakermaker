package elias.fakerMaker.dto

import elias.fakerMaker.enums.NameEnums
import elias.fakerMaker.generator_utils.RandomEnum

class NameDto() : Maker<Any>(
    nickName = "",
    isNullable = false,
    options = listOf(RandomEnum.randomEnum<NameEnums>().description)
)