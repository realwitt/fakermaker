package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.*
import elias.fakerMaker.fakers.book.HarryPotter
import elias.fakerMaker.fakers.book.ThroneOfGlass
import elias.fakerMaker.fakers.tvshow.*
import elias.fakerMaker.fakers.videogame.CallOfDuty
import net.datafaker.Faker
import kotlin.random.Random

class NameGenerator {
    private val dataFaker: Faker = Faker()
    private val rand: Random = Random

    private fun nameGenerator(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val namesMap = emptyMap<FakerEnum, String>().toMutableMap()
        for (faker in fakers) {
            when (faker) {
                FakerEnum.BACK_TO_THE_FUTURE -> namesMap[FakerEnum.BACK_TO_THE_FUTURE] = dataFaker.backToTheFuture().character()
                FakerEnum.BASEBALL -> namesMap[FakerEnum.BASEBALL] = dataFaker.baseball().players()
                FakerEnum.BASKETBALL -> namesMap[FakerEnum.BASKETBALL] = dataFaker.baseball().players()
                FakerEnum.BREAKING_BAD -> namesMap[FakerEnum.BREAKING_BAD] = dataFaker.breakingBad().character()
                FakerEnum.CALL_OF_DUTY -> namesMap[FakerEnum.CALL_OF_DUTY] = CallOfDuty.operators.random()
                FakerEnum.CLASH_OF_CLANS -> namesMap[FakerEnum.CLASH_OF_CLANS] = dataFaker.clashOfClans().troop()
                FakerEnum.DOCTOR_WHO -> namesMap[FakerEnum.DOCTOR_WHO] = dataFaker.doctorWho().character()
                FakerEnum.GAME_OF_THRONES -> namesMap[FakerEnum.GAME_OF_THRONES] = dataFaker.gameOfThrones().character()
                FakerEnum.GRAVITY_FALLS -> namesMap[FakerEnum.GRAVITY_FALLS] = GravityFalls.characters.random()
                FakerEnum.HARRY_POTTER -> namesMap[FakerEnum.HARRY_POTTER] = dataFaker.harryPotter().character()
                FakerEnum.KING_OF_THE_HILL -> namesMap[FakerEnum.KING_OF_THE_HILL] = KingOfTheHill.characters.random()
                FakerEnum.LORD_OF_THE_RINGS -> namesMap[FakerEnum.LORD_OF_THE_RINGS] = dataFaker.lordOfTheRings().character()
                FakerEnum.MONK -> namesMap[FakerEnum.MONK] = Monk.characters.random()
                FakerEnum.PARKS_AND_REC -> namesMap[FakerEnum.PARKS_AND_REC] = ParksAndRec.characters.random()
                FakerEnum.RICK_AND_MORTY -> namesMap[FakerEnum.RICK_AND_MORTY] = dataFaker.rickAndMorty().character()
                FakerEnum.SILICON_VALLEY -> namesMap[FakerEnum.SILICON_VALLEY] = dataFaker.siliconValley().character()
                FakerEnum.TECH -> namesMap[FakerEnum.TECH] = Tech.people.random()
                FakerEnum.THE_OFFICE -> namesMap[FakerEnum.THE_OFFICE] = TheOffice.characters.random()
                FakerEnum.THRONE_OF_GLASS -> namesMap[FakerEnum.THRONE_OF_GLASS] = ThroneOfGlass.characters.random()
                else -> continue
            }
        }
        return namesMap.toMap()
    }

    private fun companyGenerator(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val namesMap = emptyMap<FakerEnum, String>().toMutableMap()
        for (faker in fakers) {
            when (faker) {
                FakerEnum.GRAVITY_FALLS -> namesMap[FakerEnum.GRAVITY_FALLS] = GravityFalls.companies.random()
                FakerEnum.HARRY_POTTER -> namesMap[FakerEnum.HARRY_POTTER] = HarryPotter.companies.random()
                FakerEnum.KING_OF_THE_HILL -> namesMap[FakerEnum.KING_OF_THE_HILL] = KingOfTheHill.companies.random()
                FakerEnum.MONK -> namesMap[FakerEnum.MONK] = Monk.companies.random()
                FakerEnum.SILICON_VALLEY -> namesMap[FakerEnum.SILICON_VALLEY] = dataFaker.siliconValley().company()
                FakerEnum.PARKS_AND_REC -> namesMap[FakerEnum.PARKS_AND_REC] = ParksAndRec.companies.random()
                FakerEnum.TECH -> namesMap[FakerEnum.TECH] = Tech.companies.random()
                FakerEnum.THE_OFFICE -> namesMap[FakerEnum.THE_OFFICE] = TheOffice.companies.random()
                else -> continue
            }
        }
        return namesMap.toMap()
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
        // todo: make this a util, rename to wikiGenerator, and add a wikipedia url generator
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

    fun firstName(fakers: List<FakerEnum>?): DataTableItem {
        if (fakers.isNullOrEmpty()) {
            return DataTableItem()
        }
        val names = nameGenerator(fakers)
        val firstNames = getFirstNamesOnly(names)
        val randomFirstName = firstNames.toList().random()
        return DataTableItem(
            MakerEnum.NAME_FIRST,
            randomFirstName.first,
            names[randomFirstName.first],
            randomFirstName.second,
            getFandomUrl(randomFirstName.first, names[randomFirstName.first]!!, false)
        )
    }

    fun lastName(fakers: List<FakerEnum>?): DataTableItem {
        if (fakers.isNullOrEmpty()) {
            return DataTableItem(
                MakerEnum.NAME_LAST,
                null,
                null,
                dataFaker.name().lastName(),
                null
            )
        }
        val names = nameGenerator(fakers)
        val lastNames = getLastNamesOnly(names)
        val randomLastName = lastNames.toList().random()
        return DataTableItem(
            MakerEnum.NAME_LAST,
            randomLastName.first,
            names[randomLastName.first],
            randomLastName.second,
            getFandomUrl(randomLastName.first, names[randomLastName.first]!!, false)
        )
    }

    fun companyName(fakers: List<FakerEnum>?): DataTableItem {
        if (fakers.isNullOrEmpty()) {
            val randomCompany = Tech.companies.random()
            return DataTableItem(
                MakerEnum.NAME_COMPANY,
                FakerEnum.TECH,
                randomCompany,
                randomCompany,
                null
            )
        }
        val names = companyGenerator(fakers)
        val name = names.entries.random()
        return DataTableItem(
            MakerEnum.NAME_COMPANY,
            name.key,
            name.value,
            name.value,
            ""
        )
    }
}