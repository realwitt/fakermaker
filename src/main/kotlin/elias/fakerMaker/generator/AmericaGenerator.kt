package elias.fakerMaker.generator

import elias.fakerMaker.types.AmericaData
import elias.fakerMaker.types.DataTableItem
import elias.fakerMaker.types.Influencer
import elias.fakerMaker.types.LocationData
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.enums.StatesEnum
import elias.fakerMaker.fakers.Address
import jakarta.annotation.PostConstruct
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import kotlin.random.Random


@Component
object AmericaGenerator {
    private val logger = KotlinLogging.logger {}
    private lateinit var americaData: AmericaData
    private val statesWithAreaCodes = setOf(
        StatesEnum.AK, StatesEnum.AL, StatesEnum.AZ, StatesEnum.AR, StatesEnum.CA, StatesEnum.CO,
        StatesEnum.CT, StatesEnum.DE, StatesEnum.FL, StatesEnum.GA, StatesEnum.HI, StatesEnum.ID,
        StatesEnum.IL, StatesEnum.IN, StatesEnum.IA, StatesEnum.KS, StatesEnum.KY, StatesEnum.LA,
        StatesEnum.ME, StatesEnum.MD, StatesEnum.MA, StatesEnum.MI, StatesEnum.MN, StatesEnum.MS,
        StatesEnum.MO, StatesEnum.MT, StatesEnum.NE, StatesEnum.NV, StatesEnum.NH, StatesEnum.NJ,
        StatesEnum.NM, StatesEnum.NY, StatesEnum.NC, StatesEnum.ND, StatesEnum.OH, StatesEnum.OK,
        StatesEnum.OR, StatesEnum.PA, StatesEnum.RI, StatesEnum.SC, StatesEnum.SD, StatesEnum.TN,
        StatesEnum.TX, StatesEnum.UT, StatesEnum.VT, StatesEnum.VA, StatesEnum.WA, StatesEnum.WV,
        StatesEnum.WI, StatesEnum.WY, )
    private val validStates: List<StatesEnum> by lazy {
        StatesEnum.entries.filterNot { it == StatesEnum.state }
    }

    // Pre-compute the common components
    // opted for this approach to still allow uncommon states in the final data, like American Samoa, Palau, Guam, etc
    private val citiesWithAreaCodes: Map<StatesEnum, List<Pair<String, LocationData>>> by lazy {
        americaData
            .mapValues { (_, cities) ->
                cities.entries
                    .filter { it.value.areaCodes.isNotEmpty() }
                    .map { it.key to it.value }
                    .toList()
            }
            .filterValues { it.isNotEmpty() }
    }

    private val stateCityZipCache: Map<StatesEnum, List<Triple<String, List<String>, LocationData>>> by lazy {
        americaData
            .filter { (state, _) -> state in validStates }
            .mapValues { (_, cities) ->
                cities.entries
                    .map { (city, locationData) ->
                        Triple(city, locationData.zipCodes, locationData)
                    }
                    .toList()
            }
    }

    private val stateCityCache: Map<StatesEnum, List<String>> by lazy {
        americaData
            .filter { (state, _) -> state in validStates }  // Using validStates instead of excludedStates
            .mapValues { (_, cities) ->
                cities.keys.toList()
            }
    }


    // Pre-compute the common components
    private val streetComponents = object {
        val cachedSuffixes = Address.streetSuffixes.map { it.abbreviation to it.fullName }
        val cachedTrees = Address.treeNames
        val cachedGems = Address.gemTerms
        val cachedTerrain = Address.terrainFeatures.map { it.abbreviation to it.fullName }
        val cachedLandmarks = Address.landmarkTerms.map { it.abbreviation to it.fullName }
        val cachedDirections = Address.cardinalDirections.map { it.abbreviation to it.fullName }
        val cachedPleasant = Address.pleasantRoadAdjectives
        val cachedPatriotic = elias.fakerMaker.fakers.America.patrioticTerms
        val cachedUnion = elias.fakerMaker.fakers.America.union.map { it.split(" ").last() }
        val cachedConfederate = elias.fakerMaker.fakers.America.confederates.map { it.split(" ").last() }
        val cachedFood = elias.fakerMaker.fakers.America.food
        val cachedFirearmTerms = elias.fakerMaker.fakers.America.firearmTerms
        val cachedFirearmTypes = elias.fakerMaker.fakers.America.firearmTypes
        val cachedDiseases = elias.fakerMaker.fakers.America.disease
        val cachedPresidents = elias.fakerMaker.fakers.America.presidents.map { it.split(" ").last() }
        val cachedAddress2 = Address.address2.toList()
    }


