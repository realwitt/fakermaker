package elias.fakerMaker.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RangePriceSerializer : KSerializer<Pair<Double, Double>> {
    private val listSerializer = ListSerializer(Double.serializer())

    override val descriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Pair<Double, Double>) {
        listSerializer.serialize(encoder, listOf(value.first, value.second))
    }

    override fun deserialize(decoder: Decoder): Pair<Double, Double> {
        val list = listSerializer.deserialize(decoder)
        require(list.size == 2) { "Price range must contain exactly 2 values" }
        return Pair(list[0], list[1])
    }
}