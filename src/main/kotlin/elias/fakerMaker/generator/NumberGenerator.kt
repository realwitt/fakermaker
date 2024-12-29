package elias.fakerMaker.generator

import elias.fakerMaker.types.dto.DataTableItem
import elias.fakerMaker.enums.MakerEnum
import java.text.NumberFormat
import java.util.Locale
import kotlin.random.Random

object NumberGenerator {
    // Pre-compute and cache all ranges for better performance
    private val defaultNumberRanges = listOf(
        0..100,
        100..1000,
        1000..10000,
        10000..100000
    )

    private val defaultPriceRanges = listOf(
        0.0 to 100.0,
        100.0 to 1000.0,
        1000.0 to 10000.0,
        10000.0 to 100000.0
    )

    // Create and cache currency formatter for US dollars
    private val currencyFormatter = ThreadLocal.withInitial {
        NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }

    fun numRange(num1: Int?, num2: Int?): DataTableItem {
        val (start, end) = if (num1 != null && num2 != null) {
            if (num1 <= num2) num1 to num2 else num2 to num1
        } else {
            val range = defaultNumberRanges.random()
            range.first to range.last
        }

        return DataTableItem(
            maker = MakerEnum.NUMBER_REGULAR,
            fakersUsed = null,
            originalValue = null,
            derivedValue = Random.nextInt(start, end + 1).toString(),
            wikiUrl = null,
            influencedBy = null
        )
    }

    fun priceRange(min: Int?, max: Int?): DataTableItem {
        val (start, end) = if (min != null && max != null) {
            val minDouble = min.toDouble()
            val maxDouble = max.toDouble()
            if (minDouble <= maxDouble) minDouble to maxDouble else maxDouble to minDouble
        } else {
            defaultPriceRanges.random()
        }

        // Generate random amount with 2 decimal places
        val randomPrice = start + Random.nextDouble() * (end - start)

        // Format the price using the cached currency formatter
        val formattedPrice = currencyFormatter.get().format(randomPrice)

        return DataTableItem(
            maker = MakerEnum.NUMBER_PRICE,
            fakersUsed = null,
            originalValue = null,
            derivedValue = formattedPrice,
            wikiUrl = null,
            influencedBy = null
        )
    }
}