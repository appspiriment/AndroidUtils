package com.appspiriment.utils.time

import android.graphics.Color
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

fun LocalDate.format(pattern: String): String =
    DateTimeFormatterBuilder().appendPattern(pattern).toFormatter(Locale.ENGLISH).format(this)

fun millisToLocalDate(millis: Long): LocalDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).toLocalDate()

fun LocalDate.next(week: DayOfWeek): LocalDate = with(TemporalAdjusters.next(week))

fun String.toLocalDateOrNull(pattern: String): LocalDate? {
    return try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH))
    } catch (e: Exception) {
        null
    }
}

fun String.toLocalDateOrNow(pattern: String): LocalDate {
    return toLocalDateOrNull(pattern) ?: LocalDate.now()
}

val LocalDate.date_yyyymmdd: String get() = format("yyyyMMdd")
val LocalDate.date_mmm_dd: String get() = format("MMM dd")
val LocalDate.date_dd_MMM_yyyy: String get() = format("dd MMM yyyy")

val LocalDate.weekName: String get() = format("EEEE")
val LocalDate.nextDay: LocalDate get() = plusDays(1)
val LocalDate.previousDay: LocalDate get() = plusDays(-1)
val LocalDate.previousMonth: LocalDate get() = plusMonths(-1)
val LocalDate.nextMonth: LocalDate get() = plusMonths(1)


val LocalDate.decimalYears: Double get() = year.toDouble() + (month.value.toDouble() / 12) + (dayOfMonth.toDouble() / 365.25) - 1.086

val LocalDate.millis: Long get() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
val LocalDate.midnightMillis: Long get() = millis

/* ******************************************************
 *    Serializer for LocalDate
 * ******************************************************/

object LocalDateSerializer : KSerializer<LocalDateJson> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateJson) {
        // Serialize LocalDate to an ISO 8601 string
        encoder.encodeString(value.value.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }

    override fun deserialize(decoder: Decoder): LocalDateJson {
        // Deserialize the ISO 8601 string back to LocalDate
        return LocalDateJson(LocalDate.parse(decoder.decodeString(), DateTimeFormatter.ISO_LOCAL_DATE))
    }
}
@JvmInline
@Serializable(with = LocalDateSerializer::class) // <-- Applied here!
value class LocalDateJson(val value: LocalDate)

inline val LocalDate.serialized: LocalDateJson
    get() = LocalDateJson(this)
