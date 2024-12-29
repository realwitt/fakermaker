package elias.fakerMaker.generator

import elias.fakerMaker.types.dto.DataTableItem
import elias.fakerMaker.enums.MakerEnum
import kotlin.random.Random

object BooleanGenerator {
    // Pre-compute these strings since they're constant and frequently used
    private const val TRUE_STRING = "True"
    private const val FALSE_STRING = "False"

    fun bool(): DataTableItem {
        val value = if (Random.nextInt(2) == 1) TRUE_STRING else FALSE_STRING

        return DataTableItem(
            maker = MakerEnum.BOOLEAN,
            fakersUsed = null,
            originalValue = null,
            derivedValue = value,
            wikiUrl = null,
            influencedBy = null
        )
    }
}