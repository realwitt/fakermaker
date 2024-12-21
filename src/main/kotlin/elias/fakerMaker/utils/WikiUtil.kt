package elias.fakerMaker.utils

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

    fun extractCityWikiValues(wikiUrl: String): Pair<StatesEnum, String> {
        val segment = wikiUrl.substringAfterLast("/")
        val (city, state) = segment.split(",_", limit = 2)

        val stateEnum = StatesEnum.entries.find { it.fullName == state.replace("_", " ") }
            ?: run {
                logger.error { "Failed to parse state from wiki URL: $wikiUrl" }
                throw IllegalArgumentException("No matching state found for: $state")
            }

        return Pair(stateEnum, city.replace("_", " "))
    }
}