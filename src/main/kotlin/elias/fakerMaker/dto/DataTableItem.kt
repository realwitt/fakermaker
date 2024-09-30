package elias.fakerMaker.dto

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum

data class DataTableItem(
    val value: String,
    val type: MakerEnum?,
    val from: FakerEnum?,
    val original: String?,
    val hyperlink: String?
)
