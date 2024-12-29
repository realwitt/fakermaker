package elias.fakerMaker.types

import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.enums.IdTypeEnum
import elias.fakerMaker.serializers.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.util.*

@Serializable
data class MakerConfig(
    @SerialName("makerEnum")
    @Serializable(with = EnumMakerSerializer::class)
    val makerEnum: MakerEnum,
    val nickName: String,
    val nullable: Boolean = false,
    @Serializable(with = RangeDateSerializer::class)
    val dateRange: Pair<@Serializable(with = LocalDateSerializer::class) LocalDate,
            @Serializable(with = LocalDateSerializer::class) LocalDate>? = null,
    @Serializable(with = RangeNumberSerializer::class)
    val numberRange: Pair<Int, Int>? = null,
    @Serializable(with = RangePriceSerializer::class)
    val priceRange: Pair<Double, Double>? = null,
    @Serializable(with = EnumIdTypeSerializer::class)
    val idTypeEnum: IdTypeEnum? = null
) {
    override fun toString(): String {
        return buildString {
            append("{\n")
            append("      makerEnum : ${makerEnum.name},\n")
            append("      nickName  : \"$nickName\",\n")
            append("      nullable  : $nullable")

            when {
                dateRange != null -> {
                    val formatter = DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("MMM-dd-yyyy")
                        .toFormatter(Locale.ENGLISH)
                    append(",\n      dateRange: [\n")
                    append("        ${dateRange.first.format(formatter)},\n")
                    append("        ${dateRange.second.format(formatter)}\n")
                    append("      ]")
                }
                numberRange != null -> {
                    append(",\n      numberRange: [\n")
                    append("        ${numberRange.first},\n")
                    append("        ${numberRange.second}\n")
                    append("      ]")
                }
                priceRange != null -> {
                    append(",\n      priceRange: [\n")
                    append("        ${String.format("%.2f", priceRange.first)},\n")
                    append("        ${String.format("%.2f", priceRange.second)}\n")
                    append("      ]")
                }
            }

            if (idTypeEnum != null) {
                append(",\n      idTypeEnum: ${idTypeEnum.name}")
            }

            append("\n    }")
        }
    }
}