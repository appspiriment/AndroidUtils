package com.appspiriment.utils.time

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.TemporalAdjusters
import java.util.Locale

fun LocalDateTime.format(pattern: String): String =
    DateTimeFormatterBuilder().appendPattern(pattern).toFormatter(Locale.ENGLISH).format(this)

fun millisToLocalDateTime(millis: Long): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())

fun LocalDateTime.next(week: DayOfWeek): LocalDateTime = with(TemporalAdjusters.next(week))

fun String.toLocalDateTimeOrNull(pattern: String): LocalDateTime? {
    return try {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH))
    } catch (e: Exception) {
        null
    }
}


fun String.toLocalDateTimeOrNow(pattern: String): LocalDateTime {
    return toLocalDateTimeOrNull(pattern) ?: LocalDateTime.now()
}

val LocalDateTime.time_hhmm_a: String get() = format("hh:mm a")
val LocalDateTime.time_hhmmss_a: String get() = format("hh:mm:ss a")
val LocalDateTime.time_HHmm: String get() = format("HH:mm")
val LocalDateTime.date_yyyymmdd: String get() = format("yyyyMMdd")
val LocalDateTime.date_mmm_dd: String get() = format("MMM dd")
val LocalDateTime.date_dd_MMM_yyyy: String get() = format("dd MMM yyyy")
val LocalDateTime.dateTime_mmm_dd_hh_mm_a: String get() = format("MMM dd hh:mm a")
val LocalDateTime.dateTime_mmm_dd_HH_mm: String get() = format("MMM dd HH:mm")
val LocalDateTime.dateTime_mmm_dd_split_hh_mm_a: String get() = format("MMM dd\nhh:mm a")
val LocalDateTime.dateTime_mmm_dd_split_HH_mm: String get() = format("MMM dd\nHH:mm")


val LocalDateTime.weekName: String get() = format("EEEE")
val LocalDateTime.nextDay: LocalDateTime get() = plusDays(1)
val LocalDateTime.previousDay: LocalDateTime get() = plusDays(-1)
val LocalDateTime.previousMonth: LocalDateTime get() = plusMonths(-1)
val LocalDateTime.nextMonth: LocalDateTime get() = plusMonths(1)
val LocalDateTime.end_of_day: LocalDateTime get() = withHour(23).withMinute(59).withSecond(59).withNano(59)

val LocalDateTime.nextWholeHour: LocalDateTime get() = plusHours(1).withMinute(0).withSecond(0).withNano(0)

val LocalDateTime.decimalHours: Double get() = hour.toDouble() + minute.toDouble() / 60 + second.toDouble() / 3600
val LocalDateTime.decimalYears: Double get() = year.toDouble() + (month.value.toDouble() / 12) + (dayOfMonth.toDouble() / 365.25) - 1.086
val LocalDateTime.decimalTime: Double get() = hour.toDouble() + (minute.toDouble() / 60) + (second.toDouble() / 3600)

val LocalDateTime.millis: Long get() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
val LocalDateTime.midnightMillis: Long get() = midnightInstance.millis
val LocalDateTime.noonMillis: Long get() = noonInstance.millis
val LocalDateTime.midnightInstance: LocalDateTime get() = withHour(0).withMinute(0).withSecond(1)
val LocalDateTime.noonInstance: LocalDateTime get() = withHour(12).withMinute(0).withSecond(1)

/* ******************************************************
 *    Serializer for LocalDateTime
 * ******************************************************/

object LocalDateTimeSerializer : KSerializer<LocalDateTimeJson> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTimeJson) {
        // Serialize LocalDateTime to an ISO 8601 string
        encoder.encodeString(value.value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }

    override fun deserialize(decoder: Decoder): LocalDateTimeJson {
        // Deserialize the ISO 8601 string back to LocalDateTime
        return LocalDateTimeJson(LocalDateTime.parse(decoder.decodeString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }
}
@JvmInline
@Serializable(with = LocalDateTimeSerializer::class) // <-- Applied here!
value class LocalDateTimeJson(val value: LocalDateTime)


inline val LocalDateTime.serialized: LocalDateTimeJson
    get() = LocalDateTimeJson(this)