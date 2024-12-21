package elias.fakerMaker.generator

import elias.fakerMaker.dto.AmericaData
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.enums.StatesEnum
import elias.fakerMaker.utils.RandomEnum
import jakarta.annotation.PostConstruct
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component


@Component
object AmericaGenerator {
    private val logger = KotlinLogging.logger {}
    private lateinit var americaData: AmericaData
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
        return DataTableItem(
            MakerEnum.STATE,
            null,
            null,
            StatesEnum.entries
                .filter { it !in excludedStates }
                .random().toString(),
            null
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
            null,
            city,
            null
        )
    }

    // todo: we need to rework the dataset bc it's getting rid of dup zip codes (see Wyckoff, NH)
    fun zip(existingItems: List<DataTableItem>?): DataTableItem {
        // Get state - either from existing items or random
        val state = existingItems?.find { it.maker == MakerEnum.STATE }?.value?.let {
            StatesEnum.valueOf(it)
        } ?: StatesEnum.entries
            .filter { it !in excludedStates }
            .random()

        // Get city - either from existing items or random for the selected state
        val city = existingItems?.find { it.maker == MakerEnum.CITY }?.value
            ?: americaData[state]?.entries?.random()?.key
            ?: throw IllegalStateException("No cities found for state $state")

        // Get random zip code for the state/city combination
        val zip = americaData[state]?.get(city)?.zipCodes?.random()
            ?: throw IllegalStateException("No zip codes found for $city, $state")

        return DataTableItem(
            MakerEnum.ZIP,
            null,
            null,
            zip,
            null
        )
    }

    // address 1
    // address 2
    // phone (50% chance to be tied to the address)


}