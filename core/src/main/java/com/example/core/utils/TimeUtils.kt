package com.example.core.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object TimeUtils {

    private const val ISO_8601_DATE_TIME_UTC = "yyyy-MM-dd'T'HH:mm:ssXXX"
    private const val HOUR_MINUTE = "HH:mm"
    private const val DAY_MONTH_HOUR_MINUTE = "dd.MM HH:mm"
    private const val TODAY = "Hoje"
    private const val MONDAY = "Seg"
    private const val TUESDAY = "Ter"
    private const val WEDNESDAY = "Qua"
    private const val THURSDAY = "Qui"
    private const val FRIDAY = "Sex"
    private const val SATURDAY = "Sáb"
    private const val SUNDAY = "Dom"

    fun formatBeginDate(date: String): String {
        val formatter = DateTimeFormatter.ofPattern(ISO_8601_DATE_TIME_UTC)
        val dateTimeUTC = ZonedDateTime.parse(date, formatter)
        val dateTimeLocal = dateTimeUTC.withZoneSameInstant(ZoneId.systemDefault())
        val today = LocalDate.now(ZoneId.systemDefault())
        val endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

        return when {
            dateTimeLocal.toLocalDate() == today -> {
                "$TODAY, ${dateTimeLocal.format(DateTimeFormatter.ofPattern(HOUR_MINUTE))}"
            }

            dateTimeLocal.toLocalDate().isAfter(today) && dateTimeLocal.toLocalDate()
                .isBefore(endOfWeek.plusDays(1)) -> {
                val abbreviatedDayName = when (dateTimeLocal.dayOfWeek) {
                    DayOfWeek.MONDAY -> MONDAY
                    DayOfWeek.TUESDAY -> TUESDAY
                    DayOfWeek.WEDNESDAY -> WEDNESDAY
                    DayOfWeek.THURSDAY -> THURSDAY
                    DayOfWeek.FRIDAY -> FRIDAY
                    DayOfWeek.SATURDAY -> SATURDAY
                    else -> SUNDAY
                }
                "$abbreviatedDayName, ${dateTimeLocal.format(DateTimeFormatter.ofPattern(HOUR_MINUTE))}"
            }

            else -> dateTimeLocal.format(DateTimeFormatter.ofPattern(DAY_MONTH_HOUR_MINUTE))
        }
    }

    fun getTodayDateUtcString(): String {
        val formatter = DateTimeFormatter.ofPattern(ISO_8601_DATE_TIME_UTC)
        return formatter.format(LocalDateTime.now().atZone(ZoneOffset.UTC))
    }

    fun getCurrentWeekLastDayUtcString(): String {
        val endOfWeek = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val formatter = DateTimeFormatter.ofPattern(ISO_8601_DATE_TIME_UTC)
        return formatter.format(endOfWeek.atZone(ZoneOffset.UTC))
    }
}
