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
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.TemporalAdjusters
import java.util.Locale


fun ZonedDateTime.format(pattern: String): String =
    DateTimeFormatterBuilder().appendPattern(pattern).toFormatter(Locale.ENGLISH).format(this)

fun millisToZonedDateTime(millis: Long): ZonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())

fun ZonedDateTime.next(week: DayOfWeek): ZonedDateTime = with(TemporalAdjusters.next(week))

fun String.toZonedDateTimeOrNull(pattern: String): ZonedDateTime? {
    return try {
        ZonedDateTime.parse(this, DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH))
    } catch (e: Exception) {
        null
    }
}


fun String.toZonedDateTimeOrNow(pattern: String): ZonedDateTime {
    return toZonedDateTimeOrNull(pattern) ?: ZonedDateTime.now()
}

val ZonedDateTime.time_hhmm_a: String get() = format("hh:mm a")
val ZonedDateTime.time_hhmmss_a: String get() = format("hh:mm:ss a")
val ZonedDateTime.time_HHmm: String get() = format("HH:mm")
val ZonedDateTime.date_yyyymmdd: String get() = format("yyyyMMdd")
val ZonedDateTime.date_mmm_dd: String get() = format("MMM dd")
val ZonedDateTime.date_dd_MMM_yyyy: String get() = format("dd MMM yyyy")
val ZonedDateTime.dateTime_mmm_dd_hh_mm_a: String get() = format("MMM dd hh:mm a")
val ZonedDateTime.dateTime_mmm_dd_HH_mm: String get() = format("MMM dd HH:mm")
val ZonedDateTime.dateTime_mmm_dd_split_hh_mm_a: String get() = format("MMM dd\nhh:mm a")
val ZonedDateTime.dateTime_mmm_dd_split_HH_mm: String get() = format("MMM dd\nHH:mm")


val ZonedDateTime.weekName: String get() = format("EEEE")
val ZonedDateTime.nextDay: ZonedDateTime get() = plusDays(1)
val ZonedDateTime.previousDay: ZonedDateTime get() = plusDays(-1)
val ZonedDateTime.previousMonth: ZonedDateTime get() = plusMonths(-1)
val ZonedDateTime.nextMonth: ZonedDateTime get() = plusMonths(1)
val ZonedDateTime.end_of_day: ZonedDateTime get() = withHour(23).withMinute(59).withSecond(59).withNano(59)

val ZonedDateTime.nextWholeHour: ZonedDateTime get() = plusHours(1).withMinute(0).withSecond(0).withNano(0)

val ZonedDateTime.decimalHours: Double get() = hour.toDouble() + minute.toDouble() / 60 + second.toDouble() / 3600
val ZonedDateTime.decimalYears: Double get() = year.toDouble() + (month.value.toDouble() / 12) + (dayOfMonth.toDouble() / 365.25) - 1.086
val ZonedDateTime.decimalTime: Double get() = hour.toDouble() + (minute.toDouble() / 60) + (second.toDouble() / 3600)

val ZonedDateTime.millis: Long get() = toEpochSecond() * 1000
val ZonedDateTime.midnightMillis: Long get() = midnightInstance.millis
val ZonedDateTime.noonMillis: Long get() = noonInstance.millis
val ZonedDateTime.midnightInstance: ZonedDateTime get() = withHour(0).withMinute(0).withSecond(1)
val ZonedDateTime.noonInstance: ZonedDateTime get() = withHour(12).withMinute(0).withSecond(1)



/* ******************************************************
 *    Serializer for ZonedDateTime
 * ******************************************************/

object ZonedDateTimeSerializer : KSerializer<ZonedDateTimeJson> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ZonedDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ZonedDateTimeJson) {
        // Serialize ZonedDateTime to an ISO 8601 string
        encoder.encodeString(value.value.format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
    }

    override fun deserialize(decoder: Decoder): ZonedDateTimeJson {
        // Deserialize the ISO 8601 string back to ZonedDateTime
        return ZonedDateTimeJson(ZonedDateTime.parse(decoder.decodeString(), DateTimeFormatter.ISO_ZONED_DATE_TIME))
    }
}
@JvmInline
@Serializable(with = ZonedDateTimeSerializer::class) // <-- Applied here!
value class ZonedDateTimeJson(val value: ZonedDateTime)


inline val ZonedDateTime.serialized: ZonedDateTimeJson
    get() = ZonedDateTimeJson(this)