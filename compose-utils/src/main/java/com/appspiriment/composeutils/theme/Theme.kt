package com.appspiriment.composeutils.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


@Composable
fun CompositionBaseProvider(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if(darkTheme) darkBaseColors() else lightBaseColors()

    val sizes = createSizes()
    val typography = createBaseTypography()

    // Provide custom colors and MaterialTheme
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalSizes provides sizes,
        LocalTypography provides typography,
        content = content
     )
}

object Appspiriment{
    val colors: BaseColors
        @Composable  @ReadOnlyComposable get() = LocalColors.current
    val sizes: Sizes
        @Composable  @ReadOnlyComposable get() = LocalSizes.current
    val typography: BaseTextStyles
        @Composable @ReadOnlyComposable get() = LocalTypography.current
}
