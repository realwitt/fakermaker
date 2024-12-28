package elias.fakerMaker.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class MakerEnum(@JsonValue val prettyName: String) {
    NAME_FIRST("First Name"),
    NAME_LAST("Last Name"),
    NAME_COMPANY("Company Name"),
    EMAIL("Email"),
    PHONE("Phone Number"),
    ADDRESS("Address"),
    ADDRESS_2("Address 2"),
    STATE("State"),
    CITY("City"),
    ZIP("Zip"),
    NUMBER_PRICE("Price"),
    NUMBER_REGULAR("Number"),
    DATE("Date"),
    BOOLEAN("True/False"),
    ID("Id"),
    CREDIT_CARD_NUMBER("Credit Card"),
    CREDIT_CARD_CVV("Credit Card CVV"),
}