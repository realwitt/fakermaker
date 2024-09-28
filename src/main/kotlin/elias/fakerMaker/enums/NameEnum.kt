package elias.fakerMaker.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class NameEnum(@JsonValue val description: String) {
    FIRST("first"),
    LAST("last"),
    COMPANY("company"),
}