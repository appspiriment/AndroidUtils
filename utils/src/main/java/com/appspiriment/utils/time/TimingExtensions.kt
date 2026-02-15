package com.appspiriment.utils.time

import android.R.attr.timeZone
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToLong

/**
 * Conversion factor: 1 Hour = 2.5 Nazhika
 */
private const val HOURS_TO_NAZHIKA = 2.5

/**
 * Converts decimal degrees to a Triple of (Degrees, Minutes, Seconds).
 */
val Double.dms: Triple<Int, Int, Int>
    get() {
        var value = this + (0.5 / 3600.0 / 10000.0) // round to 1/1000 of a second
        val deg = value.toInt()
        value = (value - deg) * 60
        val min = value.toInt()
        value = (value - min) * 60
        val sec = value.toInt()
        return Triple(deg, min, sec)
    }

fun Double.fromHoursToMillis(): Long = (this * 3600000).toLong()
fun Double.fromHoursToSeconds(): Long = (this * 3600).roundToLong()
fun Double.toDMSString(): String = dms.run { "$first° $second' $third\"" }

/**
 * Converts decimal hours to a Triple of (Hours, Minutes, Seconds).
 */
fun Double.fromHoursToHMS(): Triple<Int, Int, Int> {
    val totalSeconds = (this * 3600).toInt()
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return Triple(hours, minutes, seconds)
}

/**
 * Converts decimal hours to Nazhika and Vinazhika.
 * 1 Hour = 2.5 Nazhika.
 */
fun Double.fromHoursToNazhika(): Pair<Int, Int> {
    val totalNazhika = this * HOURS_TO_NAZHIKA
    val nazhika = totalNazhika.toInt()
    val vinazhika = ((totalNazhika - nazhika) * 60).toInt()
    return Pair(nazhika, vinazhika)
}

fun Double.hourstoNazhikaVinazhikaString(): String = fromHoursToNazhika().run { "$first നാ $second വി" }

/**
 * Converts decimal Nazhika to Nazhika and Vinazhika.
 */
fun Double.nazhikaToNazhikaVinazhika(): Pair<Int, Int> {
    val nazhika = this.toInt()
    val vinazhika = ((this - nazhika) * 60).toInt()
    return Pair(nazhika, vinazhika)
}

fun Double.nazhikatoNazhikaVinazhikaString(): String = nazhikaToNazhikaVinazhika().let { "${it.first} നാ ${it.second} വി" }

fun Long.millisToDecimalHour(): Double = this.toDouble() / 3600000.0
fun Long.millisToDays(): Double = this.toDouble() / 86400000.0

/**
 * Formats epoch milliseconds to "hh:mm a" using the system default timezone.
 */
fun (Long?).millisToHmaTime(timeZone: ZoneId = ZoneId.systemDefault()): String? {
    return this?.let {
        try {
            DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
                .withZone(timeZone)
                .format(Instant.ofEpochMilli(it))
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * Formats epoch milliseconds to "MMM dd hh:mm a" using the system default timezone.
 */
fun (Long?).millisToMMddHmaTime(timeZone: ZoneId = ZoneId.systemDefault()): String? {
    return this?.let {
        try {
            DateTimeFormatter.ofPattern("MMM dd hh:mm a", Locale.ENGLISH)
                .withZone(timeZone)
                .format(Instant.ofEpochMilli(it))
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * Formats epoch milliseconds with a custom pattern using the system default timezone.
 */
fun (Long?).millisToDateTime(format: String, timeZone: ZoneId = ZoneId.systemDefault()): String? {
    return this?.let {
        try {
            DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
                .withZone(timeZone)
                .format(Instant.ofEpochMilli(it))
        } catch (e: Exception) {
            null
        }
    }
}
