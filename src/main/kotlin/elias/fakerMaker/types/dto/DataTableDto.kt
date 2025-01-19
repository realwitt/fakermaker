package elias.fakerMaker.types.dto

import elias.fakerMaker.types.DataTableItem

data class DataTableDto(
    val headers: List<String> = listOf(""),
    val data: List<Map<String, DataTableItem>> = listOf(mapOf("" to DataTableItem()))
)