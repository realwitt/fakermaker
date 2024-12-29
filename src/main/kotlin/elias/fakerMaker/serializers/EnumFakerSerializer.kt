package elias.fakerMaker.serializers

import elias.fakerMaker.enums.FakerEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import mu.KotlinLogging

object EnumFakerSerializer : KSerializer<FakerEnum> {
    private val logger = KotlinLogging.logger {}

    override val descriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): FakerEnum {
        val prettyName = decoder.decodeString()
        return try {
            FakerEnum.entries.find { it.prettyName == prettyName }
                ?: throw IllegalArgumentException("Invalid faker type: $prettyName")
        } catch (e: IllegalArgumentException) {
            logger.error(e) { "Failed to parse FakerEnum [prettyName=$prettyName]" }
            throw e
        }
    }

    override fun serialize(encoder: Encoder, value: FakerEnum) {
        encoder.encodeString(value.prettyName)
    }
}