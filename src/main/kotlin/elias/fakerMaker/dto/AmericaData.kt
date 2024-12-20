package elias.fakerMaker.dto

import elias.fakerMaker.enums.StatesEnum
import elias.fakerMaker.mapper.AmericaDataSerializer
import kotlinx.serialization.Serializable

@Serializable
data class AmericaData(
    @Serializable(with = AmericaDataSerializer::class)
    // The root object is just a map of state codes to cities
    val data: Map<StatesEnum, Map<String, LocationData>>
)

@Serializable
data class LocationData(
    val areaCodes: List<String>,
    val zipCodes: List<String>
)
