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
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.File


@Component
class AmericaGenerator {
//    private lateinit var americaData: AmericaData

    @OptIn(ExperimentalSerializationApi::class)
    @PostConstruct
    fun loadData() {

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        val resource = ClassPathResource("AmericaData.json")
//        val jsonContent = resource.inputStream.bufferedReader().use { it.readText() }
//        println(jsonContent)
        val americaDataString = resource.inputStream.use { stream ->
            json.decodeFromStream<AmericaData>(stream)
        }
        println("printing the data now...")
        println(americaDataString.toString())
    }

    // Rest of your functions remain the same
    fun state(): DataTableItem {
        return DataTableItem(
            MakerEnum.STATE,
            null,
            null,
            RandomEnum.randomEnum<StatesEnum>().toString(),
            null
        )
    }

//    fun city(existingItems: List<DataTableItem>?): DataTableItem {
//        val state = existingItems?.find { it.maker == MakerEnum.STATE }?.value?.let {
//            StatesEnum.valueOf(it)
//        } ?: RandomEnum.randomEnum<StatesEnum>()
//
//        val cityValue = americaData.data[state.toString()]?.entries?.random()?.key ?: ""
//
//        return DataTableItem(
//            MakerEnum.CITY,
//            null,
//            null,
//            cityValue,
//            null
//        )
//    }

    // city
    // state
    // zip
    // address 1
    // address 2
    // phone (50% chance to be tied to the address)


}