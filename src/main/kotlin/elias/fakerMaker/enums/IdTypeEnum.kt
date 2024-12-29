package elias.fakerMaker.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class IdTypeEnum(val prettyName: String) {
    @SerialName("UUID")
    UUID("UUID"),

    @SerialName("NanoID")
    NANO_ID("NanoID"),

    @SerialName("ShortID")
    SHORT_ID("ShortID"),

    @SerialName("NumericID")
    NUMERIC_ID("NumericID"),

    @SerialName("ObjectID")
    OBJECT_ID("ObjectID");

}