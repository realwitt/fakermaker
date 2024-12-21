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
import kotlin.random.Random


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
           maker    = MakerEnum.STATE,
           faker    = null,
           original = null,
           value    = state.toString(),
           wikiUrl  = WikiUtil.createStateWikiLink(state),
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
           maker    = MakerEnum.CITY,
           faker    = null,
           original = "$city, $state",
           value    = city,
           wikiUrl  = WikiUtil.createCityWikiLink(state, city),
        )
    }

    private data class StateCity(
        val state: StatesEnum,
        val city: String
    )

    private fun findLocation(existingItems: List<DataTableItem>?): StateCity {
        return when {
            // Case 1: ZIP exists -> find state and city that have this ZIP
            existingItems?.find { it.maker == MakerEnum.ZIP }?.value != null -> {
                val zip = existingItems.find { it.maker == MakerEnum.ZIP }?.value
                    ?: throw IllegalStateException("ZIP value was somehow null")

                // Find the state and city that contain this ZIP
                val (state, city) = americaData.entries.firstNotNullOfOrNull { (stateEnum, cities) ->
                    cities.entries.firstNotNullOfOrNull { (cityName, data) ->
                        if (zip in data.zipCodes) {
                            Pair(stateEnum, cityName)
                        } else null
                    }
                } ?: throw IllegalStateException("ZIP $zip not found in any state/city")

                StateCity(state, city)
            }

            // Case 2: City exists -> extract state and city from wiki link
            existingItems?.find { it.maker == MakerEnum.CITY }?.wikiUrl != null -> {
                val cityWikiUrl = existingItems.find { it.maker == MakerEnum.CITY }?.run {
                    wikiUrl ?: throw IllegalStateException("City '$value' hyperlink was somehow null")
                } ?: throw IllegalStateException("City not found")

                val (extractedState, extractedCity) = WikiUtil.extractCityWikiValues(cityWikiUrl)
                StateCity(extractedState, extractedCity)
            }

            // Case 3: State exists (or if not, random state) -> random city from state
            else -> {
                val stateValue = existingItems?.find { it.maker == MakerEnum.STATE }?.value ?: StatesEnum.entries
                    .filter { it !in excludedStates }
                    .random().toString()
                val state = StatesEnum.valueOf(stateValue)
                val city = americaData[state]?.entries?.random()?.key ?: run {
                    throw IllegalStateException("State $stateValue somehow has no cities")
                }
                StateCity(state, city)
            }
        }
    }

    fun zip(existingItems: List<DataTableItem>?): DataTableItem {
        val stateCity = findLocation(existingItems)
        val zip = americaData[stateCity.state]?.get(stateCity.city)?.zipCodes?.random() ?: run {
            throw IllegalStateException("City '${stateCity.city}' somehow has no zipcode")
        }

        return DataTableItem(
           maker    = MakerEnum.ZIP,
           faker    = null,
           original = "${stateCity.city}, ${stateCity.state} $zip",
           value    = zip,
           wikiUrl  = WikiUtil.createCityWikiLink(stateCity.state, stateCity.city)
        )
    }

    private fun findValidAreaCode(state: StatesEnum): Pair<String, StateCity> {
        americaData[state]?.entries?.forEach { (city, locationInfo) ->
            locationInfo.areaCodes.randomOrNull()?.let { areaCode ->
                return Pair(areaCode, StateCity(state, city))
            }
        }
        return findValidAreaCode(StatesEnum.entries.random())
    }

    // 50% chance to be tied to the existing locations provided
    fun phone(existingItems: List<DataTableItem>?): DataTableItem {
        val stateCity = findLocation(existingItems)
        val areaCodeInfo = americaData[stateCity.state]?.get(stateCity.city)?.areaCodes?.random()?.let {
            Pair(it, stateCity)
        } ?: findValidAreaCode(stateCity.state)

        val finalAreaCodeInfo = if (Random.nextBoolean()) {
            areaCodeInfo
        } else {
            findValidAreaCode(StatesEnum.entries.filter { it !in excludedStates }.random())
        }

        // todo: make rest of number random, randomize formats
        // (706) 341 2352
        // 134-234-2345
        // (455)2342344
        // 3123453456
        // 234.435.2345
        return DataTableItem(
            maker    = MakerEnum.PHONE,
            faker    = null,
            original = "${finalAreaCodeInfo.second.city}, ${finalAreaCodeInfo.second.state}",
            value    = finalAreaCodeInfo.first,
            wikiUrl  = WikiUtil.createCityWikiLink(finalAreaCodeInfo.second.state, finalAreaCodeInfo.second.city)
        )
    }


    // address 1
    // address 2


}