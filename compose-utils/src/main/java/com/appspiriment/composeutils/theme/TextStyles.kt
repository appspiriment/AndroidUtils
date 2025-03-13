package com.appspiriment.composeutils.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
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
        Font(
            googleFont = GoogleFont("Noto Sans Malayalam"),
            fontProvider = provider,
        )
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
    val textGiant: TextStyle = TextStyle.Default,

    val textSmallSemiBold: TextStyle = TextStyle.Default,
    val textSmallBold: TextStyle = TextStyle.Default,
    val textMediumSemiBold: TextStyle = TextStyle.Default,
    val textMediumBold: TextStyle = TextStyle.Default,
    val textLargeSemiBold: TextStyle = TextStyle.Default,
    val textLargeBold: TextStyle = TextStyle.Default,

)

@Composable
fun createBaseTypography(baseSize: Sizes): BaseTextStyles {
    val textNormalStyle = TextStyle.Default.copy(
        fontFamily = GoogleFonts.notoFamily,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
    return BaseTextStyles(

        textTiny = textNormalStyle.copy(
            fontSize = baseSize.fontSizeTiny
        ),
        textXXXSmall = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXXXSmall
        ),
        textXXSmall = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXXSmall
        ),
        textXSmall = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXSmall
        ),
        textSmall = textNormalStyle.copy(
            fontSize = baseSize.fontSizeSmall
        ),
        textMedium = textNormalStyle.copy(
            fontSize = baseSize.fontSizeMedium
        ),
        textMediumLarge = textNormalStyle.copy(
            fontSize = baseSize.fontSizeMediumLarge
        ),
        textLarge = textNormalStyle.copy(
            fontSize = baseSize.fontSizeLarge
        ),
        textXLarge = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXLarge
        ),
        textXXLarge = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXXLarge
        ),
        textXXXLarge = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXXXLarge
        ),
        textBig = textNormalStyle.copy(
            fontSize = baseSize.fontSizeBig
        ),
        textGiant = textNormalStyle.copy(
            fontSize = baseSize.fontSizeGiant
        ),
        //Combinations
        textSmallSemiBold = textNormalStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = baseSize.fontSizeSmall
        ),
        textSmallBold = textNormalStyle.copy(
            fontWeight = FontWeight.Bold,
            fontSize = baseSize.fontSizeSmall
        ),
        textMediumSemiBold = textNormalStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = baseSize.fontSizeMedium
        ),
        textMediumBold = textNormalStyle.copy(
            fontWeight = FontWeight.Bold,
            fontSize = baseSize.fontSizeMedium
        ),
        textLargeSemiBold = textNormalStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = baseSize.fontSizeLarge
        ),
        textLargeBold = textNormalStyle.copy(
            fontWeight = FontWeight.Bold,
            fontSize = baseSize.fontSizeLarge
        ),
    )
}


val LocalTypography  by lazy { staticCompositionLocalOf { BaseTextStyles() } }

