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

    private fun getOrGenerateRandomName(faker: FakerEnum): Pair<String, String> {
        return nameCache.getOrPut(faker) {
            staticCharacterLists[faker]?.random()?.let { fullName ->
                Pair(
                    precomputedFirstNames[faker]?.random() ?: extractFirstName(fullName),
                    precomputedLastNames[faker]?.random() ?: extractLastName(fullName)
                )
            } ?: throw IllegalStateException("Failed to find character list for faker: ${faker.prettyName}")
        }
    }

    // use caching to store the current request's fakers so we can have faster lookups
    // this is tightly coupled to nameGenerator and shouldn't be accessed
    private class ValidFakersCache {
        private var characterFakersCache: Pair<List<FakerEnum>?, Set<FakerEnum>>? = null
        private var companyFakersCache: Pair<List<FakerEnum>?, Set<FakerEnum>>? = null

        fun getValidCharacterFakers(fakers: List<FakerEnum>?): Set<FakerEnum> {
            // Check if we have a cached result for this exact list
            characterFakersCache?.let { (cachedInput, cachedResult) ->
                if (cachedInput == fakers) return cachedResult
            }

            // Calculate and cache the result
            val result = if (fakers.isNullOrEmpty()) {
                characterNameFakers
            } else {
                characterNameFakers.intersect(fakers.toSet()).also {
                    if (it.isEmpty()) {
                        throw IllegalArgumentException("No valid character name faker found in provided list: ${fakers.joinToString()}")
                    }
                }
            }

            characterFakersCache = fakers to result
            return result
        }

        fun getValidCompanyFakers(fakers: List<FakerEnum>?): Set<FakerEnum> {
            // Check if we have a cached result for this exact list
            companyFakersCache?.let { (cachedInput, cachedResult) ->
                if (cachedInput == fakers) return cachedResult
            }

            // Calculate and cache the result
            val result = if (fakers.isNullOrEmpty()) {
                companyNameFakers
            } else {
                companyNameFakers.intersect(fakers.toSet()).also {
                    if (it.isEmpty()) {
                        throw IllegalArgumentException("No valid company name faker found in provided list: ${fakers.joinToString()}")
                    }
                }
            }

            companyFakersCache = fakers to result
            return result
        }

        fun clear() {
            characterFakersCache = null
            companyFakersCache = null
        }
    }

    // ThreadLocal to ensure thread safety for concurrent requests
    private val validFakersCache = ThreadLocal.withInitial { ValidFakersCache() }

    fun firstName(fakers: List<FakerEnum>?): DataTableItem {
        val validFaker = validFakersCache.get().getValidCharacterFakers(fakers).random()
        val (firstName, _) = getOrGenerateRandomName(validFaker)
        val fullName = staticCharacterLists[validFaker]?.random()
            ?: throw IllegalStateException("Unable to find character list for faker: ${validFaker.prettyName}")

        return DataTableItem(
            maker = MakerEnum.NAME_FIRST,
            fakersUsed = listOf(validFaker),
            originalValue = fullName,
            derivedValue = firstName,
            wikiUrl = WikiUtil.createFandomWikiLink(validFaker, fullName, false),
            influencedBy = null
        )
    }

    fun lastName(fakers: List<FakerEnum>?): DataTableItem {
        val validFaker = validFakersCache.get().getValidCharacterFakers(fakers).random()
        val fullName = staticCharacterLists[validFaker]?.random()
            ?: throw IllegalStateException("Unable to find character list for faker: ${validFaker.prettyName}")

        val lastName = extractLastName(fullName)

        return DataTableItem(
            maker = MakerEnum.NAME_LAST,
            fakersUsed = listOf(validFaker),
            originalValue = fullName,
            derivedValue = lastName,
            wikiUrl = WikiUtil.createFandomWikiLink(validFaker, fullName, false),
            influencedBy = null
        )
    }

    fun companyName(fakers: List<FakerEnum>?): DataTableItem {
        val validFaker = validFakersCache.get().getValidCompanyFakers(fakers).random()
        val companyName = staticCompanyLists[validFaker]?.random()
            ?: throw IllegalStateException("Unable to find company list for faker: ${validFaker.prettyName}")

        return DataTableItem(
            maker = MakerEnum.NAME_COMPANY,
            fakersUsed = listOf(validFaker),
            originalValue = companyName,
            derivedValue = companyName,
            wikiUrl = WikiUtil.createFandomWikiLink(validFaker, companyName, true),
            influencedBy = null
        )
    }

    // Call this at the end of processing each CSV request
    fun clearCache() {
        validFakersCache.get().clear()
    }
}