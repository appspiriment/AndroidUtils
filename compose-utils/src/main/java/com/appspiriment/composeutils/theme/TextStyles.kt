package com.appspiriment.composeutils.theme

import android.content.Context
import androidx.annotation.DimenRes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.appspiriment.composeutils.R


object GoogleFonts {
    val robotoFamily: FontFamily = FontFamily(
        Font(R.font.font_roboto_thin, FontWeight.Thin),
        Font(R.font.font_roboto_extra_light, FontWeight.ExtraLight),
        Font(R.font.font_roboto_light, FontWeight.Light),
        Font(R.font.font_roboto_medium, FontWeight.Medium),
        Font(R.font.font_roboto_regular, FontWeight.Normal),
        Font(R.font.font_roboto_semi_bold, FontWeight.SemiBold),
        Font(R.font.font_roboto_bold, FontWeight.Bold),
        Font(R.font.font_roboto_extra_bold, FontWeight.ExtraBold),
        Font(R.font.font_roboto_black, FontWeight.Black),
    )
    val notoFamily: FontFamily = FontFamily(
        Font(R.font.noto_thin, FontWeight.Thin),
        Font(R.font.noto_extra_light, FontWeight.ExtraLight),
        Font(R.font.noto_light, FontWeight.Light),
        Font(R.font.noto_medium, FontWeight.Medium),
        Font(R.font.noto_semi_bold, FontWeight.SemiBold),
        Font(R.font.noto_bold, FontWeight.Bold),
        Font(R.font.noto_extra_bold, FontWeight.ExtraBold),
        Font(R.font.noto_black, FontWeight.Black),
    )
}

/**
 * Describes which font family the theme should use.
 *
 * - [Roboto]  — bundled Roboto (9 weights). Default.
 * - [Noto]    — bundled Noto Sans (8 weights). [fontPadding] applies a vertical offset in
 *               [AppspirimentText] to compensate for Noto's taller metrics.
 * - [System]  — lets the OS/Material3 choose the default font.
 * - [Custom]  — any [FontFamily] you supply.
 * - [GmsFont] — resolved at runtime via Google Play Services fonts. Silently falls back to
 *               [System] on devices without GMS.
 */
sealed class AppFontFamily {
    data object Roboto : AppFontFamily()
    data class Noto(val fontPadding: Dp = 4.dp) : AppFontFamily()
    data object System : AppFontFamily()
    data class Custom(val fontFamily: FontFamily) : AppFontFamily()
    data class GmsFont(val create: (GoogleFont.Provider) -> FontFamily) : AppFontFamily()
}

object TextStyles {
    val NoPaddingStyle = TextStyle(
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    )
}

