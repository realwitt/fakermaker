package elias.fakerMaker.generator

import elias.fakerMaker.fakers.Adjectives
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.enums.MakerEnum
import net.datafaker.Faker
import kotlin.random.Random

class EmailGenerator {
    private val dataFaker: Faker = Faker()
    private val rand: Random = Random
    private val nameGenerator: NameGenerator = NameGenerator()

// email generator
// if there are names in the schema, we should use them

    private fun generateRandomTld(): String {
        val topLevelDomains = listOf(
            ".com",
            ".dev",
            ".co",
            ".org",
            ".de",
            ".ru",
            ".io",
            ".net",
        )
        return topLevelDomains.random()
    }

    private fun generateRandomMailProvider(): String {
        val mailProviders = listOf(
            "yahoo.com",
            "ymail.com",
            "gmail.com",
            "zoho.com",
            "icloud.com",
            "proton.me",
            "mail.com",
            "outlook.com",
            "hotmail.com",
            "microsoft.com",
            "live.com",
            "msn.com",
            "excite.com",
            "lycos.com",
            "usa.net",
            "netaddress.com",
            "netscape.net",
            "aim.com",
            "aol.com",
            "juno.com",
            "netzero.com",
            "fastmail.com",
            "runbox.com",
            "postmark.com",
            "sina.com",
            "pobox.com",
            "hushmail.com",
            "mochamail.com",
            "ureach.com",
            "yandex.ru",
            "mail.ru",
        )
        return mailProviders.random()
    }

    private fun generateRandomEmailLocalPart(dataTableItems: List<DataTableItem>?): String {
        val localPartNames = mutableListOf<String>()

        if (dataTableItems.isNullOrEmpty()) {
            localPartNames.add(dataFaker.company().buzzword() + dataFaker.animal().name())
            localPartNames.add(Adjectives.quirky.random() + dataFaker.animal().name())
            localPartNames.add(dataFaker.company().buzzword())
            localPartNames.add(dataFaker.company().buzzword() + rand.nextInt(999))
            return localPartNames.random().lowercase().filter { it.isLetterOrDigit() }
        }

        // if multiple names are given, it will use the first instance of a first name, and last name
        // if only a single first or last name exists, or neither, it will look for a company name
        // we could use fold() here to be more performant... but this reads a lot easier
        val firstName = dataTableItems.find { it.maker == MakerEnum.NAME_FIRST }?.value ?: ""
        val lastName = dataTableItems.find { it.maker == MakerEnum.NAME_LAST }?.value ?: ""

        // if first name and last name exist
        if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
            val firstNameFirstInitial = firstName.first()
            localPartNames.add(firstName + lastName)
            localPartNames.add(firstName + lastName + rand.nextInt(999))
            localPartNames.add(firstNameFirstInitial + lastName)
            localPartNames.add(firstNameFirstInitial + lastName + rand.nextInt(999))
            return localPartNames.random().lowercase().filterNot { it.isWhitespace() }
        }
        val name = if (lastName.isNotEmpty()) lastName else firstName
        if (name.isNotEmpty()) {
            localPartNames.add(name)
            localPartNames.add(name + rand.nextInt(999))
            localPartNames.add(name + Adjectives.quirky.random())
            localPartNames.add(name + dataFaker.company().buzzword())
            return localPartNames.random().lowercase().filterNot { it.isWhitespace() }
        }
        return localPartNames.random().lowercase().filter { it.isLetterOrDigit() }
    }

    private fun generateRandomDomain(existingNames: List<DataTableItem>?): Pair<FakerEnum?, String> {
        val defaultDomains = listOf(
            Pair(null, dataFaker.company().name().filter { it.isLetterOrDigit() } + generateRandomTld()),
            Pair(FakerEnum.HARRY_POTTER, dataFaker.harryPotter().house()),
            Pair(FakerEnum.HARRY_POTTER, dataFaker.harryPotter().location()),
            Pair(FakerEnum.LORD_OF_THE_RINGS, dataFaker.hobbit().location()),
            Pair(FakerEnum.LORD_OF_THE_RINGS, dataFaker.lordOfTheRings().location()),
            Pair(FakerEnum.SILICON_VALLEY, dataFaker.siliconValley().company()),
            Pair(FakerEnum.SILICON_VALLEY, dataFaker.siliconValley().app())
        )

        if (!existingNames.isNullOrEmpty()) {
            val nameItems =
                existingNames.filter { item -> item.maker === MakerEnum.NAME_FIRST || item.maker === MakerEnum.NAME_LAST || item.maker === MakerEnum.NAME_COMPANY }
            if (nameItems.isNotEmpty()) {
                val domainsFromProvidedFakers = mutableListOf<Pair<FakerEnum?, String>>()
                for (name in nameItems) {
                    when (name.faker) {
                        FakerEnum.LORD_OF_THE_RINGS -> domainsFromProvidedFakers.add(Pair(FakerEnum.LORD_OF_THE_RINGS, dataFaker.lordOfTheRings().location()))
                        FakerEnum.HARRY_POTTER -> domainsFromProvidedFakers.add(Pair(FakerEnum.HARRY_POTTER, dataFaker.harryPotter().location()))
                        FakerEnum.GAME_OF_THRONES -> domainsFromProvidedFakers.add(Pair(FakerEnum.GAME_OF_THRONES, dataFaker.gameOfThrones().city()))
                        FakerEnum.RICK_AND_MORTY -> domainsFromProvidedFakers.add(Pair(FakerEnum.RICK_AND_MORTY, dataFaker.rickAndMorty().location()))
                        FakerEnum.SILICON_VALLEY -> domainsFromProvidedFakers.add(Pair(FakerEnum.SILICON_VALLEY, dataFaker.siliconValley().company()))
                        else -> domainsFromProvidedFakers.add(Pair(null, dataFaker.company().name().filter { it.isLetterOrDigit() } + generateRandomTld()))
                    }
                }
                return domainsFromProvidedFakers.random()
            }
        }
        return defaultDomains.random()
    }

    fun generateRandomEmail(existingNames: List<DataTableItem>?): DataTableItem {
        val localPartName = generateRandomEmailLocalPart(existingNames)
        val customDomainName = generateRandomDomain(existingNames)
        val customDomainNameWithTld = customDomainName.second.filter { it.isLetterOrDigit() } + generateRandomTld()
        val randomInt = rand.nextInt(2)
        // randomly pick either a custom domain name based on fakers, or a domain name from the mail providers
        val randomFullDomainName = listOf(customDomainNameWithTld, generateRandomMailProvider())

        return DataTableItem(
            MakerEnum.EMAIL,  // maker
            if (randomInt == 0) customDomainName.first else null,  // faker
            if (randomInt == 0 && customDomainName.first != null) customDomainName.second else null,  // original
            "${localPartName.lowercase()}@${randomFullDomainName[randomInt].lowercase()}",  // value
            if (randomInt == 0) customDomainName.first?.let { nameGenerator.getFandomUrl(it, customDomainName.second, false)} else null  // hyperlink
        )
    }

}