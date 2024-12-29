package elias.fakerMaker.serializers

import elias.fakerMaker.enums.IdTypeEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import mu.KotlinLogging

object EnumIdTypeSerializer : KSerializer<IdTypeEnum> {
    private val logger = KotlinLogging.logger {}

    override val descriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): IdTypeEnum {
        val name = decoder.decodeString()
        return try {
            IdTypeEnum.entries.find { it.prettyName == name }
                ?: throw IllegalArgumentException("Invalid ID type: $name")
        } catch (e: IllegalArgumentException) {
            logger.error(e) { "Failed to parse IdTypeEnum [name=$name]" }
            throw e
        }
    }

    override fun serialize(encoder: Encoder, value: IdTypeEnum) {
        encoder.encodeString(value.prettyName)
    }
}
