import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.StatesEnum
import org.springframework.stereotype.Component

@Component
object WikiUtil {
    private const val WIKIPEDIA_BASE_URL = "https://en.wikipedia.org/wiki/"
    private const val AREA_CODES_BASE_URL = "https://www.allareacodes.com/"
    private const val FANDOM_BASE_SUFFIX = ".fandom.com/wiki/"
    private val SPACE_REGEX = Regex("\\s+")

    // Pre-compute special cases map
    private val specialFandomCases = mapOf(
        FakerEnum.SILICON_VALLEY to "https://silicon-valley.fandom.com/wiki/",
        FakerEnum.GAME_OF_THRONES to "https://awoiaf.westeros.org/index.php/"
    )

    // Pre-compute state wiki links
    private val stateWikiLinks: Map<StatesEnum, String> by lazy {
        StatesEnum.entries.associateWith { state ->
            WIKIPEDIA_BASE_URL + state.fullName.replace(SPACE_REGEX, "_")
        }
    }

    // Cache for generated fandom URLs
    private val fandomBaseUrls: Map<FakerEnum, String> by lazy {
        buildMap {
            putAll(specialFandomCases)
            FakerEnum.entries
                .filterNot { it in specialFandomCases.keys }
                // The keys are the enum values
                // The values are created by transforming each key using the provided lambda
                // where "this" is the map we're building
                .associateWithTo(this) { enum ->
                    "https://${enum.toString().lowercase().replace("_", "")}$FANDOM_BASE_SUFFIX"
                }
        }
    }

    // Use ThreadLocal for StringBuilder to ensure thread safety
    private val stringBuilder = ThreadLocal.withInitial { StringBuilder(256) }

    private fun StringBuilder.safeClear(): StringBuilder {
        setLength(0)
        return this
    }

    fun createStateWikiLink(state: StatesEnum): String =
        stateWikiLinks.getValue(state)

    fun createCityWikiLink(state: StatesEnum, city: String): String =
        stringBuilder.get().safeClear()
            .append(WIKIPEDIA_BASE_URL)
            .append(city.replace(SPACE_REGEX, "_"))
            .append(",_")
            .append(state.fullName.replace(SPACE_REGEX, "_"))
            .toString()

    fun createPhoneWikiLink(areaCode: String): String =
        stringBuilder.get().safeClear()
            .append(AREA_CODES_BASE_URL)
            .append(areaCode)
            .toString()

    fun createFandomWikiLink(fakerEnum: FakerEnum, fullName: String, concatEntireName: Boolean): String {
        val baseUrl = fandomBaseUrls.getValue(fakerEnum)
        val sb = stringBuilder.get()

        val queryParam = if (concatEntireName) {
            fullName.replace(SPACE_REGEX, "_")
        } else {
            val parts = fullName.split(' ')
            if (parts.size <= 1) {
                parts[0]
            } else if (parts.first() == parts.last()) {
                parts.first()
            } else {
                sb.safeClear()
                    .append(parts.first())
                    .append('_')
                    .append(parts.last())
                    .toString()
            }
        }

        return sb.safeClear()
            .append(baseUrl)
            .append(queryParam)
            .toString()
    }
}