val TextStyle.noPadding: TextStyle
    get() = this.copy(
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
val TextStyle.roboto: TextStyle
    get() = this.copy(
        fontFamily = GoogleFonts.robotoFamily
    )


data class BaseTextStyles(
    val baseTextStyle: TextStyle = TextStyle.Default,
    val textMinimum: TextStyle = TextStyle.Default,
    val textTiny: TextStyle = TextStyle.Default,
    val textXXXSmall: TextStyle = TextStyle.Default,
    val textXXSmall: TextStyle = TextStyle.Default,
    val textXSmall: TextStyle = TextStyle.Default,
    val textXSmallMedium: TextStyle = TextStyle.Default,
    val textSmall: TextStyle = TextStyle.Default,
    val textSmallMedium: TextStyle = TextStyle.Default,
    val textMedium: TextStyle = TextStyle.Default,
    val textMediumMid: TextStyle = TextStyle.Default,
    val textMediumLarge: TextStyle = TextStyle.Default,
    val textLarge: TextStyle = TextStyle.Default,
    val textXLarge: TextStyle = TextStyle.Default,
    val textXXLarge: TextStyle = TextStyle.Default,
    val textXXXLarge: TextStyle = TextStyle.Default,
    val textBig: TextStyle = TextStyle.Default,
    val textXBig: TextStyle = TextStyle.Default,
    val textHuge: TextStyle = TextStyle.Default,
    val textGiant: TextStyle = TextStyle.Default,
)

internal fun createBaseTypography(context: Context, fontFamily: FontFamily?): BaseTextStyles {
    val res = context.resources
    val scaledDensity = res.displayMetrics.scaledDensity
    fun @receiver:DimenRes Int.toSp() = (res.getDimension(this) / scaledDensity).sp

    val baseTextStyle = TextStyle.Default.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
    return BaseTextStyles(
        baseTextStyle = baseTextStyle,
        textMinimum = baseTextStyle.copy(
            fontSize = R.dimen.font_size_minimum.toSp()
        ),
        textTiny = baseTextStyle.copy(
            fontSize = R.dimen.font_size_tiny.toSp()
        ),
        textXXXSmall = baseTextStyle.copy(
            fontSize = R.dimen.font_size_xxxsmall.toSp()
        ),
        textXXSmall = baseTextStyle.copy(
            fontSize = R.dimen.font_size_xxsmall.toSp()
        ),
        textXSmall = baseTextStyle.copy(
            fontSize = R.dimen.font_size_xsmall.toSp()
        ),
        textXSmallMedium = baseTextStyle.copy(
            fontSize = R.dimen.font_size_xsmall_medium.toSp()
        ),
        textSmall = baseTextStyle.copy(
            fontSize = R.dimen.font_size_small.toSp()
        ),
        textSmallMedium = baseTextStyle.copy(
            fontSize = R.dimen.font_size_small_medium.toSp()
        ),
        textMedium = baseTextStyle.copy(
            fontSize = R.dimen.font_size_medium.toSp()
        ),
        textMediumMid = baseTextStyle.copy(
            fontSize = R.dimen.font_size_medium_mid.toSp()
        ),
        textMediumLarge = baseTextStyle.copy(
            fontSize = R.dimen.font_size_medium_large.toSp()
        ),
        textLarge = baseTextStyle.copy(
            fontSize = R.dimen.font_size_large.toSp()
        ),
        textXLarge = baseTextStyle.copy(
            fontSize = R.dimen.font_size_xlarge.toSp()
        ),
        textXXLarge = baseTextStyle.copy(
            fontSize = R.dimen.font_size_xxlarge.toSp()
        ),
        textXXXLarge = baseTextStyle.copy(
            fontSize = R.dimen.font_size_xxxlarge.toSp()
        ),
        textBig = baseTextStyle.copy(
            fontSize = R.dimen.font_size_big.toSp()
        ),
        textXBig = baseTextStyle.copy(
            fontSize = R.dimen.font_size_xbig.toSp()
        ),
        textHuge = baseTextStyle.copy(
            fontSize = R.dimen.font_size_huge.toSp()
        ),
        textGiant = baseTextStyle.copy(
            fontSize = R.dimen.font_size_giant.toSp()
        ),
    )
}

// ── Weight extensions ─────────────────────────────────────────────────────────

val TextStyle.thin get() = this.copy(fontWeight = FontWeight.Thin)
val TextStyle.extraLight get() = this.copy(fontWeight = FontWeight.ExtraLight)
val TextStyle.light get() = this.copy(fontWeight = FontWeight.Light)
val TextStyle.normal get() = this.copy(fontWeight = FontWeight.Normal)
val TextStyle.medium get() = this.copy(fontWeight = FontWeight.Medium)
val TextStyle.semiBold get() = this.copy(fontWeight = FontWeight.SemiBold)
val TextStyle.bold get() = this.copy(fontWeight = FontWeight.Bold)
val TextStyle.extraBold get() = this.copy(fontWeight = FontWeight.ExtraBold)
val TextStyle.black get() = this.copy(fontWeight = FontWeight.Black)

// ── Style extensions ──────────────────────────────────────────────────────────

val TextStyle.italic get() = this.copy(fontStyle = FontStyle.Italic)
val TextStyle.thinItalic get() = this.copy(fontWeight = FontWeight.Thin, fontStyle = FontStyle.Italic)
val TextStyle.extraLightItalic get() = this.copy(fontWeight = FontWeight.ExtraLight, fontStyle = FontStyle.Italic)
val TextStyle.lightItalic get() = this.copy(fontWeight = FontWeight.Light, fontStyle = FontStyle.Italic)
val TextStyle.mediumItalic get() = this.copy(fontWeight = FontWeight.Medium, fontStyle = FontStyle.Italic)
val TextStyle.semiBoldItalic get() = this.copy(fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic)
val TextStyle.boldItalic get() = this.copy(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
val TextStyle.extraBoldItalic get() = this.copy(fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle.Italic)
val TextStyle.blackItalic get() = this.copy(fontWeight = FontWeight.Black, fontStyle = FontStyle.Italic)

// ── Material3-aligned semantic aliases ───────────────────────────────────────
// Maps to the nearest size tier. Use these when building components that should
// align with M3 guidelines, and use the numeric tiers for app-specific sizing.

/** M3 labelSmall — 11sp */
val BaseTextStyles.labelSmall: TextStyle get() = textXSmallMedium
/** M3 labelMedium — 12sp medium */
val BaseTextStyles.labelMedium: TextStyle get() = textSmall.medium
/** M3 labelLarge — 14sp medium */
val BaseTextStyles.labelLarge: TextStyle get() = textMedium.medium

/** M3 bodySmall — 12sp */
val BaseTextStyles.bodySmall: TextStyle get() = textSmall
/** M3 bodyMedium — 14sp */
val BaseTextStyles.bodyMedium: TextStyle get() = textMedium
/** M3 bodyLarge — 16sp */
val BaseTextStyles.bodyLarge: TextStyle get() = textMediumLarge

/** M3 titleSmall — 14sp semiBold */
val BaseTextStyles.titleSmall: TextStyle get() = textMedium.semiBold
/** M3 titleMedium — 16sp semiBold */
val BaseTextStyles.titleMedium: TextStyle get() = textMediumLarge.semiBold
/** M3 titleLarge — 20sp (nearest tier to M3's 22sp) */
val BaseTextStyles.titleLarge: TextStyle get() = textXLarge

/** M3 headlineSmall — 24sp */
val BaseTextStyles.headlineSmall: TextStyle get() = textXXLarge
/** M3 headlineMedium — 28sp */
val BaseTextStyles.headlineMedium: TextStyle get() = textXXXLarge
/** M3 headlineLarge — 32sp */
val BaseTextStyles.headlineLarge: TextStyle get() = textBig

/** M3 displaySmall — 36sp */
val BaseTextStyles.displaySmall: TextStyle get() = textXBig
/** M3 displayMedium — 40sp */
val BaseTextStyles.displayMedium: TextStyle get() = textHuge
/** M3 displayLarge — 48sp (nearest tier to M3's 57sp) */
val BaseTextStyles.displayLarge: TextStyle get() = textGiant

val LocalTypography  by lazy { staticCompositionLocalOf { BaseTextStyles() } }
