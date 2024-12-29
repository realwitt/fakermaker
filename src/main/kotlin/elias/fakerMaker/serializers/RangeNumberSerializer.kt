package elias.fakerMaker.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RangeNumberSerializer : KSerializer<Pair<Int, Int>> {
    private val listSerializer = ListSerializer(Int.serializer())

    override val descriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Pair<Int, Int>) {
        listSerializer.serialize(encoder, listOf(value.first, value.second))
    }

    override fun deserialize(decoder: Decoder): Pair<Int, Int> {
        val list = listSerializer.deserialize(decoder)
        require(list.size == 2) { "Number range must contain exactly 2 values" }
        return Pair(list[0], list[1])
    }
}