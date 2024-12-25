package elias.fakerMaker.utils

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.StatesEnum

object WikiUtil {
    private const val WIKIPEDIA_BASE_URL = "https://en.wikipedia.org/wiki/"
    private const val AREA_CODES_BASE_URL = "https://www.allareacodes.com/"
    private const val FANDOM_BASE_SUFFIX = ".fandom.com/wiki/"

    private val spaceRegex = Regex("\\s+")

    // Pre-computed map of base URLs for better performance
    private val fandomBaseUrls = buildMap<FakerEnum, String> {
        // Special cases
        put(FakerEnum.SILICON_VALLEY, "https://silicon-valley.fandom.com/wiki/")
        put(FakerEnum.GAME_OF_THRONES, "https://awoiaf.westeros.org/index.php/")
        // Generate URLs for other enum values
        FakerEnum.entries
            .filterNot { it in setOf(FakerEnum.SILICON_VALLEY, FakerEnum.GAME_OF_THRONES) }
            .forEach { enum ->
                put(enum, "https://${enum.toString().lowercase().replace("_", "")}$FANDOM_BASE_SUFFIX")
            }
    }

    fun createStateWikiLink(state: StatesEnum): String =
        WIKIPEDIA_BASE_URL + state.fullName.replace(spaceRegex, "_")

    fun createCityWikiLink(state: StatesEnum, city: String): String =
        WIKIPEDIA_BASE_URL + city.replace(spaceRegex, "_") + ",_" +
                state.fullName.replace(spaceRegex, "_")

    fun createPhoneWikiLink(areaCode: String): String =
        AREA_CODES_BASE_URL + areaCode

    fun createFandomWikiLink(fakerEnum: FakerEnum, fullName: String, concatEntireName: Boolean): String {
        val baseUrl = fandomBaseUrls.getValue(fakerEnum)

        val queryParam = if (concatEntireName) {
            fullName.replace(spaceRegex, "_")
        } else {
            fullName.split(" ").let { parts ->
                if (parts.first() == parts.last()) parts.first()
                else "${parts.first()}_${parts.last()}"
            }
        }

        return baseUrl + queryParam
    }
}