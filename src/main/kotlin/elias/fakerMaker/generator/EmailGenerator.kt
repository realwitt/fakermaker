package elias.fakerMaker.generator

import elias.fakerMaker.fakers.Adjectives
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.Tech
import elias.fakerMaker.fakers.books.GameOfThrones
import elias.fakerMaker.fakers.tvshow.*
import elias.fakerMaker.utils.WikiUtil
import kotlin.random.Random

object EmailGenerator {
    private val emailComponents = object {
        val localPartFormats = listOf(
            { first: String, last: String -> "$first$last" },
            { first: String, last: String -> "$first$last${Random.nextInt(999)}" },
            { first: String, last: String -> "${first.first()}$last" },
            { first: String, last: String -> "${first.first()}$last${Random.nextInt(999)}" }
        )

        val singleNameFormats = listOf(
            { name: String -> name },
            { name: String -> "$name${Random.nextInt(999)}" },
            { name: String -> "$name${Adjectives.quirky.random()}" },
            { name: String -> "$name${Tech.buzzwords.random()}" },
            { _: String -> Tech.buzzwords.random() }
        )

        val randomLocalFormats = listOf(
            { -> Tech.people.random() },
            { -> "${Tech.buzzwords.random()}${Random.nextInt(999)}" },
            { -> "${Adjectives.quirky.random()}${Tech.buzzwords.random()}" }
        )
    }

    private val validEmailFakers = listOf(
        FakerEnum.GAME_OF_THRONES,
        FakerEnum.GRAVITY_FALLS,
        FakerEnum.KING_OF_THE_HILL,
        FakerEnum.MONK,
        FakerEnum.PARKS_AND_REC,
        FakerEnum.POKEMON,
        FakerEnum.SILICON_VALLEY,
        FakerEnum.TECH,
        FakerEnum.THE_OFFICE
    )

    private val staticDomainLists = mapOf(
        FakerEnum.GRAVITY_FALLS to GravityFalls.companies.map { it.replace("'", "") }.toList(),
        FakerEnum.KING_OF_THE_HILL to KingOfTheHill.companies.map { it.replace("'", "") }.toList(),
        FakerEnum.MONK to Monk.companies.map { it.replace("'", "") }.toList(),
        FakerEnum.PARKS_AND_REC to ParksAndRec.companies.map { it.replace("'", "") }.toList(),
        FakerEnum.POKEMON to Pokemon.locations.map { it.replace("'", "") }.toList(),
        FakerEnum.TECH to Tech.companies.map { it.replace("'", "") }.toList(),
        FakerEnum.THE_OFFICE to TheOffice.companies.map { it.replace("'", "") }.toList(),
        FakerEnum.SILICON_VALLEY to SiliconValley.companies.map { it.replace("'", "") }.toList(),
        FakerEnum.GAME_OF_THRONES to GameOfThrones.locations.map { it.replace("'", "") }.toList()
    )

    private fun createEmailDomain(fakers: List<FakerEnum>?): Map<FakerEnum, String> {
        if (fakers.isNullOrEmpty()) {
            val faker = validEmailFakers.random()
            return domainSwitchboard(listOf(faker))
        }
        return domainSwitchboard(fakers)
    }

    private fun domainSwitchboard(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val result = buildMap {
            for (faker in fakers) {
                staticDomainLists[faker]?.let { list ->
                    put(faker, list.random())
                }
            }
        }
        return result.ifEmpty {
            val randomFaker = validEmailFakers.random()
            staticDomainLists[randomFaker]?.let { list ->
                mapOf(randomFaker to list.random())
            } ?: emptyMap()
        }
    }

    private fun generateLocalPartFromFullName(firstName: String, lastName: String): String =
        emailComponents.localPartFormats.random()
            .invoke(firstName, lastName)
            .lowercase()
            .filterNot { it.isWhitespace() }

    private fun generateLocalPartFromSingleName(name: String): String =
        emailComponents.singleNameFormats.random()
            .invoke(name)
            .lowercase()
            .filterNot { it.isWhitespace() }

    private fun generateRandomLocalPart(): String =
        emailComponents.randomLocalFormats.random()
            .invoke()
            .lowercase()
            .filterNot { it.isWhitespace() }

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

    private fun selectNonBusinessTld(): String {
        return when (Random.nextDouble()) {
            in 0.85..0.90 -> ".org"
            in 0.90..0.95 -> Tech.TLDs.random()
            else -> ".com"  // 90% chance (0.0-0.85 and 0.95-1.0)
        }
    }

    fun email(dataTableItems: List<DataTableItem>?): DataTableItem {
        val localPart = createLocalPart(dataTableItems)
        val companyItem = dataTableItems?.find { it.maker == MakerEnum.NAME_COMPANY }
        val firstName = dataTableItems?.find { it.maker == MakerEnum.NAME_FIRST }?.derivedValue
        val lastName = dataTableItems?.find { it.maker == MakerEnum.NAME_LAST }?.derivedValue

        val random = Random.nextDouble()

        // If we have a company and hit the 50% chance
        val companyDomain = companyItem?.takeIf { random < 0.5 }?.derivedValue
            ?.filter { it.isLetterOrDigit() }
            ?.lowercase()
            ?.let { domain ->
                val tld = if (Random.nextDouble() < 0.8) ".com" else Tech.TLDs.random()
                DataTableItem(
                    maker = MakerEnum.EMAIL,
                    fakersUsed = companyItem.fakersUsed,
                    originalValue = companyItem.derivedValue,
                    derivedValue = "$localPart@$domain$tld",
                    wikiUrl = companyItem.wikiUrl,
                    influencedBy = null
                )
            }

        // If we have both names and hit the 5% chance (between 0.5 and 0.55)
        val personalDomain = if (random in 0.5..0.55 && firstName != null && lastName != null) {
            val domain = "$firstName$lastName".filter { it.isLetterOrDigit() }.lowercase()
            DataTableItem(
                maker = MakerEnum.EMAIL,
                fakersUsed = null,
                originalValue = null,
                derivedValue = "$localPart@$domain.com", // Personal domains usually use .com
                wikiUrl = null,
                influencedBy = null
            )
        } else null

        // If neither hit, use email provider
        return companyDomain ?: personalDomain ?: generateDefaultEmail(localPart, dataTableItems)
    }

    private fun generateDefaultEmail(
        localPart: String,
        dataTableItems: List<DataTableItem>?,
    ): DataTableItem {
        // If forceEmailProvider is true, skip the faker domain logic entirely
        val domainFakers = dataTableItems
            ?.filter { it.maker in listOf(MakerEnum.NAME_FIRST, MakerEnum.NAME_LAST) }
            ?.flatMap { it.fakersUsed ?: emptyList() }
            ?.filter { it in validEmailFakers }

        val domainMap = createEmailDomain(domainFakers)
        val (fakerUsed, domainValue) = if (Random.nextBoolean() && domainMap.isNotEmpty()) {
            domainMap.entries.random().toPair()
        } else {
            null to Tech.emailProviders.random()
        }

        val domain = when {
            fakerUsed != null -> "${domainValue.filter { it.isLetterOrDigit() }}${selectNonBusinessTld()}"
            else -> domainValue
        }

        return DataTableItem(
            maker = MakerEnum.EMAIL,
            fakersUsed = fakerUsed?.let { listOf(it) },
            originalValue = if (fakerUsed != null) domainValue else null,
            derivedValue = "$localPart@${domain.lowercase()}",
            wikiUrl = fakerUsed?.let { WikiUtil.createFandomWikiLink(it, domainValue, false) },
            influencedBy = null
        )
    }
}