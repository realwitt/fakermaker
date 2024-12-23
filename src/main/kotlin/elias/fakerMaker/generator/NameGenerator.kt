package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.*
import elias.fakerMaker.fakers.book.HarryPotter
import elias.fakerMaker.fakers.book.ThroneOfGlass
import elias.fakerMaker.fakers.tvshow.*
import elias.fakerMaker.fakers.videogame.CallOfDuty
import elias.fakerMaker.utils.WikiUtil
import net.datafaker.Faker

object NameGenerator {
    private val dataFaker: Faker = Faker()
    private val characterNameFakers = listOf(
        FakerEnum.BACK_TO_THE_FUTURE,
        FakerEnum.BASEBALL,
        FakerEnum.BASKETBALL,
        FakerEnum.BREAKING_BAD,
        FakerEnum.CALL_OF_DUTY,
        FakerEnum.CLASH_OF_CLANS,
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
        FakerEnum.THE_OFFICE,
        FakerEnum.THRONE_OF_GLASS,
    )
    private val companyNameFakers = listOf(
        FakerEnum.GRAVITY_FALLS,
        FakerEnum.HARRY_POTTER,
        FakerEnum.KING_OF_THE_HILL,
        FakerEnum.MONK,
        FakerEnum.SILICON_VALLEY,
        FakerEnum.PARKS_AND_REC,
        FakerEnum.TECH,
        FakerEnum.THE_OFFICE,
    )


    private fun createCharacterName(fakers: List<FakerEnum>?): Map<FakerEnum, String> {
        if (fakers.isNullOrEmpty()) {
            val faker = characterNameFakers.random()
            return characterNameSwitchBoard(listOf(faker))
        }
        return characterNameSwitchBoard(fakers)
    }

    private fun characterNameSwitchBoard(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val generatedNames = fakers.mapNotNull { faker ->
            when (faker) {
                FakerEnum.BACK_TO_THE_FUTURE -> faker to dataFaker.backToTheFuture().character()
                FakerEnum.BASEBALL -> faker to dataFaker.baseball().players()
                FakerEnum.BASKETBALL -> faker to dataFaker.basketball().players()
                FakerEnum.BREAKING_BAD -> faker to dataFaker.breakingBad().character()
                FakerEnum.CALL_OF_DUTY -> faker to CallOfDuty.operators.random()
                FakerEnum.CLASH_OF_CLANS -> faker to dataFaker.clashOfClans().troop()
                FakerEnum.DOCTOR_WHO -> faker to dataFaker.doctorWho().character()
                FakerEnum.GAME_OF_THRONES -> faker to dataFaker.gameOfThrones().character()
                FakerEnum.GRAVITY_FALLS -> faker to GravityFalls.characters.random()
                FakerEnum.HARRY_POTTER -> faker to dataFaker.harryPotter().character()
                FakerEnum.KING_OF_THE_HILL -> faker to KingOfTheHill.characters.random()
                FakerEnum.LORD_OF_THE_RINGS -> faker to dataFaker.lordOfTheRings().character()
                FakerEnum.MONK -> faker to Monk.characters.random()
                FakerEnum.PARKS_AND_REC -> faker to ParksAndRec.characters.random()
                FakerEnum.RICK_AND_MORTY -> faker to dataFaker.rickAndMorty().character()
                FakerEnum.SILICON_VALLEY -> faker to dataFaker.siliconValley().character()
                FakerEnum.TECH -> faker to Tech.people.random()
                FakerEnum.THE_OFFICE -> faker to TheOffice.characters.random()
                FakerEnum.THRONE_OF_GLASS -> faker to ThroneOfGlass.characters.random()
                else -> null
            }
        }.toMap()

        return generatedNames.ifEmpty { createCharacterName(null) }
    }

    private fun createCompanyName(fakers: List<FakerEnum>?): Map<FakerEnum, String> {
        if (fakers.isNullOrEmpty()) {
            val faker = companyNameFakers.random()
            return companySwitchboard(listOf(faker))
        }
        return companySwitchboard(fakers)
    }

    private fun companySwitchboard(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val generatedNames = fakers.mapNotNull { faker ->
            when (faker) {
                FakerEnum.GRAVITY_FALLS -> faker to GravityFalls.companies.random()
                FakerEnum.HARRY_POTTER -> faker to HarryPotter.companies.random()
                FakerEnum.KING_OF_THE_HILL -> faker to KingOfTheHill.companies.random()
                FakerEnum.MONK -> faker to Monk.companies.random()
                FakerEnum.SILICON_VALLEY -> faker to dataFaker.siliconValley().company()
                FakerEnum.PARKS_AND_REC -> faker to ParksAndRec.companies.random()
                FakerEnum.TECH -> faker to Tech.companies.random()
                FakerEnum.THE_OFFICE -> faker to TheOffice.companies.random()
                else -> null
            }
        }.toMap()

        return generatedNames.ifEmpty { createCompanyName(null) }
    }

    private fun getFirstNamesOnly(names: Map<FakerEnum, String>): Map<FakerEnum, String> = names.mapValues { (_, name) ->
        name.split(" ")
            .filterNot { it in setOf("The", "Mrs.", "Mr.") }
            .firstOrNull()
            .orEmpty()
    }


    private fun getLastNamesOnly(names: Map<FakerEnum, String>): Map<FakerEnum, String> = names.mapValues { (_, name) ->
        name.split(" ")
            .filterNot { it in setOf("The", "Mrs.", "Mr.") }
            .lastOrNull()
            .orEmpty()
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