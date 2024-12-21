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
            RandomEnum.randomEnum<StatesEnum>().toString(),
            null
        )
    }

    fun city(existingItems: List<DataTableItem>?): DataTableItem {
        val state = existingItems?.find { it.maker == MakerEnum.STATE }?.value?.let {
            StatesEnum.valueOf(it)
        } ?: RandomEnum.randomEnum<StatesEnum>()

        val cityValue = americaData[state]?.entries?.random()?.key ?: ""

        return DataTableItem(
            MakerEnum.CITY,
            null,
            null,
            cityValue,
            null
        )
    }

    // zip
    // address 1
    // address 2
    // phone (50% chance to be tied to the address)


}