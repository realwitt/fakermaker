package elias.fakerMaker.dto

import elias.fakerMaker.enums.StatesEnum
import elias.fakerMaker.mapper.AmericaDataSerializer
import kotlinx.serialization.Serializable

typealias AmericaData = @Serializable(with = AmericaDataSerializer::class)
Map<StatesEnum, Map<String, LocationData>>

@Serializable
data class LocationData(
    val areaCodes: List<String>,
    val zipCodes: List<String>
)
