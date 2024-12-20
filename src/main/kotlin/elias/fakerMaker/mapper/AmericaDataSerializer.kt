package elias.fakerMaker.mapper

import elias.fakerMaker.dto.LocationData
import elias.fakerMaker.enums.StatesEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object AmericaDataSerializer: KSerializer<Map<StatesEnum, Map<String, LocationData>>> {
    // Create a serializer for the inner Map type
    private val mapSerializer = MapSerializer(
        String.serializer(),
        MapSerializer(String.serializer(), LocationData.serializer())
    )

    override val descriptor = mapSerializer.descriptor

    override fun deserialize(decoder: Decoder): Map<StatesEnum, Map<String, LocationData>> {
        // First deserialize as a Map<String, Map<String, LocationData>>
        val stringMap = mapSerializer.deserialize(decoder)

        // Then convert the string keys to StatesEnum
        return stringMap.mapKeys { (key, _) ->
            try {
                StatesEnum.valueOf(key)
            } catch (e: IllegalArgumentException) {
                println("Warning: Unknown state code $key")
                // You might want to handle unknown state codes differently
                throw e
            }
        }
    }

    override fun serialize(encoder: Encoder, value: Map<StatesEnum, Map<String, LocationData>>) {
        // Convert StatesEnum keys back to strings for serialization
        val stringMap = value.mapKeys { (key, _) -> key.name }
        mapSerializer.serialize(encoder, stringMap)
    }
}