package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.Tech
import elias.fakerMaker.fakers.books.*
import elias.fakerMaker.fakers.movies.BackToTheFuture
import elias.fakerMaker.fakers.sports.Baseball
import elias.fakerMaker.fakers.sports.Basketball
import elias.fakerMaker.fakers.tvshows.*
import elias.fakerMaker.fakers.videogames.CallOfDuty
import elias.fakerMaker.utils.WikiUtil

object NameGenerator {
    // Convert lists to sets for more efficient lookup
    private val characterNameFakers = setOf(
        FakerEnum.BACK_TO_THE_FUTURE,
        FakerEnum.BASEBALL,
        FakerEnum.BASKETBALL,
        FakerEnum.BREAKING_BAD,
        FakerEnum.CALL_OF_DUTY,
        FakerEnum.DOCTOR_WHO,
        FakerEnum.GAME_OF_THRONES,
        FakerEnum.GRAVITY_FALLS,
        FakerEnum.HARRY_POTTER,
        FakerEnum.KING_OF_THE_HILL,
        FakerEnum.LORD_OF_THE_RINGS,
        FakerEnum.MONK,
        FakerEnum.PARKS_AND_REC,
        FakerEnum.RICK_AND_MORTY,
        FakerEnum.SILICON_VALLEY,
        FakerEnum.TECH,
        FakerEnum.THE_HOBBIT,
        FakerEnum.THE_OFFICE,
        FakerEnum.THRONE_OF_GLASS
    )

    private val companyNameFakers = setOf(
        FakerEnum.GRAVITY_FALLS,
        FakerEnum.HARRY_POTTER,
        FakerEnum.KING_OF_THE_HILL,
        FakerEnum.MONK,
        FakerEnum.PARKS_AND_REC,
        FakerEnum.SILICON_VALLEY,
        FakerEnum.TECH,
        FakerEnum.THE_OFFICE
    )

    // Pre-compute character lists
    private val staticCharacterLists = mapOf(
        FakerEnum.BACK_TO_THE_FUTURE to BackToTheFuture.characters.toList(),
        FakerEnum.BASEBALL to Baseball.players.toList(),
        FakerEnum.BASKETBALL to Basketball.players.toList(),
        FakerEnum.BREAKING_BAD to BreakingBad.characters.toList(),
        FakerEnum.CALL_OF_DUTY to CallOfDuty.operators.toList(),
        FakerEnum.DOCTOR_WHO to DoctorWho.characters.toList(),
        FakerEnum.GAME_OF_THRONES to GameOfThrones.characters.toList(),
        FakerEnum.GRAVITY_FALLS to GravityFalls.characters.toList(),
        FakerEnum.HARRY_POTTER to HarryPotter.characters.toList(),
        FakerEnum.KING_OF_THE_HILL to KingOfTheHill.characters.toList(),
        FakerEnum.LORD_OF_THE_RINGS to LordOfTheRings.characters.toList(),
        FakerEnum.MONK to Monk.characters.toList(),
        FakerEnum.PARKS_AND_REC to ParksAndRec.characters.toList(),
        FakerEnum.RICK_AND_MORTY to RickAndMorty.characters.toList(),
        FakerEnum.SILICON_VALLEY to SiliconValley.characters.toList(),
        FakerEnum.TECH to Tech.people.toList(),
        FakerEnum.THE_HOBBIT to TheHobbit.characters.toList(),
        FakerEnum.THE_OFFICE to TheOffice.characters.toList(),
        FakerEnum.THRONE_OF_GLASS to ThroneOfGlass.characters.toList()
    )

    private val staticCompanyLists = mapOf(
        FakerEnum.GRAVITY_FALLS to GravityFalls.companies.toList(),
        FakerEnum.HARRY_POTTER to HarryPotter.companies.toList(),
        FakerEnum.KING_OF_THE_HILL to KingOfTheHill.companies.toList(),
        FakerEnum.MONK to Monk.companies.toList(),
        FakerEnum.PARKS_AND_REC to ParksAndRec.companies.toList(),
        FakerEnum.SILICON_VALLEY to SiliconValley.companies.toList(),
        FakerEnum.TECH to Tech.companies.toList(),
        FakerEnum.THE_OFFICE to TheOffice.companies.toList()
    )

    // Pre-compute first and last name maps for each faker
    private val precomputedFirstNames = mutableMapOf<FakerEnum, List<String>>()
    private val precomputedLastNames = mutableMapOf<FakerEnum, List<String>>()

    // Cache to store pre-generated random names for each faker
    private val nameCache = mutableMapOf<FakerEnum, Pair<String, String>>()

    private val TITLE_PREFIXES = setOf("The", "Mrs.", "Mr.")

    init {
        staticCharacterLists.forEach { (faker, names) ->
            precomputedFirstNames[faker] = names.map { name -> extractFirstName(name) }
            precomputedLastNames[faker] = names.map { name -> extractLastName(name) }
        }
    }

