package elias.fakerMaker.dto

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.Tech

// todo rework this to pull a random first name from the tech list instead
data class DataTableItem(
    val maker: MakerEnum? = MakerEnum.NAME_FIRST,
    val faker: FakerEnum? = FakerEnum.TECH,
    val original: String? = Tech.people.random(),
    val value: String = original?.split(" ")?.first() ?: "",
    val wikiUrl: String? = ""
) {
    override fun toString(): String {
        return """
            |{
            |    faker     = ${faker?.name ?: "null"},
            |    maker     = ${maker?.name ?: "null"},
            |    original  = ${original?.let { "'$it'" } ?: "null"},
            |    value     = '$value',
            |    wikiUrl   = ${wikiUrl?.let { "'$it'" } ?: "null"}
            |}
        """.trimMargin()
    }
}