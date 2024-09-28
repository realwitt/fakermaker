package elias.fakerMaker.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class FakerEnums(@JsonValue val description: String) {
    KING_OF_THE_HILL("King of the Hill"),
    CLASH_OF_CLANS("Clash of Clans"),
    HARRY_POTTER("Harry Potter"),
    BREAKING_BAD("Breaking Bad"),
    BACK_TO_THE_FUTURE("Back to the Future"),
    DOCTOR_WHO("Doctor Who"),
    GAME_OF_THRONES("Game of Thrones"),
    LORD_OF_THE_RINGS("Lord of the Rings"),
    SILICON_VALLEY("Silicon Valley"),
    BASEBALL("Baseball"),
    BASKETBALL("Basketball"),
}