    @OptIn(ExperimentalSerializationApi::class)
    @PostConstruct
    fun loadData() {
        try {
            val resource = ClassPathResource("AmericaData.json")
            americaData = resource.inputStream.use { stream ->
                Json.decodeFromStream<AmericaData>(stream)
            }

        } catch (e: Exception) {
            logger.error(e) { "Failed to load AmericaData.json..." }
            throw e
        }
    }

    private fun findOrGenerateAreaCode(state: StatesEnum, city: String? = null): Triple<StatesEnum, String, String> {
        // If the state has no area codes, immediately pick a random state that does
        if (state !in statesWithAreaCodes) {
            val randomStateWithAreaCode = statesWithAreaCodes.random()
            val (randomCity, locationData) = citiesWithAreaCodes[randomStateWithAreaCode]!!.random()
            return Triple(randomStateWithAreaCode, randomCity, locationData.areaCodes.random())
        }

        // Try with the provided city if we have one
        if (city != null) {
            americaData[state]?.get(city)?.areaCodes?.randomOrNull()?.let {
                return Triple(state, city, it)
            }
        }

        // Try to find any city with an area code in this state
        citiesWithAreaCodes[state]?.randomOrNull()?.let { (cityWithCode, locationData) ->
            return Triple(state, cityWithCode, locationData.areaCodes.random())
        }

        // This should never happen since we checked statesWithAreaCodes first
        throw IllegalStateException("Failed to find area code for state $state which should have area codes")
    }

    private fun generateRandomStateCityZip(): Triple<StatesEnum, String, String> {
        val state = citiesWithAreaCodes.keys.random()

        val (city, zipCodes, _) = stateCityZipCache[state]?.randomOrNull() ?:
        throw IllegalStateException("No cities found for state $state")

        val zip = zipCodes.randomOrNull() ?:
        throw IllegalStateException("No zip codes found for city $city in state $state")

        return Triple(state, city, zip)
    }

    private fun List<DataTableItem>?.findState(): StatesEnum? {
        if (this == null) return null

        return find { it.maker == MakerEnum.STATE }
            ?.influencedBy
            ?.find { it is Influencer.State }
            ?.let { influencer ->
                when (influencer) {
                    is Influencer.State -> influencer.state
                    else -> null
                }
            }
    }

    fun state(nickname: String): DataTableItem {
        val state = validStates.random()

        return DataTableItem(
            maker         = MakerEnum.STATE,
            fakersUsed    = null,
            originalValue = null,
            derivedValue  = state.toString(),
            wikiUrl       = WikiUtil.createStateWikiLink(state),
            influencedBy  = listOf(Influencer.State(state)),
            idTypeEnum = null,
            nickname = nickname,
        )
    }

    fun city(existingItems: List<DataTableItem>?, nickname: String): DataTableItem {
        val state = existingItems.findState() ?: validStates.random()
        val cities = stateCityCache[state] ?: throw IllegalStateException(
            "No cities found for state $state. This should never happen as we filter invalid states."
        )
        val city = cities.random()

        return DataTableItem(
            maker = MakerEnum.CITY,
            fakersUsed = null,
            originalValue = "$city, $state",
            derivedValue = city,
            wikiUrl = WikiUtil.createCityWikiLink(state, city),
            influencedBy = listOf(
                Influencer.State(state),
                Influencer.City(city)
            ),
            idTypeEnum = null,
            nickname = nickname
        )
    }

