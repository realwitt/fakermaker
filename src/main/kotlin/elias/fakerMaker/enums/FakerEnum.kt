package elias.fakerMaker.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class FakerEnum(@JsonValue val prettyName: String) {
    // books
    GAME_OF_THRONES("Game of Thrones"),
    HARRY_POTTER("Harry Potter"),
    LORD_OF_THE_RINGS("Lord of the Rings"),
    THE_HOBBIT("The Hobbit"),

    // movies
    BACK_TO_THE_FUTURE("Back to the Future"),
    HOW_TO_TRAIN_YOUR_DRAGON("How to Train Your Dragon"),
    IDIOCRACY("Idiocracy"),
    INDIANA_JONES("Indiana Jones"),
    IRON_MAN("Iron Man"),
    JURASSIC_PARK("Jurassic Park"),
    NACHO_LIBRE("Nacho Libre"),
    PIRATES_OF_THE_CARIBBEAN("Pirates of the Caribbean"),
    RANGO("Rango"),
    STAR_WARS("Star Wars"),
    TOY_STORY("Toy Story"),
    TRANSFORMERS("Transformers"),

    // tv shows
    BREAKING_BAD("Breaking Bad"),
    DOCTOR_WHO("Doctor Who"),
    MONK("Monk"),
    GRAVITY_FALLS("Gravity Falls"),
    KING_OF_THE_HILL("King of the Hill"),
    PARKS_AND_REC("Parks and Recreation"),
    POKEMON("Pokemon"),
    RICK_AND_MORTY("Rick and Morty"),
    SILICON_VALLEY("Silicon Valley"),
    THE_OFFICE("The Office"),
    THRONE_OF_GLASS("Throne of Glass"),

    // sports
    BASEBALL("Baseball"),
    BASKETBALL("Basketball"),

    // video games
    CALL_OF_DUTY("Call of Dual"),
    CLASH_OF_CLANS("Clash of Clans"),

    // misc
    TECH("Tech"),


}
