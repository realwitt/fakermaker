package elias.fakerMaker.generator

import elias.fakerMaker.fakers.Adjectives
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.dto.Influencer
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.Tech
import elias.fakerMaker.fakers.tvshow.*
import elias.fakerMaker.utils.WikiUtil
import net.datafaker.Faker
import kotlin.random.Random

object EmailGenerator {
    private val dataFaker: Faker = Faker()
    private val rand: Random = Random

    private val validEmailFakers = listOf(
        FakerEnum.GAME_OF_THRONES,
        FakerEnum.RICK_AND_MORTY,
        FakerEnum.SILICON_VALLEY,
        FakerEnum.GRAVITY_FALLS,
        FakerEnum.KING_OF_THE_HILL,
        FakerEnum.MONK,
        FakerEnum.PARKS_AND_REC,
        FakerEnum.POKEMON,
        FakerEnum.TECH,
        FakerEnum.THE_OFFICE
    )

    private fun createEmailDomain(fakers: List<FakerEnum>?): Map<FakerEnum, String> {
        if (fakers.isNullOrEmpty()) {
            val faker = validEmailFakers.random()
            return domainSwitchboard(listOf(faker))
        }
        return domainSwitchboard(fakers)
    }

    private fun domainSwitchboard(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val generatedDomains = fakers.mapNotNull { faker ->
            when (faker) {
                FakerEnum.GAME_OF_THRONES -> faker to dataFaker.gameOfThrones().city()
                FakerEnum.RICK_AND_MORTY -> faker to dataFaker.rickAndMorty().location()
                FakerEnum.SILICON_VALLEY -> faker to dataFaker.siliconValley().company()
                FakerEnum.GRAVITY_FALLS -> faker to GravityFalls.companies.random()
                FakerEnum.KING_OF_THE_HILL -> faker to KingOfTheHill.companies.random()
                FakerEnum.MONK -> faker to Monk.companies.random()
                FakerEnum.PARKS_AND_REC -> faker to ParksAndRec.companies.random()
                FakerEnum.POKEMON -> faker to Pokemon.locations.random()
                FakerEnum.TECH -> faker to Tech.companies.random()
                FakerEnum.THE_OFFICE -> faker to TheOffice.companies.random()
                else -> null
            }
        }.toMap()

        return generatedDomains.ifEmpty { createEmailDomain(null) }
    }

    private fun createLocalPart(dataTableItems: List<DataTableItem>?): String {
        val firstName = dataTableItems?.find { it.maker == MakerEnum.NAME_FIRST }?.derivedValue.orEmpty()
        val lastName = dataTableItems?.find { it.maker == MakerEnum.NAME_LAST }?.derivedValue.orEmpty()

        return when {
            firstName.isNotEmpty() && lastName.isNotEmpty() -> generateLocalPartFromFullName(firstName, lastName)
            firstName.isNotEmpty() -> generateLocalPartFromSingleName(firstName)
            lastName.isNotEmpty() -> generateLocalPartFromSingleName(lastName)
            else -> generateRandomLocalPart()
        }
    }

    private fun generateLocalPartFromFullName(firstName: String, lastName: String): String {
        val options = listOf(
            "${firstName}${lastName}",
            "${firstName}${lastName}${rand.nextInt(999)}",
            "${firstName.first()}${lastName}",
            "${firstName.first()}${lastName}${rand.nextInt(999)}"
        )
        return options.random().lowercase().filterNot { it.isWhitespace() }
    }

    private fun generateLocalPartFromSingleName(name: String): String {
        val options = listOf(
            name,
            "$name${rand.nextInt(999)}",
            "$name${Adjectives.quirky.random()}",
            "$name${dataFaker.company().buzzword()}",
            "${dataFaker.company().buzzword()}${dataFaker.animal().name()}"
        )
        return options.random().lowercase().filterNot { it.isWhitespace() }
    }

    private fun generateRandomLocalPart(): String {
        val options = listOf(
            dataFaker.name().username(),
            "${dataFaker.company().buzzword()}${rand.nextInt(999)}",
            "${dataFaker.animal().name()}${rand.nextInt(999)}",
            "${Adjectives.quirky.random()}${dataFaker.company().buzzword()}"
        )
        return options.random().lowercase().filterNot { it.isWhitespace() }
    }

    fun email(dataTableItems: List<DataTableItem>?): DataTableItem {
        val localPart = createLocalPart(dataTableItems)

        // Determine domain source based on existing data table items
        val domainFakers = dataTableItems
            ?.filter { it.maker in listOf(MakerEnum.NAME_COMPANY, MakerEnum.NAME_FIRST, MakerEnum.NAME_LAST) }
            ?.flatMap { it.fakersUsed ?: emptyList() }
            ?.filter { it in validEmailFakers }

        val domainMap = createEmailDomain(domainFakers)
        val (fakerUsed, domainValue) = if (rand.nextBoolean() && domainMap.isNotEmpty()) {
            domainMap.entries.random().toPair()
        } else {
            null to Tech.emailProviders.random()
        }

        val domain = when {
            fakerUsed != null -> "${domainValue.filter { it.isLetterOrDigit() }}${Tech.TLDs.random()}"
            else -> domainValue
        }

        return DataTableItem(
            maker = MakerEnum.EMAIL,
            fakersUsed = fakerUsed?.let { listOf(it) },
            originalValue = if (fakerUsed != null) domainValue else null,
            derivedValue = "${localPart}@${domain.lowercase()}",
            wikiUrl = fakerUsed?.let { WikiUtil.createFandomWikiLink(it, domainValue, false) },
            influencedBy = null
        )
    }
}