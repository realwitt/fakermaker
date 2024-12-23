package elias.fakerMaker.generator

import elias.fakerMaker.dto.AmericaData
import elias.fakerMaker.dto.DataTableItem
import elias.fakerMaker.dto.Influencer
import elias.fakerMaker.enums.MakerEnum
import elias.fakerMaker.enums.StatesEnum
import elias.fakerMaker.fakers.Address
import elias.fakerMaker.fakers.history.America
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

    private fun generateRandomStateCityAreaCode(): Triple<StatesEnum, String, String> {
        val state = validStates.random()

        repeat(3) {
            val cityEntry = americaData[state]?.entries?.random()
            if (cityEntry?.value?.areaCodes?.isNotEmpty() == true) {
                return Triple(state, cityEntry.key, cityEntry.value.areaCodes.random())
            }
        }

        throw IllegalStateException("Unable to find any state/city with area codes after multiple attempts")
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

    fun address(): DataTableItem {
        val shouldUseAbbreviation = Random.nextBoolean()
        val numberCount = when (Random.nextInt(3)) {
            0 -> Random.nextInt(1, 10)
            1 -> Random.nextInt(100, 999)
            else -> Random.nextInt(1000, 9999)
        }.toString()

        val patterns = listOf(
            // Basic Tree Streets (e.g., "123 Maple Dr.")
            { "$numberCount ${Address.treeNames.random()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Patriotic Streets (e.g., "123 Liberty Ave.")
            { "$numberCount ${America.patrioticTerms.random()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Gem Streets (e.g., "123 Ruby Rd.")
            { "$numberCount ${Address.gemTerms.random()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Union General Streets (e.g., "123 General Sherman Blvd.")
            { "$numberCount General ${America.union.random().split(" ").last()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Confederate Streets (e.g., "123 Lee Ave.")
            { "$numberCount ${America.confederates.random().split(" ").last()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Nature Combinations (e.g., "123 Oak Valley Dr.")
            { "$numberCount ${Address.treeNames.random()} ${Address.terrainFeatures.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Gem + Terrain (e.g., "123 Ruby Ridge Rd.")
            { "$numberCount ${Address.gemTerms.random()} ${Address.terrainFeatures.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Pleasant + Terrain (e.g., "123 Pleasant Valley")
            { "$numberCount ${Address.pleasantRoadAdjectives.random()} ${Address.terrainFeatures.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Tree + Landmark (e.g., "123 Oak Mill")
            { "$numberCount ${Address.treeNames.random()} ${Address.landmarkTerms.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Patriotic + Landmark (e.g., "123 Liberty Square")
            { "$numberCount ${America.patrioticTerms.random()} ${Address.landmarkTerms.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Directional + Tree (e.g., "123 N. Oak St.")
            { "$numberCount ${Address.cardinalDirections.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }} ${Address.treeNames.random()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Directional + Pleasant (e.g., "123 S. Pleasant Valley")
            { "$numberCount ${Address.cardinalDirections.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }} ${Address.pleasantRoadAdjectives.random()} ${Address.terrainFeatures.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Food Streets (e.g., "123 Apple Pie Ln.")
            { "$numberCount ${America.foodTerms.random()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Food + Landmark (e.g., "123 Barbecue Station")
            { "$numberCount ${America.foodTerms.random()} ${Address.landmarkTerms.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Firearm Streets (e.g., "123 Winchester Rd.")
            { "$numberCount ${America.firearmTerms.random()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Firearm Type + Landmark (e.g., "123 Rifle Range")
            { "$numberCount ${America.firearmTypes.random()} ${Address.landmarkTerms.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Medical Streets (e.g., "123 Diabetes Ln.")
            { "$numberCount ${America.disease.random()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },
            // Disease + Terrain (e.g., "123 Heart Attack Valley")
            { "$numberCount ${America.disease.random()} ${Address.terrainFeatures.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Presidential Streets (e.g., "123 Washington Ave.")
            { "$numberCount ${America.presidents.random().split(" ").last()} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" },

            // Common Triple Combinations (e.g., "123 Old North Mill Rd.")
            { "$numberCount Old ${Address.cardinalDirections.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }} ${Address.landmarkTerms.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }} ${Address.streetSuffixed.random().let{ if (shouldUseAbbreviation) it.abbreviation else it.fullName }}" }
        )
        // Select and execute one random pattern
        val address = patterns.random().invoke()

        return DataTableItem(
            maker         = MakerEnum.ADDRESS,
            fakersUsed    = null,
            originalValue = null,
            derivedValue  = address,
            wikiUrl       = null,
            influencedBy = null,
        )
    }

    fun address2(): DataTableItem {
        return DataTableItem(
            maker         = MakerEnum.ADDRESS,
            fakersUsed    = null,
            originalValue = null,
            derivedValue  = Address.address2.random().let { if (Random.nextBoolean()) it.abbreviation else it.fullName },
            wikiUrl       = null,
            influencedBy = null,
        )
    }


}