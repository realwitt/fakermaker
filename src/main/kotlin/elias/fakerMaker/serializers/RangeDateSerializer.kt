package elias.fakerMaker.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

object RangeDateSerializer : KSerializer<Pair<LocalDate, LocalDate>> {
    private val listSerializer = ListSerializer(String.serializer())

    // Custom formatter for "MMM-dd-yyyy" format (e.g., "JAN-01-1970")
    private val formatter = DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .appendPattern("MMM-dd-yyyy")
        .toFormatter(Locale.ENGLISH)

    override val descriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Pair<LocalDate, LocalDate>) {
        val dates = listOf(
            value.first.format(formatter),
            value.second.format(formatter)
        )
        listSerializer.serialize(encoder, dates)
    }

    override fun deserialize(decoder: Decoder): Pair<LocalDate, LocalDate> {
        val list = listSerializer.deserialize(decoder)
        require(list.size == 2) { "Date range must contain exactly 2 values" }
        return Pair(
            LocalDate.parse(list[0], formatter),
            LocalDate.parse(list[1], formatter)
        )
    }
}