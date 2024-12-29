package elias.fakerMaker.serializers

import elias.fakerMaker.enums.FakerEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

object ListEnumFakerSerializer : KSerializer<List<FakerEnum>> by ListSerializer(EnumFakerSerializer)