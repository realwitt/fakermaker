package elias.fakerMaker.dto

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum

data class DataTableItem(
    val value: String = "",
    val maker: MakerEnum? = MakerEnum.NAME_FIRST,
    val faker: FakerEnum? = FakerEnum.HARRY_POTTER,
    val original: String? = "",
    val hyperlink: String? = ""
){
    override fun toString(): String {
        return """
            |{
            |    faker     = ${faker?.name ?: "null"},
            |    maker     = ${maker?.name ?: "null"},
            |    original  = ${original?.let { "'$it'" } ?: "null"},
            |    value     = '$value',
            |    hyperlink = ${hyperlink?.let { "'$it'" } ?: "null"}
            |}
        """.trimMargin()
    }
}