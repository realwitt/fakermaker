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
                FakerEnum.BACK_TO_THE_FUTURE -> namesMap[FakerEnum.BACK_TO_THE_FUTURE] =
                    dataFaker.backToTheFuture().character()
                FakerEnum.BREAKING_BAD -> namesMap[FakerEnum.BREAKING_BAD] = dataFaker.breakingBad().character()
                FakerEnum.CLASH_OF_CLANS -> namesMap[FakerEnum.CLASH_OF_CLANS] = dataFaker.clashOfClans().troop()
                FakerEnum.DOCTOR_WHO -> namesMap[FakerEnum.DOCTOR_WHO] = dataFaker.doctorWho().character()
                FakerEnum.GAME_OF_THRONES -> namesMap[FakerEnum.GAME_OF_THRONES] = dataFaker.gameOfThrones().character()
                FakerEnum.HARRY_POTTER -> namesMap[FakerEnum.HARRY_POTTER] = dataFaker.harryPotter().character()
                FakerEnum.KING_OF_THE_HILL -> namesMap[FakerEnum.KING_OF_THE_HILL] =
                    kingOfTheHill[rand.nextInt(kingOfTheHill.size)]
                FakerEnum.LORD_OF_THE_RINGS -> namesMap[FakerEnum.LORD_OF_THE_RINGS] =
                    dataFaker.lordOfTheRings().character()
                FakerEnum.SILICON_VALLEY -> namesMap[FakerEnum.SILICON_VALLEY] = dataFaker.siliconValley().character()
                FakerEnum.BASEBALL -> namesMap[FakerEnum.BASEBALL] = dataFaker.baseball().players()
                FakerEnum.BASKETBALL -> namesMap[FakerEnum.BASKETBALL] = dataFaker.baseball().players()
            }
        }
        return namesMap
    }

    private fun getFirstNamesOnly(names: MutableMap<FakerEnum, String>): Map<FakerEnum, String> {
        return names.mapValues { (_, name) ->
            when {
                name.startsWith("The ") || name.startsWith("Mrs. ") || name.startsWith("Mr. ") ->
                    name.split(" ").getOrNull(1) ?: name

                else -> name.split(" ").first()
            }
        }
    }

    private fun getLastNamesOnly(names: MutableMap<FakerEnum, String>): Map<FakerEnum, String> {
        return names.mapValues { (_, name) ->
            when {
                name.startsWith("The ") || name.startsWith("Mrs. ") || name.startsWith("Mr. ") ->
                    name.split(" ").getOrNull(-1) ?: name.split(" ")[0]

                else -> name.split(" ").last()
            }
        }
    }

    private fun getFandomUrl(incomingName: Pair<FakerEnum, String>, fullName: String): String {
        // really only did this bc siliconvalley's fandom baseurl is "silicon-valley" not "siliconvalley"
        val fandomBaseUrl = when (incomingName.first) {
            FakerEnum.SILICON_VALLEY -> "silicon-valley.fandom.com/wiki/"
            FakerEnum.GAME_OF_THRONES -> "awoiaf.westeros.org/index.php/"
            else -> "${incomingName.first.toString().replace("_", "").lowercase()}.fandom.com/wiki/"
        }
        val firstName = fullName.split(" ").first()
        val lastName = fullName.split(" ").last()
        return "https://$fandomBaseUrl${if (firstName === lastName) firstName else "${firstName}_${lastName}"}"
    }

    fun generateRandomFirstName(fakers: List<FakerEnum>): DataTableItem {
        val names = nameGenerator(fakers)
        val firstNames = getFirstNamesOnly(names)
        val randomFirstName = firstNames.toList()[rand.nextInt(names.size)]
        return DataTableItem(
            randomFirstName.second,
            MakerEnum.NAME_FIRST,
            randomFirstName.first,
            names[randomFirstName.first],
            getFandomUrl(randomFirstName, names[randomFirstName.first]!!)
        )
    }

    fun generateRandomLastName(fakers: List<FakerEnum>): DataTableItem {
        val names = nameGenerator(fakers)
        val lastNames = getLastNamesOnly(names)
        val randomLastName = lastNames.toList()[rand.nextInt(names.size)]
        return DataTableItem(
            randomLastName.second,
            MakerEnum.NAME_LAST,
            randomLastName.first,
            names[randomLastName.first],
            getFandomUrl(randomLastName, names[randomLastName.first]!!)
        )
    }

    // todo: make one for company

}