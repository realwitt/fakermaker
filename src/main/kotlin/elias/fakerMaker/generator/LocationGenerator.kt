package elias.fakerMaker.generator

import elias.fakerMaker.enums.FakerEnum
import elias.fakerMaker.fakers.Tech
import elias.fakerMaker.fakers.tvshow.*

object LocationGenerator {
    private fun location(fakers: List<FakerEnum>): Map<FakerEnum, String> {
        val namesMap = emptyMap<FakerEnum, String>().toMutableMap()
        for (faker in fakers) {
            when (faker) {
                FakerEnum.CALL_OF_DUTY -> namesMap[FakerEnum.THE_OFFICE] = TheOffice.locations.random()
                FakerEnum.GRAVITY_FALLS -> namesMap[FakerEnum.GRAVITY_FALLS] = GravityFalls.locations.random()
                FakerEnum.PARKS_AND_REC -> namesMap[FakerEnum.PARKS_AND_REC] = ParksAndRec.locations.random()
                FakerEnum.POKEMON -> namesMap[FakerEnum.POKEMON] = Pokemon.locations.random()
                FakerEnum.THE_OFFICE -> namesMap[FakerEnum.THE_OFFICE] = TheOffice.locations.random()
                else -> continue
            }
        }
        return namesMap.toMap()
    }

}
