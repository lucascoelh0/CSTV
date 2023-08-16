package com.example.core.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class TimeUtilsTest {

    private val timeUtils = TimeUtils

    @Test
    fun `getTodayDateUtcString returns current date in UTC timezone formatted to ISO 8601`() {
        val result = timeUtils.getTodayDateUtcString()
        val expected = LocalDateTime
            .now()
            .atZone(ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern(ISO_8601_DATE_TIME_UTC))
        assertEquals(expected, result)
    }

    @Test
    fun `getCurrentWeekLastDayUtcString returns the last day of the current week in UTC timezone formatted to ISO 8601`() {
        val result = timeUtils.getCurrentWeekLastDayUtcString()
        val expected = LocalDateTime
            .now()
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .atZone(ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern(ISO_8601_DATE_TIME_UTC))
        assertEquals(expected, result)
    }

    companion object {
        private const val ISO_8601_DATE_TIME_UTC = "yyyy-MM-dd'T'HH:mm:ssXXX"
    }
}
