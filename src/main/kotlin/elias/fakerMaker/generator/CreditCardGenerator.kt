package elias.fakerMaker.generator

import elias.fakerMaker.types.DataTableItem
import elias.fakerMaker.enums.MakerEnum
import kotlin.random.Random

object CreditCardGenerator {
    // Pre-compute common values with proper display names
    private val cardTypes = mapOf(
        "VISA" to CardTypeInfo(listOf("4"), 16, "Visa"),
        "MASTERCARD" to CardTypeInfo(listOf("51","52","53","54","55"), 16, "Mastercard"),
        "AMEX" to CardTypeInfo(listOf("34", "37"), 15, "American Express"),
        "DISCOVER" to CardTypeInfo(listOf("6011", "644", "645", "646", "647", "648", "649", "65"), 16, "Discover")
    )

    private data class CardTypeInfo(
        val prefixes: List<String>,
        val length: Int,
        val displayName: String,
        val cvvLength: Int = if (prefixes.first().startsWith("3")) 4 else 3  // AMEX starts with 3
    )

    private val cardTypeEntries = cardTypes.entries.toList()

    // ThreadLocal StringBuilder for number generation
    private val cardNumberBuilder = ThreadLocal.withInitial { StringBuilder(19) }

    // ThreadLocal StringBuilder for CVV generation
    private val cvvBuilder = ThreadLocal.withInitial { StringBuilder(4) }

    fun creditCard(): DataTableItem {
        // Get random card type info
        val (cardType, info) = cardTypeEntries.random()
        val prefix = info.prefixes.random()

        // Generate card number
        val number = generateValidCardNumber(prefix, info.length)

        return DataTableItem(
            maker = MakerEnum.CREDIT_CARD_NUMBER,
            fakersUsed = null,
            originalValue = info.displayName,
            derivedValue = number,
            wikiUrl = null,
            influencedBy = null
        )
    }

    fun cvv(items: List<DataTableItem>?): DataTableItem {
        val cardItem = items?.find { it.maker == MakerEnum.CREDIT_CARD_NUMBER }
        val cardType = cardItem?.originalValue ?: cardTypeEntries.random().key
        val cvvLength = cardTypes[cardType]?.cvvLength ?: 3

        val cvv = cvvBuilder.get().apply {
            clear()
            repeat(cvvLength) {
                append(Random.nextInt(10))
            }
        }.toString()

        return DataTableItem(
            maker = MakerEnum.CREDIT_CARD_CVV,
            fakersUsed = null,
            originalValue = "CVV for $cardType",
            derivedValue = cvv,
            wikiUrl = null,
            influencedBy = null
        )
    }

    private fun generateValidCardNumber(prefix: String, length: Int): String {
        return cardNumberBuilder.get().apply {
            clear()
            append(prefix)

            // Generate random digits (excluding checksum position)
            val randomDigitsNeeded = length - prefix.length - 1
            repeat(randomDigitsNeeded) {
                append(Random.nextInt(10))
            }

            // Calculate and append checksum
            append(calculateLuhnChecksum(this))
        }.toString()
    }

    private fun calculateLuhnChecksum(number: StringBuilder): Int {
        var sum = 0
        var isEven = true  // Starting from right, first position is considered odd

        // Process digits right to left
        for (i in number.length - 1 downTo 0) {
            var digit = number[i].digitToInt()

            if (isEven) {
                digit *= 2
                if (digit > 9) {
                    digit -= 9
                }
            }

            sum += digit
            isEven = !isEven
        }

        return (10 - (sum % 10)) % 10
    }
}