package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.*
import elias.fakerMaker.fakers.books.*
import elias.fakerMaker.fakers.movies.BackToTheFuture
import elias.fakerMaker.fakers.sports.Baseball
import elias.fakerMaker.fakers.sports.Basketball
import elias.fakerMaker.fakers.tvshow.*
import elias.fakerMaker.fakers.videogame.CallOfDuty
import elias.fakerMaker.utils.WikiUtil

object NameGenerator {
    private val characterNameFakers = listOf(
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
        FakerEnum.THRONE_OF_GLASS,
    )

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
        FakerEnum.THRONE_OF_GLASS to ThroneOfGlass.characters.toList(),
    )

    private val companyNameFakers = listOf(
        FakerEnum.GRAVITY_FALLS,
        FakerEnum.HARRY_POTTER,
        FakerEnum.KING_OF_THE_HILL,
        FakerEnum.MONK,
        FakerEnum.PARKS_AND_REC,
        FakerEnum.SILICON_VALLEY,
        FakerEnum.TECH,
        FakerEnum.THE_OFFICE,
    )

    private val staticCompanyLists = mapOf(
        FakerEnum.GRAVITY_FALLS to GravityFalls.companies.toList(),
        FakerEnum.HARRY_POTTER to HarryPotter.companies.toList(),
        FakerEnum.KING_OF_THE_HILL to KingOfTheHill.companies.toList(),
        FakerEnum.MONK to Monk.companies.toList(),
        FakerEnum.PARKS_AND_REC to ParksAndRec.companies.toList(),
        FakerEnum.SILICON_VALLEY to SiliconValley.companies.toList(),
        FakerEnum.TECH to Tech.companies.toList(),
        FakerEnum.THE_OFFICE to TheOffice.companies.toList(),
    )


    private fun createCharacterName(fakers: List<FakerEnum>?): Map<FakerEnum, String> {
        if (fakers.isNullOrEmpty()) {
            val faker = characterNameFakers.random()
            return characterNameSwitchBoard(listOf(faker))
        }
        return characterNameSwitchBoard(fakers)
    }

    // company logic
    private fun createCompanyName(fakers: List<FakerEnum>?): Map<FakerEnum, String> {
        if (fakers.isNullOrEmpty()) {
            val faker = companyNameFakers.random()
            return companySwitchboard(listOf(faker))
        }
        return companySwitchboard(fakers)
    }

    private fun characterNameSwitchBoard(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val result = buildMap {
            for (faker in fakers) {
                staticCharacterLists[faker]?.let { list ->
                    put(faker, list.random())
                }
            }
        }
        return result.ifEmpty {
            val randomFaker = characterNameFakers.random()
            staticCharacterLists[randomFaker]?.let { list ->
                mapOf(randomFaker to list.random())
            } ?: emptyMap()
        }
    }

    private fun companySwitchboard(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val result = buildMap {
            for (faker in fakers) {
                staticCompanyLists[faker]?.let { list ->
                    put(faker, list.random())
                }
            }
        }
        return result.ifEmpty {
            val randomFaker = companyNameFakers.random()
            staticCompanyLists[randomFaker]?.let { list ->
                mapOf(randomFaker to list.random())
            } ?: emptyMap()
        }
    }

    private val TITLE_PREFIXES = setOf("The", "Mrs.", "Mr.")
    private fun getFirstNamesOnly(names: Map<FakerEnum, String>): Map<FakerEnum, String> = names.mapValues { (_, name) ->
        if (name.isEmpty()) return@mapValues ""

        // Find first space
        val firstSpaceIndex = name.indexOf(' ')
        if (firstSpaceIndex == -1) return@mapValues name

        // Get first word and check if it's a title
        val firstWord = name.substring(0, firstSpaceIndex)
        if (firstWord !in TITLE_PREFIXES) return@mapValues firstWord

        // If first word was a title, get the next word
        val secondSpaceIndex = name.indexOf(' ', firstSpaceIndex + 1)
        if (secondSpaceIndex == -1) {
            // Only one word after title
            return@mapValues name.substring(firstSpaceIndex + 1)
        }
        // Return word after title
        name.substring(firstSpaceIndex + 1, secondSpaceIndex)
    }

    private fun getLastNamesOnly(names: Map<FakerEnum, String>): Map<FakerEnum, String> = names.mapValues { (_, name) ->
        if (name.isEmpty()) return@mapValues ""

        // Start from end and find last space
        val lastSpaceIndex = name.lastIndexOf(' ')
        if (lastSpaceIndex == -1) return@mapValues name

        // Get the last word
        name.substring(lastSpaceIndex + 1)
    }


    fun firstName(fakers: List<FakerEnum>?): DataTableItem {
        if (fakers.isNullOrEmpty()) {
            val name = createCharacterName(null)
            val firstName = getFirstNamesOnly(name)
            val (fakerUsed, derivedFirstName) = firstName.toList().random()
            return DataTableItem(
                maker = MakerEnum.NAME_FIRST,
                fakersUsed = listOf(fakerUsed),
                originalValue = name[fakerUsed],
                derivedValue = derivedFirstName,
                wikiUrl = WikiUtil.createFandomWikiLink(fakerUsed, derivedFirstName, false),
                influencedBy = null
            )
        }
        val names = createCharacterName(fakers)
        val firstNames = getFirstNamesOnly(names)
        val (fakerUsed, derivedName) = firstNames.toList().random()

        return DataTableItem(
            maker = MakerEnum.NAME_FIRST,
            fakersUsed = listOf(fakerUsed),
            originalValue = names[fakerUsed],
            derivedValue = derivedName,
            wikiUrl = WikiUtil.createFandomWikiLink(fakerUsed, names[fakerUsed]!!, false),
            influencedBy = null
        )
    }

    fun lastName(fakers: List<FakerEnum>?): DataTableItem {
        if (fakers.isNullOrEmpty()) {
            val name = createCharacterName(null)
            val lastName = getLastNamesOnly(name)
            val (fakerUsed, derivedLastName) = lastName.toList().random()
            return DataTableItem(
                maker = MakerEnum.NAME_LAST,
                fakersUsed = listOf(fakerUsed),
                originalValue = name[fakerUsed],
                derivedValue = derivedLastName,
                wikiUrl = WikiUtil.createFandomWikiLink(fakerUsed, derivedLastName, false),
                influencedBy = null
            )
        }
        val names = createCharacterName(fakers)
        val lastNames = getLastNamesOnly(names)
        val (fakerUsed, derivedName) = lastNames.toList().random()

        return DataTableItem(
            maker = MakerEnum.NAME_LAST,
            fakersUsed = listOf(fakerUsed),
            originalValue = names[fakerUsed],
            derivedValue = derivedName,
            wikiUrl = WikiUtil.createFandomWikiLink(fakerUsed, names[fakerUsed]!!, false),
            influencedBy = null
        )
    }

    fun companyName(fakers: List<FakerEnum>?): DataTableItem {
        if (fakers.isNullOrEmpty()) {
            val name = createCompanyName(null)
            val (fakerUsed, companyName) = name.entries.random().toPair()
            return DataTableItem(
                maker = MakerEnum.NAME_COMPANY,
                fakersUsed = listOf(fakerUsed),
                originalValue = companyName,
                derivedValue = companyName,
                wikiUrl = WikiUtil.createFandomWikiLink(fakerUsed, companyName, false),
                influencedBy = null
            )
        }
        val names = createCompanyName(fakers)
        val (fakerUsed, companyName) = names.entries.random().toPair()

        return DataTableItem(
            maker = MakerEnum.NAME_COMPANY,
            fakersUsed = listOf(fakerUsed),
            originalValue = companyName,
            derivedValue = companyName,
            wikiUrl = WikiUtil.createFandomWikiLink(fakerUsed, companyName, false),
            influencedBy = null
        )
    }
}