package elias.fakerMaker.serializers

import elias.fakerMaker.enums.MakerEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import mu.KotlinLogging

object EnumMakerSerializer : KSerializer<MakerEnum> {
    private val logger = KotlinLogging.logger {}

    override val descriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): MakerEnum {
        val prettyName = decoder.decodeString()
        return try {
            MakerEnum.entries.find { it.prettyName == prettyName }
                ?: throw IllegalArgumentException("Invalid maker type: $prettyName")
        } catch (e: IllegalArgumentException) {
            logger.error(e) { "Failed to parse MakerEnum [prettyName=$prettyName]" }
            throw e
        }
    }

    override fun serialize(encoder: Encoder, value: MakerEnum) {
        encoder.encodeString(value.prettyName)
    }
}