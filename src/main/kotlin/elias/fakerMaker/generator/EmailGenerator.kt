package elias.fakerMaker.generator

import elias.fakerMaker.types.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.Adjectives
import elias.fakerMaker.fakers.Tech
import elias.fakerMaker.fakers.books.GameOfThrones
import elias.fakerMaker.fakers.tvshows.*
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

    // Cache sanitized domain lists since they don't change
    private val sanitizedDomainLists = staticDomainLists.mapValues { (_, domains) ->
        domains.map { it.replace("'", "").filter { c -> c.isLetterOrDigit() }.lowercase() }
    }

    // Cache common values
    private const val COM_TLD = ".com"
    private const val ORG_TLD = ".org"
    private val emailProviders = Tech.emailProviders.toList()

    // Pre-compute lists for random generation
    private val cachedTechPeople = Tech.people.toList()
    private val cachedTechBuzzwords = Tech.buzzwords.toList()
    private val cachedQuirkyAdjectives = Adjectives.quirky.toList()

    // Cache for valid fakers per request (the fakers don't change between rows)
    private class ValidFakersCache {
        private var domainFakersCache: Set<FakerEnum>? = null

        fun getValidDomainFakers(dataTableItems: List<DataTableItem>?): Set<FakerEnum> {
            if (dataTableItems == null) return emptySet()

            // Return cached result if available
            domainFakersCache?.let { return it }

            // Calculate and cache the result
            val result = buildSet {
                dataTableItems.forEach { item ->
                    item.fakersUsed?.forEach { faker ->
                        if (faker in validEmailFakers) {
                            add(faker)
                        }
                    }
                }
            }

            domainFakersCache = result
            return result
        }

        fun clear() {
            domainFakersCache = null
        }
    }

    // Creates a separate ValidFakersCache instance for each thread that accesses EmailGenerator,
    // this prevents concurrency issues when multiple threads are generating emails simultaneously.
    private val validFakersCache = ThreadLocal.withInitial { ValidFakersCache() }

    private object LocalPartGenerators {
        fun fromFullName(first: String, last: String): String = when(Random.nextInt(4)) {
            0 -> "$first$last"
            1 -> "$first$last${Random.nextInt(999)}"
            2 -> "${first.first()}$last"
            else -> "${first.first()}$last${Random.nextInt(999)}"
        }.lowercase()

        fun fromSingleName(name: String): String = when(Random.nextInt(5)) {
            0 -> name
            1 -> "$name${Random.nextInt(999)}"
            2 -> "$name${cachedQuirkyAdjectives.random()}"
            3 -> "$name${cachedTechBuzzwords.random()}"
            else -> cachedTechBuzzwords.random()
        }.lowercase()

        fun random(): String = when(Random.nextInt(3)) {
            0 -> cachedTechPeople.random()
            1 -> "${cachedTechBuzzwords.random()}${Random.nextInt(999)}"
            else -> "${cachedQuirkyAdjectives.random()}${cachedTechBuzzwords.random()}"
        }.lowercase()
    }

    fun email(dataTableItems: List<DataTableItem>?, nickname: String): DataTableItem {
        if (dataTableItems == null) {
            return generateDefaultEmail(
                localPart = LocalPartGenerators.random().filterNot { it.isWhitespace() },
                dataTableItems = null,
                nickname
            )
        }

        val companyItem = dataTableItems.find { it.maker == MakerEnum.NAME_COMPANY }
        val firstNameItem = dataTableItems.find { it.maker == MakerEnum.NAME_FIRST }
        val lastNameItem = dataTableItems.find { it.maker == MakerEnum.NAME_LAST }

        val localPart = createLocalPart(firstNameItem?.derivedValue, lastNameItem?.derivedValue)

        val random = Random.nextDouble()
        return when {
            // Company domain (50% chance if company exists)
            companyItem != null && random < 0.5 -> createCompanyEmail(localPart, companyItem, nickname)

            // Personal domain (5% chance if both names exist)
            firstNameItem != null && lastNameItem != null && random in 0.5..0.55 ->
                createPersonalEmail(localPart, firstNameItem.derivedValue, lastNameItem.derivedValue, nickname)

            // Default case with cached faker validation
            else -> generateDefaultEmail(localPart, dataTableItems, nickname)
        }
    }

    private fun createLocalPart(firstName: String?, lastName: String?): String = when {
        !firstName.isNullOrEmpty() && !lastName.isNullOrEmpty() ->
            LocalPartGenerators.fromFullName(firstName, lastName)
        !firstName.isNullOrEmpty() -> LocalPartGenerators.fromSingleName(firstName)
        !lastName.isNullOrEmpty() -> LocalPartGenerators.fromSingleName(lastName)
        else -> LocalPartGenerators.random().filterNot { it.isWhitespace() }
    }

    private fun createCompanyEmail(localPart: String, companyItem: DataTableItem, nickname: String): DataTableItem {
        val domain = companyItem.derivedValue.filter { it.isLetterOrDigit() }.lowercase()
        val tld = if (Random.nextDouble() < 0.8) COM_TLD else Tech.TLDs.random()

        return DataTableItem(
            maker = MakerEnum.EMAIL,
            fakersUsed = companyItem.fakersUsed,
            originalValue = companyItem.derivedValue,
            derivedValue = "$localPart@$domain$tld",
            wikiUrl = companyItem.wikiUrl,
            influencedBy = null,
            idTypeEnum = null,
            nickname = nickname,
        )
    }

    private fun createPersonalEmail(localPart: String, firstName: String, lastName: String, nickname: String): DataTableItem {
        val domain = "$firstName$lastName".filter { it.isLetterOrDigit() }.lowercase()
        return DataTableItem(
            maker = MakerEnum.EMAIL,
            fakersUsed = null,
            originalValue = null,
            derivedValue = "$localPart@$domain$COM_TLD",
            wikiUrl = null,
            influencedBy = null,
            idTypeEnum = null,
            nickname = nickname,
        )
    }

    private fun generateDefaultEmail(
        localPart: String,
        dataTableItems: List<DataTableItem>?,
        nickname: String
    ): DataTableItem {
        // Quick return for common case
        if (dataTableItems.isNullOrEmpty()) {
            return DataTableItem(
                maker = MakerEnum.EMAIL,
                fakersUsed = null,
                originalValue = null,
                derivedValue = "$localPart@${emailProviders.random()}",
                wikiUrl = null,
                influencedBy = null,
                idTypeEnum = null,
                nickname = nickname,
            )
        }

        // Use cached domain fakers
        val domainFakers = validFakersCache.get().getValidDomainFakers(dataTableItems)

        // Decide domain strategy
        val useValidFaker = domainFakers.isNotEmpty() && Random.nextBoolean()
        if (!useValidFaker) {
            return DataTableItem(
                maker = MakerEnum.EMAIL,
                fakersUsed = null,
                originalValue = null,
                derivedValue = "$localPart@${emailProviders.random()}",
                wikiUrl = null,
                influencedBy = null,
                idTypeEnum = null,
                nickname = nickname,
            )
        }

        // Use faker domain
        val faker = domainFakers.random()
        val domainValue = sanitizedDomainLists[faker]?.random()
            ?: return generateDefaultEmail(localPart, null, nickname)

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
            influencedBy = null,
            idTypeEnum = null,
            nickname = nickname,
        )
    }

    fun clearCache() {
        validFakersCache.get().clear()
    }
}