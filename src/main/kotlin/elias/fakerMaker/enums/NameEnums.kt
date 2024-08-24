package elias.fakerMaker.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class NameEnums(@JsonValue val description: String) {
    FIRST("first"),
    LAST("last"),
    COMPANY("company"),
}