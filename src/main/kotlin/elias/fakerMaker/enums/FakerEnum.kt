package elias.fakerMaker.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class FakerEnum(@JsonValue val description: String) {
    BACK_TO_THE_FUTURE("Back to the Future"),
    BREAKING_BAD("Breaking Bad"),
    CLASH_OF_CLANS("Clash of Clans"),
    DOCTOR_WHO("Doctor Who"),
    GAME_OF_THRONES("Game of Thrones"),
    HARRY_POTTER("Harry Potter"),
    KING_OF_THE_HILL("King of the Hill"),
    LORD_OF_THE_RINGS("Lord of the Rings"),
    SILICON_VALLEY("Silicon Valley"),
    BASEBALL("Baseball"),
    BASKETBALL("Basketball"),
}