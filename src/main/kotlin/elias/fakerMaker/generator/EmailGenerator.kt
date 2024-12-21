package elias.fakerMaker.generator

import elias.fakerMaker.fakers.Adjectives
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.fakers.Tech
import elias.fakerMaker.fakers.tvshow.*
import net.datafaker.Faker
import kotlin.random.Random

object EmailGenerator {
    private val dataFaker: Faker = Faker()
    private val rand: Random = Random

    private fun generateRandomEmailLocalPart(dataTableItems: List<DataTableItem>?): String {
        val localPartNames = mutableListOf<String>()
        // we could use fold() here to be more performant... but this reads a lot easier
        val firstName = dataTableItems?.find { it.maker == MakerEnum.NAME_FIRST }?.value ?: ""
        val lastName = dataTableItems?.find { it.maker == MakerEnum.NAME_LAST }?.value ?: ""

        if (dataTableItems.isNullOrEmpty() || (firstName.isEmpty() && lastName.isEmpty())) {
            localPartNames.add(dataFaker.company().buzzword() + dataFaker.animal().name())
            localPartNames.add(Adjectives.quirky.random() + dataFaker.animal().name())
            localPartNames.add(dataFaker.company().buzzword())
            localPartNames.add(dataFaker.company().buzzword() + rand.nextInt(999))
            return localPartNames.random().lowercase().filter { it.isLetterOrDigit() }
        }

        if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
            val firstNameFirstInitial = firstName.first()
            localPartNames.add(firstName + lastName)
            localPartNames.add(firstName + lastName + rand.nextInt(999))
            localPartNames.add(firstNameFirstInitial + lastName)
            localPartNames.add(firstNameFirstInitial + lastName + rand.nextInt(999))
            localPartNames.add(dataFaker.company().buzzword() + rand.nextInt(999))
            return localPartNames.random().lowercase().filterNot { it.isWhitespace() }
        }
        val name = lastName.ifEmpty { firstName }
        if (name.isNotEmpty()) {
            localPartNames.add(name)
            localPartNames.add(name + rand.nextInt(999))
            localPartNames.add(name + Adjectives.quirky.random())
            localPartNames.add(name + dataFaker.company().buzzword())
            localPartNames.add(dataFaker.company().buzzword() + dataFaker.animal().name())
            return localPartNames.random().lowercase().filterNot { it.isWhitespace() }
        }
        throw IllegalStateException("This should never be reached.")
    }


    private fun processValidEmailFakers(faker: FakerEnum?): List<Pair<FakerEnum?, String>> = when (faker) {
        FakerEnum.GAME_OF_THRONES -> listOf(Pair(FakerEnum.GAME_OF_THRONES, dataFaker.gameOfThrones().city()))
        FakerEnum.RICK_AND_MORTY -> listOf(Pair(FakerEnum.RICK_AND_MORTY, dataFaker.rickAndMorty().location()))
        FakerEnum.SILICON_VALLEY -> listOf(
            Pair(FakerEnum.SILICON_VALLEY, dataFaker.siliconValley().company()),
            Pair(FakerEnum.SILICON_VALLEY, dataFaker.siliconValley().app())
        )
        FakerEnum.GRAVITY_FALLS -> listOf(
            Pair(FakerEnum.GRAVITY_FALLS, GravityFalls.locations.random()),
            Pair(FakerEnum.GRAVITY_FALLS, GravityFalls.companies.random())
        )
        FakerEnum.KING_OF_THE_HILL -> listOf(
            Pair(FakerEnum.KING_OF_THE_HILL, KingOfTheHill.locations.random()),
            Pair(FakerEnum.KING_OF_THE_HILL, KingOfTheHill.companies.random())
        )
        FakerEnum.MONK -> listOf(Pair(FakerEnum.MONK, Monk.companies.random()))
        FakerEnum.PARKS_AND_REC -> listOf(
            Pair(FakerEnum.PARKS_AND_REC, ParksAndRec.companies.random()),
            Pair(FakerEnum.PARKS_AND_REC, ParksAndRec.locations.random())
        )
        FakerEnum.POKEMON -> listOf(Pair(FakerEnum.POKEMON, Pokemon.locations.random()))
        FakerEnum.TECH -> listOf(Pair(FakerEnum.TECH, Tech.companies.random()))
        FakerEnum.THE_OFFICE -> listOf(
            Pair(FakerEnum.THE_OFFICE, TheOffice.locations.random()),
            Pair(FakerEnum.THE_OFFICE, TheOffice.companies.random())
        )
        else -> emptyList()
    }

    // not all fakers will have email values... so we gotta keep track of those that do
    private val randomEmailFaker = listOf(
        FakerEnum.CALL_OF_DUTY, FakerEnum.LORD_OF_THE_RINGS,
        FakerEnum.HARRY_POTTER, FakerEnum.GAME_OF_THRONES,
        FakerEnum.RICK_AND_MORTY, FakerEnum.SILICON_VALLEY,
        FakerEnum.GRAVITY_FALLS, FakerEnum.KING_OF_THE_HILL,
        FakerEnum.MONK, FakerEnum.PARKS_AND_REC,
        FakerEnum.POKEMON, FakerEnum.THE_OFFICE, FakerEnum.TECH,
    ).random()

    private fun generateRandomDomain(dataTableItems: List<DataTableItem>?): Pair<FakerEnum?, String> {
        if (dataTableItems.isNullOrEmpty()) {
           return processValidEmailFakers(randomEmailFaker)[0]
        }

        // Find possible makers to riff off of...
        // given a list of makers, the NAME Makers will be greedily generated into DataTableItems first,
        // so if NAME makers were chosen, we'll have them to riff off of here
        val companyName = dataTableItems.find { it.maker == MakerEnum.NAME_COMPANY }
        val firstName = dataTableItems.find { it.maker == MakerEnum.NAME_FIRST }
        val lastName = dataTableItems.find { it.maker == MakerEnum.NAME_LAST }

        // If companyName and a firstName or lastName exists, make it ~50/50 chance that
        // A) it uses that company as the domain
        // B) it uses one of name's fakers as the domain
        if (companyName != null && (firstName != null || lastName != null)) {
            if (rand.nextBoolean()) {
                if (companyName.faker != null && companyName.original != null) {
                    return Pair(companyName.faker, companyName.original)
                }
            }
        }
        // if only companyName exists, use it
        if (companyName != null && (firstName == null && lastName == null)) {
            if (companyName.faker != null && companyName.original != null) {
                return Pair(companyName.faker, companyName.original)
            }
        }

        // Generate domains based on the faker types of the names
        val potentialDomains = listOfNotNull(companyName, firstName, lastName)
            .flatMap { name -> processValidEmailFakers(name.faker) }

        val allDomains = potentialDomains.ifEmpty {
            processValidEmailFakers(randomEmailFaker)
        }

        return allDomains.random()
    }

        fun generateRandomEmail(dataTableItems: List<DataTableItem>?): DataTableItem {
        val localPartName = generateRandomEmailLocalPart(dataTableItems)
        val customDomainName = generateRandomDomain(dataTableItems)
        val customDomainNameWithTld = customDomainName.second.filter { it.isLetterOrDigit() } + Tech.TLDs.random()
        val randomInt = rand.nextInt(2)
        // randomly pick either a custom domain name based on fakers, or a domain name from the mail providers
        val randomFullDomainName = listOf(customDomainNameWithTld, Tech.emailProviders.random())

        return DataTableItem(
           maker    = MakerEnum.EMAIL,
           faker    = if (randomInt == 0) customDomainName.first else null,
           original = if (randomInt == 0 && customDomainName.first != null) customDomainName.second else null,
           value    = "${localPartName.lowercase()}@${randomFullDomainName[randomInt].lowercase()}",
           wikiUrl  = if (randomInt == 0) customDomainName.first?.let { NameGenerator.getFandomUrl(it, customDomainName.second, false)} else null
        )
    }

}