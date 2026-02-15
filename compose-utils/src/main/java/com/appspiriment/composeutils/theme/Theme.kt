package com.appspiriment.composeutils.theme

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)


@Composable
fun MalayalamCompositionBaseProvider(
    isDarkTheme: Boolean? = null,
    fontFamily: ((GoogleFont.Provider)->FontFamily)? = null,
    content: @Composable () -> Unit
) {
    val fontProvider = fontFamily ?: { GoogleFonts.notoFamily }
    CompositionBaseProvider(isDarkTheme = isDarkTheme, fontFamily = fontProvider, content = content)
}


@Composable
fun CompositionBaseProvider(
    isDarkTheme: Boolean? = null,
    fontFamily: ((GoogleFont.Provider)->FontFamily)? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val (forcedContext, forcedConfiguration) = remember(isDarkTheme, context, configuration) {
        if (isDarkTheme != null) {
            val targetNightMode = if (isDarkTheme) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
            val newConfig = Configuration(configuration).apply {
                uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or targetNightMode
            }

            // Create the configuration-specific context
            val configContext = context.createConfigurationContext(newConfig)
            
            // Wrap the ORIGINAL context (which is likely the Activity) so Hilt can find it.
            // We override getResources and getAssets to use the themed versions.
            val wrappedContext = object : android.content.ContextWrapper(context) {
                override fun getResources(): android.content.res.Resources = configContext.resources
                override fun getAssets(): android.content.res.AssetManager = configContext.assets
                override fun getTheme(): android.content.res.Resources.Theme = configContext.theme
            }
            
            wrappedContext to newConfig
        } else {
            context to configuration
        }
    }

    CompositionLocalProvider(
        LocalContext provides forcedContext,
        LocalConfiguration provides forcedConfiguration
    ) {
        // Colors are resolved here using the forcedContext provided above
        val colors = baseColors()
        val uiColors = baseUiColors()

        val sizes = createSizes()
        val uiSizes = createUiSizes()
        val typography = createBaseTypography(baseSize = sizes, fontFamily = fontFamily?.invoke(provider))
        
        // This check should be verified against how GoogleFonts.notoFamily is defined
        val isNotoFont = fontFamily == GoogleFonts.notoFamily
        val flags = BaseFlags(
            isNotoFont = isNotoFont,
            notoFontPadding = if(isNotoFont) (0).dp else 0.dp
        )

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
