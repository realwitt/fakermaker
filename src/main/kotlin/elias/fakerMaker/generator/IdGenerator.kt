package elias.fakerMaker.generator

import elias.fakerMaker.types.DataTableItem
import elias.fakerMaker.enums.IdTypeEnum
import elias.fakerMaker.enums.MakerEnum
import java.util.UUID
import kotlin.random.Random

object IdGenerator {
    // Precomputed values for better performance
    private val BASE_62_CHARS = ('0'..'9') + ('A'..'Z') + ('a'..'z')
    private const val NANO_ID_LENGTH = 21
    private const val SHORT_ID_LENGTH = 12
    private const val NUMERIC_ID_MIN = 1000000
    private const val NUMERIC_ID_MAX = 999999999

    // Regex pattern for MongoDB ObjectId-like format
    private val HEX_CHARS = "0123456789abcdef".toCharArray()
    private const val OBJECT_ID_LENGTH = 24

    fun id(): DataTableItem {
        val idType = IdTypeEnum.entries.random()
        val generatedId = when(idType) {
            IdTypeEnum.UUID -> generateUUID()
            IdTypeEnum.NANO_ID -> generateNanoId()
            IdTypeEnum.SHORT_ID -> generateShortId()
            IdTypeEnum.NUMERIC_ID -> generateNumericId()
            IdTypeEnum.OBJECT_ID -> generateObjectId()
        }

        return DataTableItem(
            maker = MakerEnum.ID,
            fakersUsed = null,
            originalValue = idType.prettyName,
            derivedValue = generatedId,
            wikiUrl = null,
            influencedBy = null
        )
    }

    // UUID v4
    private fun generateUUID(): String = UUID.randomUUID().toString()

    // NanoID-like format (21 chars, URL-friendly)
    private fun generateNanoId(): String = buildString(NANO_ID_LENGTH) {
        repeat(NANO_ID_LENGTH) {
            append(BASE_62_CHARS.random())
        }
    }

    // Short ID (12 chars, URL-friendly)
    private fun generateShortId(): String = buildString(SHORT_ID_LENGTH) {
        repeat(SHORT_ID_LENGTH) {
            append(BASE_62_CHARS.random())
        }
    }

    // Simple numeric ID
    private fun generateNumericId(): String =
        Random.nextInt(NUMERIC_ID_MIN, NUMERIC_ID_MAX).toString()

    // MongoDB ObjectId-like format (24 hex chars)
    private fun generateObjectId(): String = buildString(OBJECT_ID_LENGTH) {
        repeat(OBJECT_ID_LENGTH) {
            append(HEX_CHARS.random())
        }
    }
}