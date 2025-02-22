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
    val textNormal: TextStyle = TextStyle.Default,
    val textTiny: TextStyle = TextStyle.Default,
    val textXXSmall: TextStyle = TextStyle.Default,
    val textXSmall: TextStyle = TextStyle.Default,
    val textSmall: TextStyle = TextStyle.Default,
    val textSmallSemiBold: TextStyle = TextStyle.Default,
    val textSmallBold: TextStyle = TextStyle.Default,
    val textMedium: TextStyle = TextStyle.Default,
    val textMediumSemiBold: TextStyle = TextStyle.Default,
    val textMediumLarge: TextStyle = TextStyle.Default,
    val textMediumLargeSemiBold: TextStyle = TextStyle.Default,
    val textLarge: TextStyle = TextStyle.Default,
    val textLargeSemiBold: TextStyle = TextStyle.Default,
    val textLargeBold: TextStyle = TextStyle.Default,
    val textXLarge: TextStyle = TextStyle.Default,
    val textXLargeSemiBold: TextStyle = TextStyle.Default,
    val textXLargeThin: TextStyle = TextStyle.Default,
    val textXXLarge: TextStyle = TextStyle.Default,
    val textXXLargeSemiBold: TextStyle = TextStyle.Default,
    val textXXLargeThin: TextStyle = TextStyle.Default,
    val textXXXLarge: TextStyle = TextStyle.Default,
    val textXXXLargeSemiBold: TextStyle = TextStyle.Default,
    val textXXXLargeThin: TextStyle = TextStyle.Default,
    val textGiant: TextStyle = TextStyle.Default,
    val textGiantThin: TextStyle = TextStyle.Default,
)

@Composable
fun createBaseTypography(): BaseTextStyles {
    val textNormalStyle = TextStyle.Default.copy(
        fontFamily = GoogleFonts.notoFamily,
        color = LocalColors.current.onMainSurface, platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
    val baseSize = LocalSizes.current
    return BaseTextStyles(
        textNormal = textNormalStyle,
        textTiny = textNormalStyle.copy(fontSize = baseSize.fontSizeTiny),
        textXXSmall = textNormalStyle.copy(fontSize = baseSize.fontSizeXXSmall),
        textXSmall = textNormalStyle.copy(fontSize = baseSize.fontSizeXSmall),
        textSmall = textNormalStyle.copy(fontSize = baseSize.fontSizeSmall),
        textSmallSemiBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeSmall,
            fontWeight = FontWeight.SemiBold
        ),
        textSmallBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeSmall,
            fontWeight = FontWeight.Bold
        ),
        textMedium = textNormalStyle.copy(fontSize = baseSize.fontSizeMedium),
        textMediumSemiBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeMedium,
            fontWeight = FontWeight.SemiBold
        ),
        textMediumLarge = textNormalStyle.copy(fontSize = baseSize.fontSizeMediumLarge),
        textMediumLargeSemiBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeMediumLarge,
            fontWeight = FontWeight.SemiBold
        ),
        textLarge = textNormalStyle.copy(fontSize = baseSize.fontSizeLarge),
        textLargeSemiBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeLarge,
            fontWeight = FontWeight.SemiBold
        ),
        textLargeBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeLarge,
            fontWeight = FontWeight.Bold
        ),
        textXLarge = textNormalStyle.copy(fontSize = baseSize.fontSizeXLarge),
        textXLargeSemiBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXLarge,
            fontWeight = FontWeight.SemiBold
        ),
        textXLargeThin = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXLarge,
            fontWeight = FontWeight.Light
        ),
        textXXLarge = textNormalStyle.copy(fontSize = baseSize.fontSizeXXLarge),
        textXXLargeSemiBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXXLarge,
            fontWeight = FontWeight.SemiBold
        ),
        textXXLargeThin = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXXLarge,
            fontWeight = FontWeight.Light
        ),
        textXXXLarge = textNormalStyle.copy(fontSize = baseSize.fontSizeXXXLarge),
        textXXXLargeSemiBold = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXXXLarge,
            fontWeight = FontWeight.SemiBold
        ),
        textXXXLargeThin = textNormalStyle.copy(
            fontSize = baseSize.fontSizeXXXLarge,
            fontWeight = FontWeight.Light
        ),
        textGiant = textNormalStyle.copy(fontSize = baseSize.fontSizeHuge),
        textGiantThin = textNormalStyle.copy(
            fontSize = baseSize.fontSizeHuge,
            fontWeight = FontWeight.Light
        ),
    )
}


val LocalTypography = staticCompositionLocalOf { BaseTextStyles() }

