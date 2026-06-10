package com.appspiriment.composeutils.theme

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.appspiriment.composeutils.R

/**
 * Design-token color palette for the Appspiriment theme.
 *
 * Consumed inside composable functions via [Appspiriment.colors].
 * When a [androidx.compose.ui.graphics.Color] value is needed as a [com.appspiriment.composeutils.wrappers.UiColor]
 * (e.g. to pass into a ViewModel or a UiImage tint), convert inline:
 *   Appspiriment.colors.primary.toUiColor()
 */
data class BaseColors(
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val onSecondary: Color = Color.Unspecified,
    val tertiary: Color = Color.Unspecified,
    val onTertiary: Color = Color.Unspecified,
    val iconTint: Color = Color.Unspecified,
    val disabledIconTint: Color = Color.Unspecified,
    val drawerItem: Color = Color.Unspecified,
    val scrimColor: Color = Color.Unspecified,
    val navigationBarColor: Color = Color.Unspecified,
    val mainSurface: Color = Color.Unspecified,
    val onMainSurface: Color = Color.Unspecified,
    val secondarySurface: Color = Color.Unspecified,
    val onSecondarySurface: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val onBackground: Color = Color.Unspecified,
    val primaryCardContainer: Color = Color.Unspecified,
    val onPrimaryCardContainer: Color = Color.Unspecified,
    val onPrimaryCardContainerDimmed: Color = Color.Unspecified,
    val secondaryCardContainer: Color = Color.Unspecified,
    val onSecondaryCardContainer: Color = Color.Unspecified,
    val tertiaryCardContainer: Color = Color.Unspecified,
    val onTertiaryCardContainer: Color = Color.Unspecified,
    val greyCardContainer: Color = Color.Unspecified,
    val onGreyCardContainer: Color = Color.Unspecified,
    val error: Color = Color.Unspecified,
    val onError: Color = Color.Unspecified,
    val errorCardContainer: Color = Color.Unspecified,
    val onErrorCardContainer: Color = Color.Unspecified,
    val accentedBlueTitle: Color = Color.Unspecified,
    val accentedBlackTitle: Color = Color.Unspecified,
    val accentedRedTitle: Color = Color.Unspecified,
    val accentedBlueText: Color = Color.Unspecified,
    val accentedRedText: Color = Color.Unspecified,
    val accentedGoldText: Color = Color.Unspecified,
    val topAppBar: Color = Color.Unspecified,
    val onTopAppBar: Color = Color.Unspecified,
    val disabledText: Color = Color.Unspecified,
    val subText: Color = Color.Unspecified,
    val hintText: Color = Color.Unspecified,
    val dividerColor: Color = Color.Unspecified,
)

/** Non-composable factory — callable from tests, ViewModels, and remember{} blocks. */
internal fun baseColors(context: Context) = BaseColors(
    primary                      = Color(ContextCompat.getColor(context, R.color.primary)),
    onPrimary                    = Color(ContextCompat.getColor(context, R.color.onPrimary)),
    secondary                    = Color(ContextCompat.getColor(context, R.color.secondary)),
    onSecondary                  = Color(ContextCompat.getColor(context, R.color.onSecondary)),
    tertiary                     = Color(ContextCompat.getColor(context, R.color.tertiary)),
    onTertiary                   = Color(ContextCompat.getColor(context, R.color.onTertiary)),
    scrimColor                   = Color(ContextCompat.getColor(context, R.color.scrim)),
    iconTint                     = Color(ContextCompat.getColor(context, R.color.iconTint)),
    disabledIconTint             = Color(ContextCompat.getColor(context, R.color.disabledIconTint)),
    drawerItem                   = Color(ContextCompat.getColor(context, R.color.drawerItem)),
    navigationBarColor           = Color(ContextCompat.getColor(context, R.color.navigationBar)),
    mainSurface                  = Color(ContextCompat.getColor(context, R.color.mainSurface)),
    onMainSurface                = Color(ContextCompat.getColor(context, R.color.onMainSurface)),
    secondarySurface             = Color(ContextCompat.getColor(context, R.color.secondarySurface)),
    onSecondarySurface           = Color(ContextCompat.getColor(context, R.color.onSecondarySurface)),
    background                   = Color(ContextCompat.getColor(context, R.color.background)),
    onBackground                 = Color(ContextCompat.getColor(context, R.color.onBackground)),
    primaryCardContainer         = Color(ContextCompat.getColor(context, R.color.primaryCardContainer)),
    onPrimaryCardContainer       = Color(ContextCompat.getColor(context, R.color.onPrimaryCardContainer)),
    onPrimaryCardContainerDimmed = Color(ContextCompat.getColor(context, R.color.onPrimaryCardContainerDimmed)),
    secondaryCardContainer       = Color(ContextCompat.getColor(context, R.color.secondaryCardContainer)),
    onSecondaryCardContainer     = Color(ContextCompat.getColor(context, R.color.onSecondaryCardContainer)),
    tertiaryCardContainer        = Color(ContextCompat.getColor(context, R.color.tertiaryCardContainer)),
    onTertiaryCardContainer      = Color(ContextCompat.getColor(context, R.color.onTertiaryCardContainer)),
    greyCardContainer            = Color(ContextCompat.getColor(context, R.color.greyCardContainer)),
    onGreyCardContainer          = Color(ContextCompat.getColor(context, R.color.onGreyCardContainer)),
    error                        = Color(ContextCompat.getColor(context, R.color.error)),
    onError                      = Color(ContextCompat.getColor(context, R.color.onError)),
    errorCardContainer           = Color(ContextCompat.getColor(context, R.color.errorCardContainer)),
    onErrorCardContainer         = Color(ContextCompat.getColor(context, R.color.onErrorCardContainer)),
    accentedBlueTitle            = Color(ContextCompat.getColor(context, R.color.accentedBlueTitle)),
    accentedBlackTitle           = Color(ContextCompat.getColor(context, R.color.accentedBlackTitle)),
    accentedRedTitle             = Color(ContextCompat.getColor(context, R.color.accentedRedTitle)),
    accentedBlueText             = Color(ContextCompat.getColor(context, R.color.accentedBlueText)),
    accentedRedText              = Color(ContextCompat.getColor(context, R.color.accentedRedText)),
    accentedGoldText             = Color(ContextCompat.getColor(context, R.color.accentedGoldText)),
    topAppBar                    = Color(ContextCompat.getColor(context, R.color.topAppBar)),
    onTopAppBar                  = Color(ContextCompat.getColor(context, R.color.onTopAppBar)),
    disabledText                 = Color(ContextCompat.getColor(context, R.color.disabledText)),
    subText                      = Color(ContextCompat.getColor(context, R.color.subText)),
    hintText                     = Color(ContextCompat.getColor(context, R.color.hintText)),
    dividerColor                 = Color(ContextCompat.getColor(context, R.color.dividerColor)),
)

/** Composable wrapper — reads [LocalContext] and delegates to the non-composable overload. */
@Composable
@ReadOnlyComposable
internal fun baseColors() = baseColors(LocalContext.current)

val LocalColors = staticCompositionLocalOf { BaseColors() }
