package elias.fakerMaker.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class FakerEnum(@JsonValue val prettyName: String) {
    BACK_TO_THE_FUTURE("Back to the Future"),
    BASEBALL("Baseball"),
    BASKETBALL("Basketball"),
    BREAKING_BAD("Breaking Bad"),
    CALL_OF_DUTY("Call of Dual"),
    CLASH_OF_CLANS("Clash of Clans"),
    DOCTOR_WHO("Doctor Who"),
    GAME_OF_THRONES("Game of Thrones"),
    GRAVITY_FALLS("Gravity Falls"),
    HARRY_POTTER("Harry Potter"),
    KING_OF_THE_HILL("King of the Hill"),
    LORD_OF_THE_RINGS("Lord of the Rings"),
    MONK("Monk"),
    PARKS_AND_REC("Parks and Recreation"),
    POKEMON("Pokemon"),
    RICK_AND_MORTY("Rick and Morty"),
    SILICON_VALLEY("Silicon Valley"),
    TECH("Tech"),
    THE_OFFICE("The Office"),
    THE_HOBBIT("The Hobbit"),
    THRONE_OF_GLASS("Throne of Glass"),
}
