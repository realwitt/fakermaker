package elias.fakerMaker.generator

import elias.fakerMaker.types.DataTableItem
import elias.fakerMaker.enums.MakerEnum
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

object DateGenerator {
    private val FORMATTER = DateTimeFormatter
        .ofPattern("MMM-dd-yyyy")
        .withLocale(Locale.US)
        .withZone(ZoneOffset.UTC)

    // Calculate default start date once (100 years ago)
    private val DEFAULT_START = System.currentTimeMillis() - (100L * 365 * 24 * 60 * 60 * 1000)

    fun dateRange(start: Long?, end: Long?, nickname: String): DataTableItem {
        val startTime = start ?: DEFAULT_START
        val endTime = end ?: System.currentTimeMillis()

        val (minTime, maxTime) = if (startTime <= endTime) startTime to endTime else endTime to startTime
        val randomTime = minTime + Random.nextLong(maxTime - minTime + 1)

        return DataTableItem(
            maker = MakerEnum.DATE,
            fakersUsed = null,
            originalValue = null,
            derivedValue = Instant.ofEpochMilli(randomTime)
                .atZone(ZoneOffset.UTC)
                .format(FORMATTER)
                .uppercase(),
            wikiUrl = null,
            influencedBy = null,
            idTypeEnum = null,
            nickname = nickname,
        )
    }
}