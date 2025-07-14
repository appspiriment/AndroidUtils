package com.appspiriment.composeutils.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont

import com.appspiriment.composeutils.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

object GoogleFonts {
    val robotoFamily: FontFamily = FontFamily(
        Font(
            googleFont = GoogleFont("Roboto"),
            fontProvider = provider,
        )
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
    val textTiny: TextStyle = TextStyle.Default,
    val textXXXSmall: TextStyle = TextStyle.Default,
    val textXXSmall: TextStyle = TextStyle.Default,
    val textXSmall: TextStyle = TextStyle.Default,
    val textSmall: TextStyle = TextStyle.Default,
    val textMedium: TextStyle = TextStyle.Default,
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

@Composable
internal fun createBaseTypography(baseSize: Sizes, fontFamily: FontFamily?): BaseTextStyles {
    val baseTextStyle = TextStyle.Default.copy(
        fontFamily = fontFamily,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
    return BaseTextStyles(
        baseTextStyle = baseTextStyle,
        textTiny = baseTextStyle.copy(
            fontSize = baseSize.fontSizeTiny
        ),
        textXXXSmall = baseTextStyle.copy(
            fontSize = baseSize.fontSizeXXXSmall
        ),
        textXXSmall = baseTextStyle.copy(
            fontSize = baseSize.fontSizeXXSmall
        ),
        textXSmall = baseTextStyle.copy(
            fontSize = baseSize.fontSizeXSmall
        ),
        textSmall = baseTextStyle.copy(
            fontSize = baseSize.fontSizeSmall
        ),
        textMedium = baseTextStyle.copy(
            fontSize = baseSize.fontSizeMedium
        ),
        textMediumLarge = baseTextStyle.copy(
            fontSize = baseSize.fontSizeMediumLarge
        ),
        textLarge = baseTextStyle.copy(
            fontSize = baseSize.fontSizeLarge
        ),
        textXLarge = baseTextStyle.copy(
            fontSize = baseSize.fontSizeXLarge
        ),
        textXXLarge = baseTextStyle.copy(
            fontSize = baseSize.fontSizeXXLarge
        ),
        textXXXLarge = baseTextStyle.copy(
            fontSize = baseSize.fontSizeXXXLarge
        ),
        textBig = baseTextStyle.copy(
            fontSize = baseSize.fontSizeBig
        ),
        textXBig = baseTextStyle.copy(
            fontSize = baseSize.fontSizeXBig
        ),
        textHuge = baseTextStyle.copy(
            fontSize = baseSize.fontSizeHuge
        ),
        textGiant = baseTextStyle.copy(
            fontSize = baseSize.fontSizeGiant
        ),

    )
}

val TextStyle.thin get() = this.copy(fontWeight = FontWeight.Thin)
val TextStyle.extraLight get() = this.copy(fontWeight = FontWeight.ExtraLight)
val TextStyle.light get() = this.copy(fontWeight = FontWeight.Light)
val TextStyle.normal get() = this.copy(fontWeight = FontWeight.Normal)
val TextStyle.medium get() = this.copy(fontWeight = FontWeight.Medium)
val TextStyle.semiBold get() = this.copy(fontWeight = FontWeight.SemiBold)
val TextStyle.bold get() = this.copy(fontWeight = FontWeight.Bold)
val TextStyle.extraBold get() = this.copy(fontWeight = FontWeight.ExtraBold)
val TextStyle.black get() = this.copy(fontWeight = FontWeight.Black)

val LocalTypography  by lazy { staticCompositionLocalOf { BaseTextStyles() } }

