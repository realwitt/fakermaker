package elias.fakerMaker.generator

import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.Adjectives
import elias.fakerMaker.fakers.Tech
import elias.fakerMaker.fakers.books.GameOfThrones
import elias.fakerMaker.fakers.tvshows.*
import elias.fakerMaker.utils.WikiUtil
import kotlin.random.Random

object EmailGenerator {
    private val validEmailFakers = setOf(
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
        FakerEnum.GRAVITY_FALLS to GravityFalls.companies,
        FakerEnum.KING_OF_THE_HILL to KingOfTheHill.companies,
        FakerEnum.MONK to Monk.companies,
        FakerEnum.PARKS_AND_REC to ParksAndRec.companies,
        FakerEnum.POKEMON to Pokemon.locations,
        FakerEnum.TECH to Tech.companies,
        FakerEnum.THE_OFFICE to TheOffice.companies,
        FakerEnum.SILICON_VALLEY to SiliconValley.companies,
        FakerEnum.GAME_OF_THRONES to GameOfThrones.locations
    )

    // Pre-compute filtered domain lists to avoid repeated filtering operations
    private val sanitizedDomainLists = staticDomainLists.mapValues { (_, domains) ->
        domains.map { it.replace("'", "").filter { c -> c.isLetterOrDigit() }.lowercase() }
    }

    // Cache common values and computations
    private const val COM_TLD = ".com"
    private const val ORG_TLD = ".org"
    private val emailProviders = Tech.emailProviders.toList() // Convert to list for good random access

    private val localPartGenerators = object {
        fun fromFullName(first: String, last: String): String = when(Random.nextInt(4)) {
            0 -> "$first$last"
            1 -> "$first$last${Random.nextInt(999)}"
            2 -> "${first.first()}$last"
            else -> "${first.first()}$last${Random.nextInt(999)}"
        }.lowercase()

        fun fromSingleName(name: String): String = when(Random.nextInt(5)) {
            0 -> name
            1 -> "$name${Random.nextInt(999)}"
            2 -> "$name${Adjectives.quirky.random()}"
            3 -> "$name${Tech.buzzwords.random()}"
            else -> Tech.buzzwords.random()
        }.lowercase()

        fun random(): String = when(Random.nextInt(3)) {
            0 -> Tech.people.random()
            1 -> "${Tech.buzzwords.random()}${Random.nextInt(999)}"
            else -> "${Adjectives.quirky.random()}${Tech.buzzwords.random()}"
        }.lowercase()
    }

    fun email(dataTableItems: List<DataTableItem>?): DataTableItem {
        if (dataTableItems == null) {
            return generateDefaultEmail(
                localPart =  localPartGenerators.random().filterNot { it.isWhitespace() },
                dataTableItems = null
            )
        }

        val companyItem = dataTableItems.find { it.maker == MakerEnum.NAME_COMPANY }
        val firstNameItem = dataTableItems.find { it.maker == MakerEnum.NAME_FIRST }
        val lastNameItem = dataTableItems.find { it.maker == MakerEnum.NAME_LAST }

        val localPart = createLocalPart(firstNameItem?.derivedValue, lastNameItem?.derivedValue)

        val random = Random.nextDouble()
        return when {
            // Company domain (50% chance if company exists)
            companyItem != null && random < 0.5 -> createCompanyEmail(localPart, companyItem)

            // Personal domain (5% chance if both names exist)
            firstNameItem != null && lastNameItem != null && random in 0.5..0.55 ->
                createPersonalEmail(localPart, firstNameItem.derivedValue, lastNameItem.derivedValue)

            // Default case
            else -> {
                val relevantItems = dataTableItems.filter {
                    it.maker == MakerEnum.NAME_FIRST || it.maker == MakerEnum.NAME_LAST
                }
                generateDefaultEmail(localPart, relevantItems)
            }
        }
    }

    private fun createLocalPart(firstName: String?, lastName: String?): String = when {
        !firstName.isNullOrEmpty() && !lastName.isNullOrEmpty() ->
            localPartGenerators.fromFullName(firstName, lastName)
        !firstName.isNullOrEmpty() -> localPartGenerators.fromSingleName(firstName)
        !lastName.isNullOrEmpty() -> localPartGenerators.fromSingleName(lastName)
        else -> localPartGenerators.random().filterNot { it.isWhitespace() }
    }

    private fun createCompanyEmail(localPart: String, companyItem: DataTableItem): DataTableItem {
        val domain = companyItem.derivedValue.filter { it.isLetterOrDigit() }.lowercase()
        val tld = if (Random.nextDouble() < 0.8) COM_TLD else Tech.TLDs.random()

        return DataTableItem(
            maker = MakerEnum.EMAIL,
            fakersUsed = companyItem.fakersUsed,
            originalValue = companyItem.derivedValue,
            derivedValue = "$localPart@$domain$tld",
            wikiUrl = companyItem.wikiUrl,
            influencedBy = null
        )
    }

    private fun createPersonalEmail(localPart: String, firstName: String, lastName: String): DataTableItem {
        val domain = "$firstName$lastName".filter { it.isLetterOrDigit() }.lowercase()
        return DataTableItem(
            maker = MakerEnum.EMAIL,
            fakersUsed = null,
            originalValue = null,
            derivedValue = "$localPart@$domain$COM_TLD",
            wikiUrl = null,
            influencedBy = null
        )
    }

    private fun generateDefaultEmail(
        localPart: String,
        dataTableItems: List<DataTableItem>?
    ): DataTableItem {
        // Quick return for common case
        if (dataTableItems.isNullOrEmpty()) {
            return DataTableItem(
                maker = MakerEnum.EMAIL,
                fakersUsed = null,
                originalValue = null,
                derivedValue = "$localPart@${emailProviders.random()}",
                wikiUrl = null,
                influencedBy = null
            )
        }

        val domainFakers = buildSet {
            dataTableItems.forEach { item ->
                item.fakersUsed?.forEach { faker ->
                    if (faker in validEmailFakers) {
                        add(faker)
                    }
                }
            }
        }

        // Decide domain strategy
        val useValidFaker = domainFakers.isNotEmpty() && Random.nextBoolean()
        if (!useValidFaker) {
            return DataTableItem(
                maker = MakerEnum.EMAIL,
                fakersUsed = null,
                originalValue = null,
                derivedValue = "$localPart@${emailProviders.random()}",
                wikiUrl = null,
                influencedBy = null
            )
        }

        // Use faker domain
        val faker = domainFakers.random()
        val domainValue = sanitizedDomainLists[faker]?.random()
            ?: return generateDefaultEmail(localPart, null)

        val tld = when(Random.nextDouble()) {
            in 0.85..0.90 -> ORG_TLD
            in 0.90..0.95 -> Tech.TLDs.random()
            else -> COM_TLD
        }

        return DataTableItem(
            maker = MakerEnum.EMAIL,
            fakersUsed = listOf(faker),
            originalValue = domainValue,
            derivedValue = "$localPart@$domainValue$tld",
            wikiUrl = WikiUtil.createFandomWikiLink(faker, domainValue, false),
            influencedBy = null
        )
    }
}