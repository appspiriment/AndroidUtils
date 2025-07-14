package com.appspiriment.composeutils.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontFamily


@Composable
fun CompositionBaseProvider(
    content: @Composable () -> Unit
) {
    MalayalamCompositionBaseProvider(fontFamily = null, content = content)
}


@Composable
fun MalayalamCompositionBaseProvider(
    fontFamily: FontFamily? = GoogleFonts.notoFamily,
    content: @Composable () -> Unit
) {
    val colors = baseColors()
    val uiColors = baseUiColors()

    val sizes = createSizes()
    val uiSizes = createUiSizes()
    val typography = createBaseTypography(baseSize = sizes, fontFamily = fontFamily)
    val flags = BaseFlags(
        isNotoFont = fontFamily == GoogleFonts.notoFamily
    )

    // Provide custom colors and MaterialTheme
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalUiColors provides uiColors,
        LocalSizes provides sizes,
        LocalUiSizes provides uiSizes,
        LocalTypography provides typography,
        LocalFlags provides flags,
        content = content
    )
}

object Appspiriment{
    val colors: BaseColors
        @Composable  @ReadOnlyComposable get() = LocalColors.current
    val uiColors: BaseUiColors
        @Composable  @ReadOnlyComposable get() = LocalUiColors.current
    val sizes: Sizes
        @Composable  @ReadOnlyComposable get() = LocalSizes.current
    val uiSizes: UiSizes
        @Composable  @ReadOnlyComposable get() = LocalUiSizes.current
    val typography: BaseTextStyles
        @Composable @ReadOnlyComposable get() = LocalTypography.current
    val flags: BaseFlags
        @Composable @ReadOnlyComposable get() = LocalFlags.current
}
