package elias.fakerMaker.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MakerEnum(val prettyName: String) {
    @SerialName("First Name")
    NAME_FIRST("First Name"),

    @SerialName("Last Name")
    NAME_LAST("Last Name"),

    @SerialName("Company Name")
    NAME_COMPANY("Company Name"),

    @SerialName("Email")
    EMAIL("Email"),

    @SerialName("Phone Number")
    PHONE("Phone Number"),

    @SerialName("Address")
    ADDRESS("Address"),

    @SerialName("Address 2")
    ADDRESS_2("Address 2"),

    @SerialName("State")
    STATE("State"),

    @SerialName("City")
    CITY("City"),

    @SerialName("Zip")
    ZIP("Zip"),

    @SerialName("Price")
    NUMBER_PRICE("Price"),

    @SerialName("Number")
    NUMBER_REGULAR("Number"),

    @SerialName("Date")
    DATE("Date"),

    @SerialName("True/False")
    BOOLEAN("True/False"),

    @SerialName("Id")
    ID("Id"),

    @SerialName("Credit Card")
    CREDIT_CARD_NUMBER("Credit Card"),

    @SerialName("Credit Card CVV")
    CREDIT_CARD_CVV("Credit Card CVV");

    override fun toString(): String = name
}