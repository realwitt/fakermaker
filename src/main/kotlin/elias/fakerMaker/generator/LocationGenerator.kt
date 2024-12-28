package elias.fakerMaker.generator

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.fakers.tvshows.GravityFalls
import elias.fakerMaker.fakers.tvshows.ParksAndRec
import elias.fakerMaker.fakers.tvshows.Pokemon
import elias.fakerMaker.fakers.tvshows.TheOffice
import elias.fakerMaker.fakers.videogames.CallOfDuty

object LocationGenerator {
    private val cachedCallOfDutyLocations = CallOfDuty.maps.toList()
    private val cachedGravityFallsLocations = GravityFalls.locations.toList()
    private val cachedParksAndRecLocations = ParksAndRec.locations.toList()
    private val cachedPokemonLocations = Pokemon.locations.toList()
    private val cachedOfficeLocations = TheOffice.locations.toList()

    private fun location(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val namesMap = emptyMap<FakerEnum, String>().toMutableMap()
        for (faker in fakers) {
            when (faker) {
                FakerEnum.CALL_OF_DUTY -> namesMap[FakerEnum.CALL_OF_DUTY] = cachedCallOfDutyLocations.random()
                FakerEnum.GRAVITY_FALLS -> namesMap[FakerEnum.GRAVITY_FALLS] = cachedGravityFallsLocations.random()
                FakerEnum.PARKS_AND_REC -> namesMap[FakerEnum.PARKS_AND_REC] = cachedParksAndRecLocations.random()
                FakerEnum.POKEMON -> namesMap[FakerEnum.POKEMON] = cachedPokemonLocations.random()
                FakerEnum.THE_OFFICE -> namesMap[FakerEnum.THE_OFFICE] = cachedOfficeLocations.random()
                else -> continue
            }
        }
        return namesMap.toMap()
    }

}
