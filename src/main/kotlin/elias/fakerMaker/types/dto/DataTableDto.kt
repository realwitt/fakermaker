package elias.fakerMaker.types.dto

import elias.fakerMaker.types.DataTableItem

data class DataTableDto(
    val headers: List<String> = listOf(""),
    val data: List<DataTableItem> = listOf(DataTableItem())
)