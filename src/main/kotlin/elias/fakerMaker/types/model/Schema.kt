package elias.fakerMaker.types.model

import elias.fakerMaker.enums.FakerEnum
import kotlinx.serialization.Serializable
import elias.fakerMaker.serializers.*
import elias.fakerMaker.types.MakerConfig
import java.time.format.DateTimeFormatterBuilder
import java.util.*

@Serializable
data class Schema(
    val sessionID: String? = null,
    @Serializable(with = ListEnumFakerSerializer::class)
    val fakers: List<FakerEnum> = emptyList(),
    val makers: List<MakerConfig> = emptyList()
) {
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("{\n")

        // SessionID
        if (sessionID != null) {
            sb.append("  sessionID: \"$sessionID\",\n")
        }

        // Fakers
        sb.append("  fakers: [\n")
        fakers.forEachIndexed { index, faker ->
            sb.append("    ${faker.name}")
            if (index < fakers.size - 1) sb.append(",")
            sb.append("\n")
        }
        sb.append("  ],\n")

        // Makers
        sb.append("  makers: [\n")
        makers.forEachIndexed { index, maker ->
            sb.append("    {\n")
            sb.append("      makerEnum : ${maker.makerEnum.name},\n")
            sb.append("      nickName  : ${maker.nickname},\n")
            sb.append("      nullable  : ${maker.nullable}")

            // Handle different range types
            when {
                maker.dateRange != null -> {
                    sb.append(",\n      dateRange: [\n")
                    val formatter = DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("MMM-dd-yyyy")
                        .toFormatter(Locale.ENGLISH)
                    sb.append("        ${maker.dateRange.first.format(formatter)},\n")
                    sb.append("        ${maker.dateRange.second.format(formatter)}\n")
                    sb.append("      ]")
                }
                maker.numberRange != null -> {
                    sb.append(",\n      numberRange: [\n")
                    sb.append("        ${maker.numberRange.first},\n")
                    sb.append("        ${maker.numberRange.second}\n")
                    sb.append("      ]")
                }
                maker.priceRange != null -> {
                    sb.append(",\n      priceRange: [\n")
                    sb.append("        ${String.format("%.2f", maker.priceRange.first)},\n")
                    sb.append("        ${String.format("%.2f", maker.priceRange.second)}\n")
                    sb.append("      ]")
                }
            }

            // Optional idEnum
            if (maker.idTypeEnum != null) {
                sb.append(",\n      idTypeEnum: ${maker.idTypeEnum.name}")
            }

            sb.append("\n    }")
            if (index < makers.size - 1) sb.append(",")
            sb.append("\n")
        }
        sb.append("  ]\n}")

        return sb.toString()
    }
}