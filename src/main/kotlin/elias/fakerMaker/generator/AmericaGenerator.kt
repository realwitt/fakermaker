package elias.fakerMaker.generator

import elias.fakerMaker.dto.AmericaData
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.enums.StatesEnum
import elias.fakerMaker.utils.WikiUtil
import jakarta.annotation.PostConstruct
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component


@Component
object AmericaGenerator {
    private lateinit var americaData: AmericaData
    private val logger = KotlinLogging.logger {}
    private val excludedStates = setOf(
        StatesEnum.state
    )

    @OptIn(ExperimentalSerializationApi::class)
    @PostConstruct
    fun loadData() {
        try {
            val resource = ClassPathResource("AmericaData.json")
            americaData = resource.inputStream.use { stream ->
                Json.decodeFromStream<AmericaData>(stream)
            }

        } catch (e: Exception) {
            logger.error(e) { "Failed to load AmericaData.json..." }
            throw e
        }
    }

    fun state(): DataTableItem {
        val state = StatesEnum.entries
            .filter { it !in excludedStates }
            .random()

        return DataTableItem(
            MakerEnum.STATE,
            null,
            null,
            state.toString(),
            WikiUtil.createStateWikiLink(state),
        )
    }

    fun city(existingItems: List<DataTableItem>?): DataTableItem {
        val state = existingItems?.find { it.maker == MakerEnum.STATE }?.value?.let {
            StatesEnum.valueOf(it)
        } ?: StatesEnum.entries
            .filter { it !in excludedStates }
            .random()

        val city = americaData[state]?.entries?.random()?.key ?: ""

        return DataTableItem(
            MakerEnum.CITY,
            null,
            "$city, $state",
            city,
            WikiUtil.createCityWikiLink(state, city),
        )
    }

    fun zip(existingItems: List<DataTableItem>?): DataTableItem {
        val state: StatesEnum?
        val city: String?

        val zip = when {
            // Case 1: City exists -> extract state and city from wiki link
            existingItems?.find { it.maker == MakerEnum.CITY }?.wikiUrl != null -> {
                val cityWikiUrl = existingItems.find { it.maker == MakerEnum.CITY }?.run {
                    wikiUrl ?: throw IllegalStateException("City '$value' hyperlink was somehow null")
                } ?: throw IllegalStateException("City not found")

                val (extractedState, extractedCity) = WikiUtil.extractCityWikiValues(cityWikiUrl)
                state = extractedState
                city = extractedCity
                americaData[state]?.get(city)?.zipCodes?.random() ?: run {
                    throw IllegalStateException("City '${city}' somehow has no zipcode")
                }
            }

            // Case 2: State exists (or if not, random state) -> random city from state -> random zip
            else -> {
                val stateValue = existingItems?.find { it.maker == MakerEnum.STATE }?.value ?: StatesEnum.entries
                    .filter { it !in excludedStates }
                    .random().toString()
                state = StatesEnum.valueOf(stateValue)
                city = americaData[state]?.entries?.random()?.key ?: run {
                    throw IllegalStateException("State $stateValue is somehow has no cities")
                }
                americaData[state]?.get(city)?.zipCodes?.random() ?: run {
                    throw IllegalStateException("State $city somehow has no zip codes")
                }
            }
        }

        return DataTableItem(
            maker = MakerEnum.ZIP,
            null,
            "$city, $state $zip",
            value = zip,
            wikiUrl = WikiUtil.createCityWikiLink(state, city)
        )
    }

    // address 1
    // address 2
    // phone (50% chance to be tied to the address)


}