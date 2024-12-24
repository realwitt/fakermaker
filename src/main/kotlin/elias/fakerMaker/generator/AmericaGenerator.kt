package elias.fakerMaker.generator

import elias.fakerMaker.dto.AmericaData
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.dto.Influencer
import elias.fakerMaker.dto.LocationData
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.enums.StatesEnum
import elias.fakerMaker.fakers.Address
import elias.fakerMaker.utils.WikiUtil
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
    private lateinit var americaData: AmericaData
    private val logger = KotlinLogging.logger {}
    private val excludedStates = setOf(
        StatesEnum.state
    )
    val validStates = setOf(
        StatesEnum.AK, StatesEnum.AL, StatesEnum.AZ, StatesEnum.AR, StatesEnum.CA, StatesEnum.CO,
        StatesEnum.CT, StatesEnum.DE, StatesEnum.FL, StatesEnum.GA, StatesEnum.HI, StatesEnum.ID,
        StatesEnum.IL, StatesEnum.IN, StatesEnum.IA, StatesEnum.KS, StatesEnum.KY, StatesEnum.LA,
        StatesEnum.ME, StatesEnum.MD, StatesEnum.MA, StatesEnum.MI, StatesEnum.MN, StatesEnum.MS,
        StatesEnum.MO, StatesEnum.MT, StatesEnum.NE, StatesEnum.NV, StatesEnum.NH, StatesEnum.NJ,
        StatesEnum.NM, StatesEnum.NY, StatesEnum.NC, StatesEnum.ND, StatesEnum.OH, StatesEnum.OK,
        StatesEnum.OR, StatesEnum.PA, StatesEnum.RI, StatesEnum.SC, StatesEnum.SD, StatesEnum.TN,
        StatesEnum.TX, StatesEnum.UT, StatesEnum.VT, StatesEnum.VA, StatesEnum.WA, StatesEnum.WV,
        StatesEnum.WI, StatesEnum.WY, )

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

    private fun generateRandomStateCityAreaCode(): Triple<StatesEnum, String, String> {
        val state = citiesWithAreaCodes.keys.random()

        val (city, locationData) = citiesWithAreaCodes[state]?.random()
            ?: throw IllegalStateException("No cities found with area codes for state $state")

        return Triple(state, city, locationData.areaCodes.random())
    }

    private fun generateRandomStateCityZip(): Triple<StatesEnum, String, String> {
        val state = StatesEnum.entries
            .filter { it !in excludedStates }
            .random()
        val city = americaData[state]?.entries?.random()?.key ?: run {
            throw IllegalStateException("Somehow the state $state has no cities...")
        }
        val zip = americaData[state]?.get(city)?.zipCodes?.random() ?: run {
            throw IllegalStateException("City '$city' somehow has no zip code")
        }
        return Triple(state, city, zip)
    }

    fun state(): DataTableItem {
        val state = StatesEnum.entries
            .filter { it !in excludedStates }
            .random()

        return DataTableItem(
            maker         = MakerEnum.STATE,
            fakersUsed    = null,
            originalValue = null,
            derivedValue  = state.toString(),
            wikiUrl       = WikiUtil.createStateWikiLink(state),
            influencedBy  = listOf(Influencer.State(state))
        )
    }

    fun city(existingItems: List<DataTableItem>?): DataTableItem {
        // the switchboard service will order the MakerEnums for us
        // if zip exists, it will be influenced by city, never the other way around

        // check if state already exists
        val state = existingItems
            ?.find { it.maker == MakerEnum.STATE }
            ?.influencedBy
            ?.find { it is Influencer.State }
            ?.let { influenceType ->
                when (influenceType) {
                    is Influencer.State -> influenceType.state
                    else -> null
                }
                // if not, generate random valid one
            } ?: StatesEnum.entries
            .filter { it !in excludedStates }
            .random()

        val city = americaData[state]?.entries?.random()?.key ?: run {
            throw IllegalStateException("Somehow the state $state has no cities?? ;-;")
        }

        return DataTableItem(
            maker         = MakerEnum.CITY,
            fakersUsed    = null,
            originalValue = "$city, $state",
            derivedValue  = city,
            wikiUrl       = WikiUtil.createCityWikiLink(state, city),
            influencedBy  = listOf(
                Influencer.State(state),
                Influencer.City(city)
            )
        )
    }

    fun zip(existingItems: List<DataTableItem>?): DataTableItem {
        val (state, city, zip) = if (existingItems.isNullOrEmpty()) {
            generateRandomStateCityZip()
        } else {
            // Try to find city first (which will have state)
            existingItems.find { it.maker == MakerEnum.CITY }
                ?.let { cityDataTableItem ->
                    val state = cityDataTableItem.influencedBy
                        ?.find { it is Influencer.State }
                        ?.let { influencer ->
                            when (influencer) {
                                is Influencer.State -> influencer.state
                                else -> null
                            }
                        }
                    val city = cityDataTableItem.influencedBy
                        ?.find { it is Influencer.City }
                        ?.let { influencer ->
                            when (influencer) {
                                is Influencer.City -> influencer.city
                                else -> null
                            }
                        }
                    if (state != null && city != null) {
                        val zip = americaData[state]?.get(city)?.zipCodes?.random()
                        if (zip != null) Triple(state, city, zip) else null
                    } else {
                        throw IllegalStateException("Somehow the city ${city},${state} has no zip?? ;-;")
                    }
                }
            // If no city found, try to find state
                ?: existingItems.find { it.maker == MakerEnum.STATE }
                    ?.influencedBy
                    ?.find { it is Influencer.State }
                    ?.let { influenceType ->
                        when (influenceType) {
                            is Influencer.State -> {
                                val state = influenceType.state
                                val city = americaData[state]?.entries?.random()?.key
                                val zip = americaData[state]?.get(city)?.zipCodes?.random()
                                if (city != null && zip != null) Triple(state, city, zip)
                                else {
                                    throw IllegalStateException("Somehow the city ${city},${state} has no zip?? ;-;")
                                }
                            }
                            else -> null
                        }
                    }
                // No city or state found, generate everything randomly
                ?: generateRandomStateCityZip()
        }

        return DataTableItem(
            maker         = MakerEnum.ZIP,
            fakersUsed    = null,
            originalValue = "$city, $state $zip",
            derivedValue  = zip,
            wikiUrl       = WikiUtil.createCityWikiLink(state, city),
            influencedBy  = listOf(
                Influencer.State(state),
                Influencer.City(city),
                Influencer.Zip(zip)
            )
        )
    }

    fun phone(existingItems: List<DataTableItem>?): DataTableItem {
        val (state, city, areaCode) = (if (existingItems.isNullOrEmpty()) {
            generateRandomStateCityAreaCode()
        } else
        // Try to find city first (which will have state)
            existingItems.find { it.maker == MakerEnum.CITY }
                ?.let { cityDataTableItem ->
                    val state = cityDataTableItem.influencedBy
                        ?.find { it is Influencer.State }
                        ?.let { influencer ->
                            when (influencer) {
                                is Influencer.State -> influencer.state
                                else -> null
                            }
                        }
                    val city = cityDataTableItem.influencedBy
                        ?.find { it is Influencer.City }
                        ?.let { influencer ->
                            when (influencer) {
                                is Influencer.City -> influencer.city
                                else -> null
                            }
                        }
                    if (state != null && city != null) {
                        val areaCode = americaData[state]?.get(city)?.areaCodes?.randomOrNull()
                        if (areaCode != null) Triple(state, city, areaCode) else {
                            generateRandomStateCityAreaCode()
                        }
                    } else null
                }
            // If no city found, try to find zip
                ?: existingItems.find { it.maker == MakerEnum.ZIP }
                    ?.influencedBy
                    ?.let { influencers ->
                        val state = influencers.find { it is Influencer.State }?.let {
                            when (it) {
                                is Influencer.State -> it.state
                                else -> null
                            }
                        }
                        val city = influencers.find { it is Influencer.City }?.let {
                            when (it) {
                                is Influencer.City -> it.city
                                else -> null
                            }
                        }
                        if (state != null && city != null) {
                            val areaCode = americaData[state]?.get(city)?.areaCodes?.randomOrNull()
                            if (areaCode != null) Triple(state, city, areaCode) else {
                                generateRandomStateCityAreaCode()
                            }
                        } else null
                    }
                // If no zip found, try to find state
                ?: existingItems.find { it.maker == MakerEnum.STATE }
                    ?.influencedBy
                    ?.find { it is Influencer.State }
                    ?.let { influenceType ->
                        when (influenceType) {
                            is Influencer.State -> {
                                val state = influenceType.state
                                val city = americaData[state]?.entries?.random()?.key
                                if (city != null) {
                                    val areaCode = americaData[state]?.get(city)?.areaCodes?.randomOrNull()
                                    if (areaCode != null) Triple(state, city, areaCode) else {
                                        generateRandomStateCityAreaCode()
                                    }
                                } else null
                            }

                            else -> null
                        }
                    }
                // No city, zip, or state found, generate everything randomly
                ?: generateRandomStateCityAreaCode())

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
            maker         = MakerEnum.PHONE,
            fakersUsed    = null,
            originalValue = areaCode,
            derivedValue  = formattedPhone,
            wikiUrl       = WikiUtil.createPhoneWikiLink(areaCode),
            influencedBy = listOfNotNull(
                Influencer.State(state),
                Influencer.City(city),
                Influencer.AreaCode(areaCode),
                existingItems?.find { it.maker == MakerEnum.ZIP }?.let { Influencer.Zip(it.derivedValue) }
            )
        )
    }

    // Pre-compute the common components
    private val streetComponents = object {
        val suffixes = Address.streetSuffixed.map { it.abbreviation to it.fullName }
        val trees = Address.treeNames
        val gems = Address.gemTerms
        val terrain = Address.terrainFeatures.map { it.abbreviation to it.fullName }
        val landmarks = Address.landmarkTerms.map { it.abbreviation to it.fullName }
        val directions = Address.cardinalDirections.map { it.abbreviation to it.fullName }
        val pleasant = Address.pleasantRoadAdjectives
        val patriotic = elias.fakerMaker.fakers.America.patrioticTerms
        val union = elias.fakerMaker.fakers.America.union.map { it.split(" ").last() }
        val confederate = elias.fakerMaker.fakers.America.confederates.map { it.split(" ").last() }
        val food = elias.fakerMaker.fakers.America.food
        val firearms = elias.fakerMaker.fakers.America.firearmTerms
        val firearmTypes = elias.fakerMaker.fakers.America.firearmTypes
        val diseases = elias.fakerMaker.fakers.America.disease
        val presidents = elias.fakerMaker.fakers.America.presidents.map { it.split(" ").last() }
    }

    private fun getSuffix(useAbbrev: Boolean): String =
        streetComponents.suffixes.random().let { if (useAbbrev) it.first else it.second }

    private fun getTerrain(useAbbrev: Boolean): String =
        streetComponents.terrain.random().let { if (useAbbrev) it.first else it.second }

    private fun getLandmark(useAbbrev: Boolean): String =
        streetComponents.landmarks.random().let { if (useAbbrev) it.first else it.second }

    private fun getDirection(useAbbrev: Boolean): String =
        streetComponents.directions.random().let { if (useAbbrev) it.first else it.second }

    fun address(): DataTableItem {
        val numberCount = when (Random.nextInt(3)) {
            0 -> Random.nextInt(1, 10)
            1 -> Random.nextInt(100, 999)
            else -> Random.nextInt(1000, 9999)
        }.toString()

        val useAbbrev = Random.nextBoolean()

        val addressString = when (Random.nextInt(19)) {
            0 -> "$numberCount ${streetComponents.trees.random()} ${getSuffix(useAbbrev)}"
            1 -> "$numberCount ${streetComponents.patriotic.random()} ${getSuffix(useAbbrev)}"
            2 -> "$numberCount ${streetComponents.gems.random()} ${getSuffix(useAbbrev)}"
            3 -> "$numberCount General ${streetComponents.union.random()} ${getSuffix(useAbbrev)}"
            4 -> "$numberCount ${streetComponents.confederate.random()} ${getSuffix(useAbbrev)}"
            5 -> "$numberCount ${streetComponents.trees.random()} ${getTerrain(useAbbrev)}"
            6 -> "$numberCount ${streetComponents.gems.random()} ${getTerrain(useAbbrev)}"
            7 -> "$numberCount ${streetComponents.pleasant.random()} ${getTerrain(useAbbrev)}"
            8 -> "$numberCount ${streetComponents.trees.random()} ${getLandmark(useAbbrev)}"
            9 -> "$numberCount ${streetComponents.patriotic.random()} ${getLandmark(useAbbrev)}"
            10 -> "$numberCount ${getDirection(useAbbrev)} ${streetComponents.trees.random()} ${getSuffix(useAbbrev)}"
            11 -> "$numberCount ${getDirection(useAbbrev)} ${streetComponents.pleasant.random()} ${getTerrain(useAbbrev)}"
            12 -> "$numberCount ${streetComponents.food.random()} ${getSuffix(useAbbrev)}"
            13 -> "$numberCount ${streetComponents.food.random()} ${getLandmark(useAbbrev)}"
            14 -> "$numberCount ${streetComponents.firearms.random()} ${getSuffix(useAbbrev)}"
            15 -> "$numberCount ${streetComponents.firearmTypes.random()} ${getLandmark(useAbbrev)}"
            16 -> "$numberCount ${streetComponents.diseases.random()} ${getSuffix(useAbbrev)}"
            17 -> "$numberCount ${streetComponents.presidents.random()} ${getSuffix(useAbbrev)}"
            else -> "$numberCount Old ${getDirection(useAbbrev)} ${getLandmark(useAbbrev)} ${getSuffix(useAbbrev)}"
        }

        return DataTableItem(
            maker = MakerEnum.ADDRESS_2,
            fakersUsed = null,
            originalValue = null,
            derivedValue = Address.address2.random().let { if (Random.nextBoolean()) it.abbreviation else it.fullName },
            wikiUrl = null,
            influencedBy = null
        )
    }

    fun address2(): DataTableItem {
        // todo: the address 2 needs a number after it, random 1, 2, or 3 digit number
        return DataTableItem(
            maker         = MakerEnum.ADDRESS_2,
            fakersUsed    = null,
            originalValue = null,
            derivedValue  = Address.address2.random().let { if (Random.nextBoolean()) it.abbreviation else it.fullName },
            wikiUrl       = null,
            influencedBy = null,
        )
    }


}