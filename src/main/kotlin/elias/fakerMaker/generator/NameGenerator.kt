package elias.fakerMaker.generator

import elias.fakerMaker.enums.FakerEnum
import net.datafaker.Faker
import kotlin.random.Random

class NameDtoGenerator {


    private val dataFaker: Faker = Faker()
    private val rand: Random = Random

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

    fun nameGenerator(fakers: List<FakerEnum>): MutableMap<FakerEnum, String> {
        val namesMap = emptyMap<FakerEnum, String>().toMutableMap()
        for (faker in fakers) {
            when (faker) {
                FakerEnum.KING_OF_THE_HILL -> namesMap[FakerEnum.KING_OF_THE_HILL] =
                    kingOfTheHill[rand.nextInt(kingOfTheHill.size)]

                FakerEnum.BREAKING_BAD -> namesMap[FakerEnum.BREAKING_BAD] = dataFaker.breakingBad().character()
                FakerEnum.HARRY_POTTER -> namesMap[FakerEnum.HARRY_POTTER] = dataFaker.harryPotter().character()
                else -> {}
            }
        }
        return namesMap
    }

    fun getFirstNames(names: MutableMap<FakerEnum, String>): Map<FakerEnum, String> {
        val firstNames = mutableMapOf<FakerEnum, String>()
        for ((faker, name) in names) {
            if (name.startsWith("The") || name.startsWith("Mrs.") || name.startsWith("Mr.")) {
                try {
                    firstNames[faker] = name.split(" ")[1]
                } catch (e: IndexOutOfBoundsException) {
                    // if for some
                    continue
                }
            } else firstNames[faker] = name.split(" ")[0]
        }
        return firstNames
    }




    fun getLastNames(names: MutableMap<FakerEnum, String>): Map<FakerEnum, String> {
        val lastNames = mutableMapOf<FakerEnum, String>()
        for ((faker, name) in names) {
            if ((name.startsWith("The") || name.startsWith("Mrs.") || name.startsWith("Mr.")) || name.split(" ").size > 1) {
                try {
                    lastNames[faker] = name.split(" ")[-1]
                } catch (e: IndexOutOfBoundsException) {
                    continue
                }
            } else lastNames[faker] = name.split(" ")[0]
        }
        return lastNames
    }


    val names = nameGenerator(listOf(FakerEnum.BREAKING_BAD, FakerEnum.KING_OF_THE_HILL))
    val firstNames = getFirstNames(names)
    val lastNames = getLastNames(names)


//    private val nameGenerator: NameGenerator = NameGenerator()
//    private val rand: Random = Random

//    fun generate() : NameDto {
//        val name = NameDto()
//        // todo make the name generator util work better
//        name.nickName = "random" + rand.nextInt(100)
//        name.isNullable = rand.nextBoolean()
//        name.options = listOf(RandomEnum.randomEnum<NameEnums>().description)
//
//        return name
//    }
}