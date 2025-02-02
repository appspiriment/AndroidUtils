package com.appspiriment.composeutils.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.textSizeResource

data class Sizes(
    val appBarSize: Dp = Dp.Unspecified,

    val iconSmall: Dp = Dp.Unspecified,
    val iconMedium: Dp = Dp.Unspecified,
    val iconStandard: Dp = Dp.Unspecified,
    val iconMediumLarge: Dp = Dp.Unspecified,
    val iconLarge: Dp = Dp.Unspecified,
    val iconXLarge: Dp = Dp.Unspecified,
    val iconXXLarge: Dp = Dp.Unspecified,

    val paddingGiant: Dp = Dp.Unspecified,
    val paddingXXXXLarge: Dp = Dp.Unspecified,
    val paddingXXXLarge: Dp = Dp.Unspecified,
    val paddingXXLarge: Dp = Dp.Unspecified,
    val paddingXLarge: Dp = Dp.Unspecified,
    val paddingLarge: Dp = Dp.Unspecified,
    val paddingMedium: Dp = Dp.Unspecified,
    val paddingSmallMedium: Dp = Dp.Unspecified,
    val paddingSmall: Dp = Dp.Unspecified,
    val paddingXSmall: Dp = Dp.Unspecified,
    val paddingXXSmall: Dp = Dp.Unspecified,
    val noPadding: Dp = Dp.Unspecified,

    val cornerRadiusSmall: Dp = Dp.Unspecified,
    val cornerRadiusMedium: Dp = Dp.Unspecified,
    val cornerRadiusNormal: Dp = Dp.Unspecified,
    val cornerRadiusMediumLarge: Dp = Dp.Unspecified,
    val cornerRadiusLarge: Dp = Dp.Unspecified,
    val cornerRadiusXLarge: Dp = Dp.Unspecified,
    val cornerRadiusXXLarge: Dp = Dp.Unspecified,

    val fontSizeTiny: TextUnit = TextUnit.Unspecified,
    val fontSizeXXXSmall: TextUnit = TextUnit.Unspecified,
    val fontSizeXXSmall: TextUnit = TextUnit.Unspecified,
    val fontSizeXSmall: TextUnit = TextUnit.Unspecified,
    val fontSizeSmall: TextUnit = TextUnit.Unspecified,
    val fontSizeStandard: TextUnit = TextUnit.Unspecified,
    val fontSizeMedium: TextUnit = TextUnit.Unspecified,
    val fontSizeMediumLarge: TextUnit = TextUnit.Unspecified,
    val fontSizeLarge: TextUnit = TextUnit.Unspecified,
    val fontSizeXLarge: TextUnit = TextUnit.Unspecified,
    val fontSizeXXLarge: TextUnit = TextUnit.Unspecified,
    val fontSizeXXXLarge: TextUnit = TextUnit.Unspecified,
    val fontSizeHuge: TextUnit = TextUnit.Unspecified,
    val fontSizeGiant: TextUnit = TextUnit.Unspecified,
)

// Composable function to create an object of Sizes
@Composable
fun createSizes(): Sizes {
    return Sizes(
        appBarSize = dimensionResource(id = R.dimen.app_bar_height),

        iconSmall = dimensionResource(id = R.dimen.icon_small),
        iconMedium = dimensionResource(id = R.dimen.icon_medium),
        iconStandard = dimensionResource(id = R.dimen.icon_standard),
        iconMediumLarge = dimensionResource(id = R.dimen.icon_medium_large),
        iconLarge = dimensionResource(id = R.dimen.icon_large),
        iconXLarge = dimensionResource(id = R.dimen.icon_xlarge),
        iconXXLarge = dimensionResource(id = R.dimen.icon_xxlarge),

        paddingGiant = dimensionResource(id = R.dimen.padding_giant),
        paddingXXXXLarge = dimensionResource(id = R.dimen.padding_xxxxlarge),
        paddingXXXLarge = dimensionResource(id = R.dimen.padding_xxxlarge),
        paddingXXLarge = dimensionResource(id = R.dimen.padding_xxlarge),
        paddingXLarge = dimensionResource(id = R.dimen.padding_xlarge),
        paddingLarge = dimensionResource(id = R.dimen.padding_large),
        paddingMedium = dimensionResource(id = R.dimen.padding_medium),
        paddingSmallMedium = dimensionResource(id = R.dimen.padding_smallmedium),
        paddingSmall = dimensionResource(id = R.dimen.padding_small),
        paddingXSmall = dimensionResource(id = R.dimen.padding_xsmall),
        paddingXXSmall = dimensionResource(id = R.dimen.padding_xxsmall),
        noPadding = dimensionResource(id = R.dimen.no_padding),

        cornerRadiusSmall = dimensionResource(id = R.dimen.corner_radius_small),
        cornerRadiusMedium = dimensionResource(id = R.dimen.corner_radius_medium),
        cornerRadiusNormal = dimensionResource(id = R.dimen.corner_radius_normal),
        cornerRadiusMediumLarge = dimensionResource(id = R.dimen.corner_radius_medium_large),
        cornerRadiusLarge = dimensionResource(id = R.dimen.corner_radius_large),
        cornerRadiusXLarge = dimensionResource(id = R.dimen.corner_radius_xlarge),
        cornerRadiusXXLarge = dimensionResource(id = R.dimen.corner_radius_xxlarge),

        fontSizeTiny = textSizeResource(id = R.dimen.font_size_tiny),
        fontSizeXXXSmall = textSizeResource(id = R.dimen.font_size_xxxsmall),
        fontSizeXXSmall = textSizeResource(id = R.dimen.font_size_xxsmall),
        fontSizeXSmall = textSizeResource(id = R.dimen.font_size_xsmall),
        fontSizeSmall = textSizeResource(id = R.dimen.font_size_small),
        fontSizeStandard = textSizeResource(id = R.dimen.font_size_standard),
        fontSizeMedium = textSizeResource(id = R.dimen.font_size_medium),
        fontSizeMediumLarge = textSizeResource(id = R.dimen.font_size_medium_large),
        fontSizeLarge = textSizeResource(id = R.dimen.font_size_large),
        fontSizeXLarge = textSizeResource(id = R.dimen.font_size_xlarge),
        fontSizeXXLarge = textSizeResource(id = R.dimen.font_size_xxlarge),
        fontSizeXXXLarge = textSizeResource(id = R.dimen.font_size_xxxlarge),
        fontSizeHuge = textSizeResource(id = R.dimen.font_size_huge),
        fontSizeGiant = textSizeResource(id = R.dimen.font_size_giant)
    )
}

val LocalSizes = staticCompositionLocalOf { Sizes() }

