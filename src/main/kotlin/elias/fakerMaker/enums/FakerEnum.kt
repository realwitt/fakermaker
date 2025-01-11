package elias.fakerMaker.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class FakerEnum(val prettyName: String) {
    @SerialName("Back to the Future")
    BACK_TO_THE_FUTURE("Back to the Future"),

    @SerialName("Baseball")
    BASEBALL("Baseball"),

    @SerialName("Basketball")
    BASKETBALL("Basketball"),

    @SerialName("Breaking Bad")
    BREAKING_BAD("Breaking Bad"),

    @SerialName("Call of Duty")
    CALL_OF_DUTY("Call of Duty"),

    @SerialName("Clash of Clans")
    CLASH_OF_CLANS("Clash of Clans"),

    @SerialName("Doctor Who")
    DOCTOR_WHO("Doctor Who"),

    @SerialName("Game of Thrones")
    GAME_OF_THRONES("Game of Thrones"),

    @SerialName("Gravity Falls")
    GRAVITY_FALLS("Gravity Falls"),

    @SerialName("Harry Potter")
    HARRY_POTTER("Harry Potter"),

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

    @SerialName("King of the Hill")
    KING_OF_THE_HILL("King of the Hill"),

    @SerialName("Lord of the Rings")
    LORD_OF_THE_RINGS("Lord of the Rings"),

    @SerialName("Monk")
    MONK("Monk"),

    @SerialName("Nacho Libre")
    NACHO_LIBRE("Nacho Libre"),

    @SerialName("Parks and Recreation")
    PARKS_AND_REC("Parks and Recreation"),

    @SerialName("Pirates of the Caribbean")
    PIRATES_OF_THE_CARIBBEAN("Pirates of the Caribbean"),

    @SerialName("Pokemon")
    POKEMON("Pokemon"),

    @SerialName("Rango")
    RANGO("Rango"),

    @SerialName("Rick and Morty")
    RICK_AND_MORTY("Rick and Morty"),

    @SerialName("Silicon Valley")
    SILICON_VALLEY("Silicon Valley"),

    @SerialName("Star Wars")
    STAR_WARS("Star Wars"),

    @SerialName("Tech")
    TECH("Tech"),

    @SerialName("The Hobbit")
    THE_HOBBIT("The Hobbit"),

    @SerialName("The Office")
    THE_OFFICE("The Office"),

    @SerialName("Throne of Glass")
    THRONE_OF_GLASS("Throne of Glass"),

    @SerialName("Toy Story")
    TOY_STORY("Toy Story"),

    @SerialName("Transformers")
    TRANSFORMERS("Transformers");
}