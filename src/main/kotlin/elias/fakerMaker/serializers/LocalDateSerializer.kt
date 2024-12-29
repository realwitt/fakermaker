package elias.fakerMaker.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

// I saw jackson has a serializer for LocalDate, but I used kotlin serialization everywhere else so I figured I'd stay consistent
object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}