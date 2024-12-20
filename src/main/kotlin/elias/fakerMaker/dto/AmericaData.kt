package elias.fakerMaker.dto

import elias.fakerMaker.enums.StatesEnum
import kotlinx.serialization.Serializable

@Serializable
data class AmericaData(
    // The root object is just a map of state codes to cities
    val data: Map<String, Map<String, LocationData>>
)

@Serializable
data class LocationData(
    val areaCodes: List<String>,
    val zipCodes: List<String>
)
