package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import net.datafaker.Faker
import kotlin.random.Random

class NameGenerator {
    private val dataFaker: Faker = Faker()
    private val rand: Random = Random

    // todo: make pr in datafaker to add this
    val kingOfTheHill = listOf(
        "Hank Hill",
        "Peggy Hill",
        "Bobby Hill",
        "Cotton Hill",
        "Didi Hill",
        "Tilly Garrison",
        "G.H. Hill",
        "Michiko",
        "Junichiro",
        "Ladybird",
        "Chuck Garrison",
        "Gary Kasner",
        "Luanne Leanne Kleinschmidt Platter",
        "Hoyt Platter",
        "Leanne Platter",
        "Lucky Kleinschmidt",
        "Buckley",
        "June Kremzer",
        "Elvin Mackleston",
        "Mud Dobber",
        "Dusty Hill",
        "Dale Alvin Gribble",
        "Nancy Hicks-Gribble",
        "Joseph Gribble",
        "Bug Gribble",
        "John Redcorn",
        "Rusty Shackleford",
        "Juan Pedro",
        "Jeff Boomhauer III",
        "Katherine Hester",
        "Meemaw",
        "Patch Boomhauer",
        "Marlene",
        "William Fontaine de la Tour Dauterive",
        "Bill Dauterive",
        "Marilyn",
        "Ann Richards",
        "Cyndi Beauchamp",
        "Charlene",
        "Violetta Dauterive",
        "Gilbert Dauterive",
        "Girac Dauterive",
        "Rene Dautrive",
        "Esme Dauterive",
        "Alphonse Dauterive",
        "Wally",
        "Kohng Koy Souphanousinphone",
        "Minh Souphanousinphone",
        "Connie Souphanousinphone",
        "Tid Pao Souphanousinphone",
        "Chane Wassanasong",
        "Ted Wassanasong",
        "Cindy Wassanasong",
        "Mr. Tranh",
        "Mr. Ho",
        "Laoma Souphanousinphone",
        "Buck Strickland",
        "Elizabeth Strickland",
        "Jody Rayroy Strickland",
        "Randy Strickland"
    )

    private fun nameGenerator(fakers: List<FakerEnum>): MutableMap<FakerEnum, String> {
        val namesMap = emptyMap<FakerEnum, String>().toMutableMap()
        for (faker in fakers) {
            when (faker) {
                FakerEnum.BACK_TO_THE_FUTURE -> namesMap[FakerEnum.BACK_TO_THE_FUTURE] = dataFaker.backToTheFuture().character()
                FakerEnum.BREAKING_BAD -> namesMap[FakerEnum.BREAKING_BAD] = dataFaker.breakingBad().character()
                FakerEnum.CLASH_OF_CLANS -> namesMap[FakerEnum.CLASH_OF_CLANS] = dataFaker.clashOfClans().troop()
                FakerEnum.DOCTOR_WHO -> namesMap[FakerEnum.DOCTOR_WHO] = dataFaker.doctorWho().character()
                FakerEnum.GAME_OF_THRONES -> namesMap[FakerEnum.GAME_OF_THRONES] = dataFaker.gameOfThrones().character()
                FakerEnum.HARRY_POTTER -> namesMap[FakerEnum.HARRY_POTTER] = dataFaker.harryPotter().character()
                FakerEnum.KING_OF_THE_HILL -> namesMap[FakerEnum.KING_OF_THE_HILL] = kingOfTheHill.random()
                FakerEnum.LORD_OF_THE_RINGS -> namesMap[FakerEnum.LORD_OF_THE_RINGS] = dataFaker.lordOfTheRings().character()
                FakerEnum.RICK_AND_MORTY -> namesMap[FakerEnum.RICK_AND_MORTY] = dataFaker.rickAndMorty().character()
                FakerEnum.SILICON_VALLEY -> namesMap[FakerEnum.SILICON_VALLEY] = dataFaker.siliconValley().character()
                FakerEnum.BASEBALL -> namesMap[FakerEnum.BASEBALL] = dataFaker.baseball().players()
                FakerEnum.BASKETBALL -> namesMap[FakerEnum.BASKETBALL] = dataFaker.baseball().players()
            }
        }
        return namesMap
    }

    private fun getFirstNamesOnly(names: Map<FakerEnum, String>): Map<FakerEnum, String> = names.mapValues { (_, name) ->
        name.split(" ").let { parts ->
            when {
                name.startsWith("The ") || name.startsWith("Mrs. ") || name.startsWith("Mr. ") -> parts.lastOrNull()
                    ?: parts.first()

                else -> parts.first()
            }
        }
    }

    private fun getLastNamesOnly(names: Map<FakerEnum, String>): Map<FakerEnum, String> = names.mapValues { (_, name) ->
        name.split(" ").let { parts ->
            when {
                name.startsWith("The ") || name.startsWith("Mrs. ") || name.startsWith("Mr. ") -> parts.lastOrNull()
                    ?: parts.first()

                else -> parts.last()
            }
        }
    }


    fun getFandomUrl(fakerEnum: FakerEnum, fullName: String, concatEntireName: Boolean): String {
        // allow more fine-grained control over which wiki we want to pull from
        // ...and bc fandom's Silicon Valley link is "silicon-valley" instead of "siliconvalley" -_-
        val fandomBaseUrl = when (fakerEnum) {
            FakerEnum.SILICON_VALLEY -> "silicon-valley.fandom.com/wiki/"
            FakerEnum.GAME_OF_THRONES -> "awoiaf.westeros.org/index.php/"
            else -> "${fakerEnum.toString().replace("_", "").lowercase()}.fandom.com/wiki/"
        }
        val firstName = fullName.split(" ").first()
        val lastName = fullName.split(" ").last()
        // unlikes names where we only want first and last, for locations we want the whole name
        val fandomQueryParam = when (concatEntireName) {
            true -> fullName.replace(" ", "_")
            false -> {if (firstName == lastName) firstName else "${firstName}_${lastName}"}
        }
        return "https://$fandomBaseUrl$fandomQueryParam"
    }

    fun generateRandomFirstName(fakers: List<FakerEnum>?): DataTableItem {
        if (fakers.isNullOrEmpty()) {
            return DataTableItem(
                dataFaker.name().firstName(),
                null,
                null,
                null,
                null
            )
        }
        val names = nameGenerator(fakers)
        val firstNames = getFirstNamesOnly(names)
        val randomFirstName = firstNames.toList().random()
        return DataTableItem(
            randomFirstName.second,
            MakerEnum.NAME_FIRST,
            randomFirstName.first,
            names[randomFirstName.first],
            getFandomUrl(randomFirstName.first, names[randomFirstName.first]!!, false)
        )
    }

    fun generateRandomLastName(fakers: List<FakerEnum>?): DataTableItem {
        if (fakers.isNullOrEmpty()) {
            return DataTableItem(
                dataFaker.name().lastName(),
                null,
                null,
                null,
                null
            )
        }
        val names = nameGenerator(fakers)
        val lastNames = getLastNamesOnly(names)
        val randomLastName = lastNames.toList().random()
        return DataTableItem(
            randomLastName.second,
            MakerEnum.NAME_LAST,
            randomLastName.first,
            names[randomLastName.first],
            getFandomUrl(randomLastName.first, names[randomLastName.first]!!, false)
        )
    }

}