package elias.fakerMaker.generator

import elias.fakerMaker.customFakers.Adjectives
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

    private fun generateRandomEmailLocalPart(names: List<DataTableItem>?): String {
        val localPartNames = mutableListOf(
            dataFaker.company().buzzword() + dataFaker.animal().name(),
            Adjectives.quirky.random() + dataFaker.animal().name(),
        )

        // note: if multiple names are given, only the first instance of a first and last name will be used
        if (!names.isNullOrEmpty() && names.any { it.maker === MakerEnum.NAME_LAST } && names.any { it.maker === MakerEnum.NAME_FIRST }) {
            val firstNameFirstInitial = names.first() { it.maker == MakerEnum.NAME_FIRST }.value.first().toString()
            val firstName = names.first { it.maker == MakerEnum.NAME_FIRST }.value
            val lastName = names.first() { it.maker == MakerEnum.NAME_LAST }.value
            localPartNames.add(firstName)
            localPartNames.add(lastName)
            localPartNames.add(firstName + lastName)
            localPartNames.add(firstNameFirstInitial + lastName)
            localPartNames.add(firstNameFirstInitial + lastName + rand.nextInt(999))
            localPartNames.add(dataFaker.company().buzzword() + lastName)
            localPartNames.add(dataFaker.company().buzzword() + lastName + rand.nextInt(999))
            localPartNames.add(Adjectives.quirky.random() + lastName)
            localPartNames.add(Adjectives.quirky.random() + lastName + rand.nextInt(999))
            return localPartNames.random().lowercase().filterNot { it.isWhitespace() }
        }
        if (!names.isNullOrEmpty() && names.any { it.maker === MakerEnum.NAME_LAST }) {
            val lastName = names.first() { it.maker == MakerEnum.NAME_LAST }.value
            localPartNames.add(Adjectives.quirky.random() + lastName)
            localPartNames.add(Adjectives.quirky.random() + lastName + rand.nextInt(999))
            localPartNames.add(dataFaker.company().buzzword() + lastName)
            localPartNames.add(dataFaker.company().buzzword() + lastName + rand.nextInt(999))
            return localPartNames.random().lowercase().filterNot { it.isWhitespace() }
        }
        if (!names.isNullOrEmpty() && names.any { it.maker === MakerEnum.NAME_FIRST }) {
            val firstName = names.first() { it.maker == MakerEnum.NAME_FIRST }.value
            localPartNames.add(Adjectives.quirky.random() + firstName)
            localPartNames.add(Adjectives.quirky.random() + firstName + rand.nextInt(999))
            localPartNames.add(dataFaker.company().buzzword() + firstName)
            localPartNames.add(dataFaker.company().buzzword() + firstName + rand.nextInt(999))
            return localPartNames.random().lowercase().filterNot { it.isWhitespace() }
        }

        localPartNames.add(dataFaker.company().buzzword())
        localPartNames.add(dataFaker.animal().name() + dataFaker.company().buzzword())
        localPartNames.add(dataFaker.animal().name() + dataFaker.company().buzzword() + rand.nextInt(999))
        localPartNames.add(dataFaker.company().buzzword() + dataFaker.animal().name())
        localPartNames.add(dataFaker.company().buzzword() + dataFaker.animal().name() + rand.nextInt(999))
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
            "${localPartName.lowercase()}@${randomFullDomainName[randomInt].lowercase()}",
            MakerEnum.EMAIL,
            if (randomInt == 0) customDomainName.first else null,
            if (randomInt == 0 && customDomainName.first != null) customDomainName.second else null,
            if (randomInt == 0) customDomainName.first?.let { nameGenerator.getFandomUrl(it, customDomainName.second, false)} else null,
        )
    }

}