    fun zip(existingItems: List<DataTableItem>?, nickname: String): DataTableItem {
        val (state, city, zip) = if (existingItems.isNullOrEmpty()) {
            generateRandomStateCityZip()
        } else {
            // Try to find existing state from city or state maker
            val existingState = existingItems.findState()

            if (existingState != null) {
                // For existing state, still generate random city and zip
                val (city, zipCodes, _) = stateCityZipCache[existingState]?.randomOrNull() ?:
                throw IllegalStateException("No cities found for state $existingState")
                val zip = zipCodes.randomOrNull() ?:
                throw IllegalStateException("No zip codes found for city $city in state $existingState")
                Triple(existingState, city, zip)
            } else {
                generateRandomStateCityZip()
            }
        }

        // pad the zip with 0s for cases like the Virgin Islands (VI) that have a zip of 00841
        val paddedZip = zip.padStart(5, '0')

        return DataTableItem(
            maker = MakerEnum.ZIP,
            fakersUsed = null,
            originalValue = "$city, $state $zip",
            derivedValue = paddedZip,
            wikiUrl = WikiUtil.createCityWikiLink(state, city),
            influencedBy = listOf(
                Influencer.State(state),
                Influencer.City(city),
                Influencer.Zip(zip)
            ),
            idTypeEnum = null,
            nickname = nickname,
        )
    }

    fun phone(existingItems: List<DataTableItem>?, nickname: String): DataTableItem {
        // Get state and city from existing items if available
        val cityItem = existingItems?.find { it.maker == MakerEnum.CITY }
        val stateFromCity = cityItem?.influencedBy
            ?.filterIsInstance<Influencer.State>()
            ?.firstOrNull()
            ?.state
        val cityName = cityItem?.influencedBy
            ?.filterIsInstance<Influencer.City>()
            ?.firstOrNull()
            ?.city

        // If no city, try to get just the state
        val stateItem = if (stateFromCity == null) {
            existingItems?.find { it.maker == MakerEnum.STATE }
        } else null
        val stateFromState = stateItem?.influencedBy
            ?.filterIsInstance<Influencer.State>()
            ?.firstOrNull()
            ?.state

        // Generate area code based on what we have
        val (state, city, areaCode) = when {
            // If we have both state and city
            stateFromCity != null && cityName != null ->
                findOrGenerateAreaCode(stateFromCity, cityName)
            // If we just have state
            stateFromCity != null || stateFromState != null ->
                findOrGenerateAreaCode(stateFromCity ?: stateFromState!!)
            // If we have nothing
            else -> {
                val randomState = statesWithAreaCodes.random()
                val (randomCity, locationData) = citiesWithAreaCodes[randomState]!!.random()
                Triple(randomState, randomCity, locationData.areaCodes.random())
            }
        }

        val prefix = Random.nextInt(100, 999)
        val lineNumber = Random.nextInt(1000, 9999)

        val phoneFormats = listOf(
            "%s-%s-%s",                // 706-341-2352
            "(%s) %s-%s",              // (706) 341-2352
            "(%s)%s-%s",               // (706)341-2352
            "(%s) %s %s",              // (706) 341 2352
            "%s.%s.%s",                // 706.341.2352
            "%s%s%s",                  // 7063412352
            "+1 %s-%s-%s",             // +1 706-341-2352
            "+1 (%s) %s-%s",           // +1 (706) 341-2352
            "+1%s%s%s",                // +17063412352
            "%s %s %s"                 // 706 341 2352
        )
        val formattedPhone = phoneFormats.random().format(areaCode, prefix, lineNumber)

        return DataTableItem(
            maker = MakerEnum.PHONE,
            fakersUsed = null,
            originalValue = areaCode,
            derivedValue = formattedPhone,
            wikiUrl = WikiUtil.createPhoneWikiLink(areaCode),
            influencedBy = listOfNotNull(
                Influencer.State(state),
                Influencer.City(city),
                Influencer.AreaCode(areaCode),
                existingItems?.find { it.maker == MakerEnum.ZIP }?.let { Influencer.Zip(it.derivedValue) }
            ),
            idTypeEnum = null,
            nickname = nickname
        )
    }

    private fun getSuffix(useAbbrev: Boolean): String =
        streetComponents.cachedSuffixes.random().let { if (useAbbrev) it.first else it.second }

    private fun getTerrain(useAbbrev: Boolean): String =
        streetComponents.cachedTerrain.random().let { if (useAbbrev) it.first else it.second }

