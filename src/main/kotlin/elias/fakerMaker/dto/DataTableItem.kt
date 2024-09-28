package elias.fakerMaker.dto

import elias.fakerMaker.enums.FakerEnum

data class DataTableItem(
    val value: String,
    val from: FakerEnum?,
    val original: String?,
    val hyperlink: String?
)
