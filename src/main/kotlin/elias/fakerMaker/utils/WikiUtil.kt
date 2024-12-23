package elias.fakerMaker.utils

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.StatesEnum
import mu.KotlinLogging


object WikiUtil {
    private val logger = KotlinLogging.logger {}

    fun createStateWikiLink(state: StatesEnum) : String {
        return "https://en.wikipedia.org/wiki/${state.fullName.replace(" ", "_")}"
    }

    fun createCityWikiLink(state: StatesEnum, city: String): String {
        // https://en.wikipedia.org/wiki/Fort_Ransom,_North_Dakota
        return "https://en.wikipedia.org/wiki/${city.replace(" ", "_")},_${state.fullName.replace(" ", "_")}"
    }

    fun createPhoneWikiLink(areaCode: String): String {
        return "https://www.allareacodes.com/${areaCode}"
    }

    fun createFandomWikiLink(fakerEnum: FakerEnum, fullName: String, concatEntireName: Boolean): String {
        // allow more fine-grained control over which wiki we want to pull from
        // ...and bc fandom's Silicon Valley link is "silicon-valley" instead of "siliconvalley" -_-
        val fandomBaseUrl = when (fakerEnum) {
            FakerEnum.SILICON_VALLEY -> "silicon-valley.fandom.com/wiki/"
            FakerEnum.GAME_OF_THRONES -> "awoiaf.westeros.org/index.php/"
            else -> "${fakerEnum.toString().replace("_", "").lowercase()}.fandom.com/wiki/"
        }
        val firstName = fullName.split(" ").first()
        val lastName = fullName.split(" ").last()
        // unlikes names where we only want first and last, for locations we want the whole name
        val fandomQueryParam = when (concatEntireName) {
            true -> fullName.replace(" ", "_")
            false -> {if (firstName == lastName) firstName else "${firstName}_${lastName}"}
        }
        return "https://$fandomBaseUrl$fandomQueryParam"
    }
}