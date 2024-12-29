package elias.fakerMaker.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class FakerEnum(val prettyName: String) {
    // Books
    @SerialName("Game of Thrones")
    GAME_OF_THRONES("Game of Thrones"),

    @SerialName("Harry Potter")
    HARRY_POTTER("Harry Potter"),

    @SerialName("Lord of the Rings")
    LORD_OF_THE_RINGS("Lord of the Rings"),

    @SerialName("The Hobbit")
    THE_HOBBIT("The Hobbit"),

    // Movies
    @SerialName("Back to the Future")
    BACK_TO_THE_FUTURE("Back to the Future"),

    @SerialName("How to Train Your Dragon")
    HOW_TO_TRAIN_YOUR_DRAGON("How to Train Your Dragon"),

    @SerialName("Idiocracy")
    IDIOCRACY("Idiocracy"),

    @SerialName("Indiana Jones")
    INDIANA_JONES("Indiana Jones"),

    @SerialName("Iron Man")
    IRON_MAN("Iron Man"),

    @SerialName("Jurassic Park")
    JURASSIC_PARK("Jurassic Park"),

    @SerialName("Nacho Libre")
    NACHO_LIBRE("Nacho Libre"),

    @SerialName("Pirates of the Caribbean")
    PIRATES_OF_THE_CARIBBEAN("Pirates of the Caribbean"),

    @SerialName("Rango")
    RANGO("Rango"),

    @SerialName("Star Wars")
    STAR_WARS("Star Wars"),

    @SerialName("Toy Story")
    TOY_STORY("Toy Story"),

    @SerialName("Transformers")
    TRANSFORMERS("Transformers"),

    // TV Shows
    @SerialName("Breaking Bad")
    BREAKING_BAD("Breaking Bad"),

    @SerialName("Doctor Who")
    DOCTOR_WHO("Doctor Who"),

    @SerialName("Monk")
    MONK("Monk"),

    @SerialName("Gravity Falls")
    GRAVITY_FALLS("Gravity Falls"),

    @SerialName("King of the Hill")
    KING_OF_THE_HILL("King of the Hill"),

    @SerialName("Parks and Recreation")
    PARKS_AND_REC("Parks and Recreation"),

    @SerialName("Pokemon")
    POKEMON("Pokemon"),

    @SerialName("Rick and Morty")
    RICK_AND_MORTY("Rick and Morty"),

    @SerialName("Silicon Valley")
    SILICON_VALLEY("Silicon Valley"),

    @SerialName("The Office")
    THE_OFFICE("The Office"),

    @SerialName("Throne of Glass")
    THRONE_OF_GLASS("Throne of Glass"),

    // Sports
    @SerialName("Baseball")
    BASEBALL("Baseball"),

    @SerialName("Basketball")
    BASKETBALL("Basketball"),

    // Video Games
    @SerialName("Call of Duty")
    CALL_OF_DUTY("Call of Duty"),

    @SerialName("Clash of Clans")
    CLASH_OF_CLANS("Clash of Clans"),

    // Miscellaneous
    @SerialName("Tech")
    TECH("Tech");

}