    private fun extractFirstName(name: String): String {
        if (name.isEmpty()) return ""

        val firstSpaceIndex = name.indexOf(' ')
        if (firstSpaceIndex == -1) return name

        val firstWord = name.substring(0, firstSpaceIndex)
        if (firstWord !in TITLE_PREFIXES) return firstWord

        val secondSpaceIndex = name.indexOf(' ', firstSpaceIndex + 1)
        return if (secondSpaceIndex == -1) {
            name.substring(firstSpaceIndex + 1)
        } else {
            name.substring(firstSpaceIndex + 1, secondSpaceIndex)
        }
    }

    private fun extractLastName(name: String): String {
        if (name.isEmpty()) return ""

        val lastSpaceIndex = name.lastIndexOf(' ')
        return if (lastSpaceIndex == -1) name else name.substring(lastSpaceIndex + 1)
    }

    private fun getOrGenerateRandomName(faker: FakerEnum): Pair<String, String>? {
        return nameCache.getOrPut(faker) {
            staticCharacterLists[faker]?.random()?.let { fullName ->
                Pair(
                    precomputedFirstNames[faker]?.random() ?: extractFirstName(fullName),
                    precomputedLastNames[faker]?.random() ?: extractLastName(fullName)
                )
            } ?: return null
        }
    }

    fun firstName(fakers: List<FakerEnum>?): DataTableItem {
        // Quick return for empty case with cached faker
        if (fakers.isNullOrEmpty()) {
            val randomFaker = characterNameFakers.random()
            val (firstName, _) = getOrGenerateRandomName(randomFaker) ?:
            return defaultDataTableItem(MakerEnum.NAME_FIRST)

            return DataTableItem(
                maker = MakerEnum.NAME_FIRST,
                fakersUsed = listOf(randomFaker),
                originalValue = staticCharacterLists[randomFaker]?.random(),
                derivedValue = firstName,
                wikiUrl = WikiUtil.createFandomWikiLink(randomFaker, firstName, false),
                influencedBy = null
            )
        }

        // For provided fakers, use the first valid one
        val validFaker = fakers.firstOrNull { it in characterNameFakers } ?: characterNameFakers.random()
        val (firstName, _) = getOrGenerateRandomName(validFaker) ?:
        return defaultDataTableItem(MakerEnum.NAME_FIRST)

        return DataTableItem(
            maker = MakerEnum.NAME_FIRST,
            fakersUsed = listOf(validFaker),
            originalValue = staticCharacterLists[validFaker]?.random(),
            derivedValue = firstName,
            wikiUrl = WikiUtil.createFandomWikiLink(validFaker, firstName, false),
            influencedBy = null
        )
    }

    fun lastName(fakers: List<FakerEnum>?): DataTableItem {
        // Quick return for empty case with cached faker
        if (fakers.isNullOrEmpty()) {
            val randomFaker = characterNameFakers.random()
            val (_, lastName) = getOrGenerateRandomName(randomFaker) ?:
            return defaultDataTableItem(MakerEnum.NAME_LAST)

            return DataTableItem(
                maker = MakerEnum.NAME_LAST,
                fakersUsed = listOf(randomFaker),
                originalValue = staticCharacterLists[randomFaker]?.random(),
                derivedValue = lastName,
                wikiUrl = WikiUtil.createFandomWikiLink(randomFaker, lastName, false),
                influencedBy = null
            )
        }

        // For provided fakers, use the first valid one
        val validFaker = fakers.firstOrNull { it in characterNameFakers } ?: characterNameFakers.random()
        val (_, lastName) = getOrGenerateRandomName(validFaker) ?:
        return defaultDataTableItem(MakerEnum.NAME_LAST)

        return DataTableItem(
            maker = MakerEnum.NAME_LAST,
            fakersUsed = listOf(validFaker),
            originalValue = staticCharacterLists[validFaker]?.random(),
            derivedValue = lastName,
            wikiUrl = WikiUtil.createFandomWikiLink(validFaker, lastName, false),
            influencedBy = null
        )
    }

    fun companyName(fakers: List<FakerEnum>?): DataTableItem {
        val validFaker = when {
            fakers.isNullOrEmpty() -> companyNameFakers.random()
            else -> fakers.firstOrNull { it in companyNameFakers } ?: companyNameFakers.random()
        }

        val companyName = staticCompanyLists[validFaker]?.random() ?:
        return defaultDataTableItem(MakerEnum.NAME_COMPANY)

        return DataTableItem(
            maker = MakerEnum.NAME_COMPANY,
            fakersUsed = listOf(validFaker),
            originalValue = companyName,
            derivedValue = companyName,
            wikiUrl = WikiUtil.createFandomWikiLink(validFaker, companyName, false),
            influencedBy = null
        )
    }

    private fun defaultDataTableItem(maker: MakerEnum) = DataTableItem(
        maker = maker,
        fakersUsed = null,
        originalValue = null,
        derivedValue = "",
        wikiUrl = null,
        influencedBy = null
    )
}