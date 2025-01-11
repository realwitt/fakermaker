package elias.fakerMaker.types.dto

import elias.fakerMaker.types.DataTableItem

data class DataTableDto(
    val headers: List<String> = listOf(""),
    val data: List<List<DataTableItem>> = listOf(listOf(DataTableItem()))
)

/*
{
    headers: ["First", "Last", "Birthday", "Card #", "CVV"]
    data: [
        [DataTableItem, DataTableItem, etc. ] // row 1
        [DataTableItem, DataTableItem, etc. ] // row 2
        [DataTableItem, DataTableItem, etc. ] // etc.
    ]
}
 */

// make sure if the order would be changed by sorting that we return the correct order on return
// like lastName, firstName, firstName2 -> comes out in the same order