package com.appspiriment.composeutils.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.appspiriment.composeutils.R

data class BaseColors(
    val primary: Color = Color.Unspecified,
    val onPrimary : Color = Color.Unspecified,
    val secondary : Color = Color.Unspecified,
    val onSecondary : Color = Color.Unspecified,
    val tertiary : Color = Color.Unspecified,
    val onTertiary : Color = Color.Unspecified,
    val scrimColor : Color = Color.Unspecified,
    val navigationBarColor : Color = Color.Unspecified,
    val mainSurface : Color = Color.Unspecified,
    val onMainSurface : Color = Color.Unspecified,
    val secondarySurface : Color = Color.Unspecified,
    val onSecondarySurface : Color = Color.Unspecified,
    val background : Color = Color.Unspecified,
    val onBackground : Color = Color.Unspecified,
    val primaryCardContainer : Color = Color.Unspecified,
    val onPrimaryCardContainer : Color = Color.Unspecified,
    val secondaryCardContainer : Color = Color.Unspecified,
    val onSecondaryCardContainer : Color = Color.Unspecified,
    val tertiaryCardContainer : Color = Color.Unspecified,
    val onTertiaryCardContainer : Color = Color.Unspecified,
    val greyCardContainer : Color = Color.Unspecified,
    val onGreyCardContainer : Color = Color.Unspecified,
    val error : Color = Color.Unspecified,
    val onError: Color = Color.Unspecified,
    val errorCardContainer : Color = Color.Unspecified,
    val onErrorCardContainer : Color = Color.Unspecified,
    val accentedBlueTitle : Color = Color.Unspecified,
    val accentedBlackTitle : Color = Color.Unspecified,
    val accentedRedTitle : Color = Color.Unspecified,
    val accentedBlueText : Color = Color.Unspecified,
    val accentedRedText : Color = Color.Unspecified,
    val accentedGoldText : Color = Color.Unspecified,
    val topAppBar : Color = Color.Unspecified,
    val onTopAppBar : Color = Color.Unspecified,
    val disabledText : Color = Color.Unspecified,
    val subText : Color = Color.Unspecified,
    val dividerColor : Color = Color.Unspecified,
)

// Light color scheme object
@Composable
fun lightBaseColors() = BaseColors(
    primary = colorResource(id = R.color.primary),
    onPrimary = colorResource(id = R.color.onPrimary),
    secondary = colorResource(id = R.color.secondary),
    onSecondary = colorResource(id = R.color.onSecondary),
    tertiary = colorResource(id = R.color.tertiary),
    onTertiary = colorResource(id = R.color.onTertiary),
    scrimColor = colorResource(id = R.color.scrim),
    navigationBarColor = colorResource(id = R.color.navigationBar),
    mainSurface = colorResource(id = R.color.mainSurface),
    onMainSurface = colorResource(id = R.color.onMainSurface),
    secondarySurface = colorResource(id = R.color.secondarySurface),
    onSecondarySurface = colorResource(id = R.color.onSecondarySurface),
    background = colorResource(id = R.color.background),
    onBackground = colorResource(id = R.color.onBackground),
    primaryCardContainer = colorResource(id = R.color.primaryCardContainer),
    onPrimaryCardContainer = colorResource(id = R.color.onPrimaryCardContainer),
    secondaryCardContainer = colorResource(id = R.color.secondaryCardContainer),
    onSecondaryCardContainer = colorResource(id = R.color.onSecondaryCardContainer),
    tertiaryCardContainer = colorResource(id = R.color.tertiaryCardContainer),
    onTertiaryCardContainer = colorResource(id = R.color.onTertiaryCardContainer),
    greyCardContainer = colorResource(id = R.color.greyCardContainer),
    onGreyCardContainer = colorResource(id = R.color.onGreyCardContainer),
    error = colorResource(id = R.color.error),
    onError = colorResource(id = R.color.onError),
    errorCardContainer = colorResource(id = R.color.errorCardContainer),
    onErrorCardContainer = colorResource(id = R.color.onErrorCardContainer),
    accentedBlueTitle = colorResource(id = R.color.accentedBlueTitle),
    accentedBlackTitle = colorResource(id = R.color.accentedBlackTitle),
    accentedRedTitle = colorResource(id = R.color.accentedRedTitle),
    accentedBlueText = colorResource(id = R.color.accentedBlueText),
    accentedRedText = colorResource(id = R.color.accentedRedText),
    accentedGoldText = colorResource(id = R.color.accentedGoldText),
    topAppBar = colorResource(id = R.color.topAppBar),
    onTopAppBar = colorResource(id = R.color.onTopAppBar),
    disabledText = colorResource(id = R.color.disabledText),
    subText = colorResource(id = R.color.subText),
    dividerColor = colorResource(id = R.color.dividerColor)
)

// Light color scheme object
@Composable
fun darkBaseColors() = BaseColors(
    primary = colorResource(id = R.color.primary),
    onPrimary = colorResource(id = R.color.onPrimary),
    secondary = colorResource(id = R.color.secondary),
    onSecondary = colorResource(id = R.color.onSecondary),
    tertiary = colorResource(id = R.color.tertiary),
    onTertiary = colorResource(id = R.color.onTertiary),
    scrimColor = colorResource(id = R.color.scrim),
    navigationBarColor = colorResource(id = R.color.navigationBar),
    mainSurface = colorResource(id = R.color.mainSurface),
    onMainSurface = colorResource(id = R.color.onMainSurface),
    secondarySurface = colorResource(id = R.color.secondarySurface),
    onSecondarySurface = colorResource(id = R.color.onSecondarySurface),
    background = colorResource(id = R.color.background),
    onBackground = colorResource(id = R.color.onBackground),
    primaryCardContainer = colorResource(id = R.color.primaryCardContainer),
    onPrimaryCardContainer = colorResource(id = R.color.onPrimaryCardContainer),
    secondaryCardContainer = colorResource(id = R.color.secondaryCardContainer),
    onSecondaryCardContainer = colorResource(id = R.color.onSecondaryCardContainer),
    tertiaryCardContainer = colorResource(id = R.color.tertiaryCardContainer),
    onTertiaryCardContainer = colorResource(id = R.color.onTertiaryCardContainer),
    greyCardContainer = colorResource(id = R.color.greyCardContainer),
    onGreyCardContainer = colorResource(id = R.color.onGreyCardContainer),
    errorCardContainer = colorResource(id = R.color.errorCardContainer),
    onErrorCardContainer = colorResource(id = R.color.onErrorCardContainer),
    accentedBlueTitle = colorResource(id = R.color.accentedBlueTitle),
    accentedBlackTitle = colorResource(id = R.color.accentedBlackTitle),
    accentedRedTitle = colorResource(id = R.color.accentedRedTitle),
    topAppBar = colorResource(id = R.color.topAppBar),
    onTopAppBar = colorResource(id = R.color.onTopAppBar),
    disabledText = colorResource(id = R.color.disabledText),
    subText = colorResource(id = R.color.subText),
    accentedBlueText = colorResource(id = R.color.accentedBlueText),
    accentedRedText = colorResource(id = R.color.accentedRedText),
    accentedGoldText = colorResource(id = R.color.accentedGoldText),
    error = colorResource(id = R.color.error),
    onError = colorResource(id = R.color.onError),
    dividerColor = colorResource(id = R.color.dividerColor)
)
val LocalColors = staticCompositionLocalOf { BaseColors() }
