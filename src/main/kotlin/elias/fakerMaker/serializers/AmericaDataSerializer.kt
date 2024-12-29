package elias.fakerMaker.serializers

import elias.fakerMaker.types.LocationData
import elias.fakerMaker.enums.StatesEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import mu.KotlinLogging

object AmericaDataSerializer: KSerializer<Map<StatesEnum, Map<String, LocationData>>> {
    private val logger = KotlinLogging.logger {}

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
                logger.error(e) { "Failed to parse StateEnum [code=$key]" }
                throw e
            } catch (e: Error) {
                logger.error(e) { "Error occured parsing StateEnums..." }
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