    private fun getLandmark(useAbbrev: Boolean): String =
        streetComponents.cachedLandmarks.random().let { if (useAbbrev) it.first else it.second }

    private fun getDirection(useAbbrev: Boolean): String =
        streetComponents.cachedDirections.random().let { if (useAbbrev) it.first else it.second }

    fun address(nickname: String): DataTableItem {
        val numberCount = when (Random.nextInt(3)) {
            0 -> Random.nextInt(1, 10)
            1 -> Random.nextInt(100, 999)
            else -> Random.nextInt(1000, 9999)
        }.toString()

        val useAbbrev = Random.nextBoolean()

        val addressString = when (Random.nextInt(19)) {
            0 -> "$numberCount ${streetComponents.cachedTrees.random()} ${getSuffix(useAbbrev)}"
            1 -> "$numberCount ${streetComponents.cachedPatriotic.random()} ${getSuffix(useAbbrev)}"
            2 -> "$numberCount ${streetComponents.cachedGems.random()} ${getSuffix(useAbbrev)}"
            3 -> "$numberCount General ${streetComponents.cachedUnion.random()} ${getSuffix(useAbbrev)}"
            4 -> "$numberCount ${streetComponents.cachedConfederate.random()} ${getSuffix(useAbbrev)}"
            5 -> "$numberCount ${streetComponents.cachedTrees.random()} ${getTerrain(useAbbrev)}"
            6 -> "$numberCount ${streetComponents.cachedGems.random()} ${getTerrain(useAbbrev)}"
            7 -> "$numberCount ${streetComponents.cachedPleasant.random()} ${getTerrain(useAbbrev)}"
            8 -> "$numberCount ${streetComponents.cachedTrees.random()} ${getLandmark(useAbbrev)}"
            9 -> "$numberCount ${streetComponents.cachedPatriotic.random()} ${getLandmark(useAbbrev)}"
            10 -> "$numberCount ${getDirection(useAbbrev)} ${streetComponents.cachedTrees.random()} ${getSuffix(useAbbrev)}"
            11 -> "$numberCount ${getDirection(useAbbrev)} ${streetComponents.cachedPleasant.random()} ${getTerrain(useAbbrev)}"
            12 -> "$numberCount ${streetComponents.cachedFood.random()} ${getSuffix(useAbbrev)}"
            13 -> "$numberCount ${streetComponents.cachedFood.random()} ${getLandmark(useAbbrev)}"
            14 -> "$numberCount ${streetComponents.cachedFirearmTerms.random()} ${getSuffix(useAbbrev)}"
            15 -> "$numberCount ${streetComponents.cachedFirearmTypes.random()} ${getLandmark(useAbbrev)}"
            16 -> "$numberCount ${streetComponents.cachedDiseases.random()} ${getSuffix(useAbbrev)}"
            17 -> "$numberCount ${streetComponents.cachedPresidents.random()} ${getSuffix(useAbbrev)}"
            else -> "$numberCount Old ${getDirection(useAbbrev)} ${getLandmark(useAbbrev)} ${getSuffix(useAbbrev)}"
        }

        return DataTableItem(
            maker         = MakerEnum.ADDRESS,
            fakersUsed    = null,
            originalValue = null,
            derivedValue  = addressString,
            wikiUrl       = null,
            influencedBy  = null,
            idTypeEnum    = null,
            nickname = nickname,
        )
    }

    fun address2(nickname: String): DataTableItem {
        val baseAddress = streetComponents.cachedAddress2.random().let {
            if (Random.nextBoolean()) it.abbreviation else it.fullName
        }
        val number = when (Random.nextInt(3)) {
            0 -> Random.nextInt(1, 10)        // 1 digit (1-9)
            1 -> Random.nextInt(10, 100)      // 2 digits (10-99)
            else -> Random.nextInt(100, 1000)  // 3 digits (100-999)
        }

        return DataTableItem(
            maker         = MakerEnum.ADDRESS_2,
            fakersUsed    = null,
            originalValue = null,
            derivedValue  = "$baseAddress $number",
            wikiUrl       = null,
            influencedBy  = null,
            idTypeEnum    = null,
            nickname = nickname
        )
    }


}