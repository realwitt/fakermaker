package elias.fakerMaker.dto

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.enums.StatesEnum
import elias.fakerMaker.fakers.Tech


/*
 we need this object to hold
 maker
 fakers used
 original value (name of fictional character)
 derived value (name we're using)
 related makers used (ZIP, PHONE, CITY)
 wikiUrl
 */

sealed class Influencer {
    data class State(val state: StatesEnum) : Influencer()
    data class City(val city: String) : Influencer()
    data class Zip(val zip: String) : Influencer()
    data class AreaCode(val areaCode: String) : Influencer()
}

data class DataTableItem(
    val maker: MakerEnum?               = MakerEnum.NAME_FIRST,
    val fakersUsed: List<FakerEnum>?    = listOf(FakerEnum.TECH),
    val originalValue: String?          = Tech.people.random(),
    val derivedValue: String            = originalValue?.split(" ")?.first() ?: "",
    val wikiUrl: String?                = "",
    val influencedBy: List<Influencer>? = listOf(
        Influencer.State(StatesEnum.AR),
    )
) {
    override fun toString(): String {
        return """
            |{
            |    fakersUsed     = ${fakersUsed?.forEach { println(it.name) } ?: "null"},
            |    maker          = ${maker?.name ?: "null"},
            |    originalValue  = ${originalValue?.let { "'$it'" } ?: "null"},
            |    derivedValue   = '$derivedValue',
            |    wikiUrl        = ${wikiUrl?.let { "'$it'" } ?: "null"}
            |    influencedBy   = ${influencedBy?.let { "'$it'" } ?: "null"}
            |}
        """.trimMargin()
    }
}