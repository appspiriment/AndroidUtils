package com.appspiriment.composeutils.theme

import android.content.Context
import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R

/**
 * Design-token dimension palette for the Appspiriment theme.
 *
 * Consumed inside composable functions via [Appspiriment.sizes].
 * When a [Dp] value is needed as a [com.appspiriment.composeutils.wrappers.UiDimen]
 * (e.g. to pass into a ViewModel), convert inline:
 *   Appspiriment.sizes.paddingMedium.toUiDimen()
 */
data class Sizes(
    val appBarSize: Dp = Dp.Unspecified,

    val iconXSmall: Dp = Dp.Unspecified,
    val iconSmall: Dp = Dp.Unspecified,
    val iconMedium: Dp = Dp.Unspecified,
    val iconStandard: Dp = Dp.Unspecified,
    val iconStandardLarge: Dp = Dp.Unspecified,
    val iconLarge: Dp = Dp.Unspecified,
    val iconXLarge: Dp = Dp.Unspecified,
    val iconXXLarge: Dp = Dp.Unspecified,
    val iconXXXLarge: Dp = Dp.Unspecified,
    val iconXXXXLarge: Dp = Dp.Unspecified,
    val iconBig: Dp = Dp.Unspecified,
    val iconGiant: Dp = Dp.Unspecified,

    val paddingGiant: Dp = Dp.Unspecified,
    val paddingXXXXLarge: Dp = Dp.Unspecified,
    val paddingXXXLarge: Dp = Dp.Unspecified,
    val paddingXXLarge: Dp = Dp.Unspecified,
    val paddingXLarge: Dp = Dp.Unspecified,
    val paddingLarge: Dp = Dp.Unspecified,
    val paddingMedium: Dp = Dp.Unspecified,
    val paddingSmallMedium: Dp = Dp.Unspecified,
    val paddingSmall: Dp = Dp.Unspecified,
    val paddingXSmallPlus: Dp = Dp.Unspecified,
    val paddingXSmall: Dp = Dp.Unspecified,
    val paddingXXSmall: Dp = Dp.Unspecified,
    val paddingTiny: Dp = Dp.Unspecified,
    val noPadding: Dp = Dp.Unspecified,

    val cornerRadiusSmall: Dp = Dp.Unspecified,
    val cornerRadiusMedium: Dp = Dp.Unspecified,
    val cornerRadiusNormal: Dp = Dp.Unspecified,
    val cornerRadiusMediumLarge: Dp = Dp.Unspecified,
    val cornerRadiusLarge: Dp = Dp.Unspecified,
    val cornerRadiusXLarge: Dp = Dp.Unspecified,
    val cornerRadiusXXLarge: Dp = Dp.Unspecified,
    val cornerRadiusXXXLarge: Dp = Dp.Unspecified,

    val actionButtonSize: Dp = Dp.Unspecified,
    val floatingButtonSizeSmall: Dp = Dp.Unspecified,
    val floatingButtonSize: Dp = Dp.Unspecified,
    val floatingButtonSizeLarge: Dp = Dp.Unspecified,
)

/** Non-composable factory — callable from tests, ViewModels, and remember{} blocks. */
internal fun createSizes(context: Context): Sizes {
    val res = context.resources
    val density = res.displayMetrics.density
    fun @receiver:DimenRes Int.toDp() = Dp(res.getDimension(this) / density)

    return Sizes(
        appBarSize = R.dimen.app_bar_height.toDp(),

        iconXSmall = R.dimen.icon_xsmall.toDp(),
        iconSmall = R.dimen.icon_small.toDp(),
        iconMedium = R.dimen.icon_medium.toDp(),
        iconStandard = R.dimen.icon_standard.toDp(),
        iconStandardLarge = R.dimen.icon_standard_large.toDp(),
        iconLarge = R.dimen.icon_large.toDp(),
        iconXLarge = R.dimen.icon_xlarge.toDp(),
        iconXXLarge = R.dimen.icon_xxlarge.toDp(),
        iconXXXLarge = R.dimen.icon_xxxlarge.toDp(),
        iconXXXXLarge = R.dimen.icon_xxxxlarge.toDp(),
        iconBig = R.dimen.icon_big.toDp(),
        iconGiant = R.dimen.icon_giant.toDp(),

        paddingGiant = R.dimen.padding_giant.toDp(),
        paddingXXXXLarge = R.dimen.padding_xxxxlarge.toDp(),
        paddingXXXLarge = R.dimen.padding_xxxlarge.toDp(),
        paddingXXLarge = R.dimen.padding_xxlarge.toDp(),
        paddingXLarge = R.dimen.padding_xlarge.toDp(),
        paddingLarge = R.dimen.padding_large.toDp(),
        paddingMedium = R.dimen.padding_medium.toDp(),
        paddingSmallMedium = R.dimen.padding_smallmedium.toDp(),
        paddingSmall = R.dimen.padding_small.toDp(),
        paddingXSmallPlus = R.dimen.padding_xsmall_plus.toDp(),
        paddingXSmall = R.dimen.padding_xsmall.toDp(),
        paddingXXSmall = R.dimen.padding_xxsmall.toDp(),
        paddingTiny = R.dimen.padding_tiny.toDp(),
        noPadding = 0.dp,

        cornerRadiusSmall = R.dimen.corner_radius_small.toDp(),
        cornerRadiusMedium = R.dimen.corner_radius_medium.toDp(),
        cornerRadiusNormal = R.dimen.corner_radius_normal.toDp(),
        cornerRadiusMediumLarge = R.dimen.corner_radius_medium_large.toDp(),
        cornerRadiusLarge = R.dimen.corner_radius_large.toDp(),
        cornerRadiusXLarge = R.dimen.corner_radius_xlarge.toDp(),
        cornerRadiusXXLarge = R.dimen.corner_radius_xxlarge.toDp(),
        cornerRadiusXXXLarge = R.dimen.corner_radius_xxxlarge.toDp(),

        actionButtonSize = R.dimen.action_button_size.toDp(),
        floatingButtonSizeSmall = R.dimen.fab_button_size_small.toDp(),
        floatingButtonSize = R.dimen.fab_button_size.toDp(),
        floatingButtonSizeLarge = R.dimen.fab_button_size_large.toDp(),
    )
}

/** Composable wrapper — reads [LocalContext] and delegates to the non-composable overload. */
@Composable
@ReadOnlyComposable
internal fun createSizes() = createSizes(LocalContext.current)

val LocalSizes by lazy { staticCompositionLocalOf { Sizes() } }
