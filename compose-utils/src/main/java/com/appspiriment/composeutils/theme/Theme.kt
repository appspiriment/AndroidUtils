package com.appspiriment.composeutils.theme

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R

private val gmsProvider by lazy {
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}

/**
 * Convenience entry point for Malayalam / Noto-script content.
 * Defaults to [AppFontFamily.Noto]; pass a different [AppFontFamily] to override.
 */
@Composable
fun MalayalamCompositionBaseProvider(
    isDarkTheme: Boolean? = null,
    fontFamily: AppFontFamily = AppFontFamily.Noto(),
    content: @Composable () -> Unit,
) {
    CompositionBaseProvider(isDarkTheme = isDarkTheme, fontFamily = fontFamily, content = content)
}

/**
 * Root theme provider. Wrap your activity/screen content with this composable.
 *
 * @param isDarkTheme Force light/dark mode. `null` follows the system setting.
 * @param fontFamily  Which font family to activate. Defaults to [AppFontFamily.Roboto].
 *                    Use [AppFontFamily.Noto] for scripts with taller metrics (e.g. Malayalam).
 *                    [AppFontFamily.GmsFont] silently falls back to the system font on
 *                    devices without Google Play Services.
 */
@Composable
fun CompositionBaseProvider(
    isDarkTheme: Boolean? = null,
    fontFamily: AppFontFamily = AppFontFamily.Roboto,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val (forcedContext, forcedConfiguration) = remember(isDarkTheme, context, configuration) {
        if (isDarkTheme != null) {
            val targetNightMode = if (isDarkTheme) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
            val newConfig = Configuration(configuration).apply {
                uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or targetNightMode
            }
            val configContext = context.createConfigurationContext(newConfig)
            // Wrap the original Activity context so DI frameworks (e.g. Hilt) can still resolve it,
            // while resources/assets are served from the configuration-specific context.
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
        LocalConfiguration provides forcedConfiguration,
    ) {
        val resolvedFont = remember(fontFamily) {
            when (fontFamily) {
                AppFontFamily.Roboto     -> GoogleFonts.robotoFamily
                is AppFontFamily.Noto    -> GoogleFonts.notoFamily
                AppFontFamily.System     -> null
                is AppFontFamily.Custom  -> fontFamily.fontFamily
                is AppFontFamily.GmsFont -> runCatching { fontFamily.create(gmsProvider) }.getOrNull()
            }
        }
        val colors    = remember(forcedConfiguration) { baseColors(forcedContext) }
        val sizes     = remember(forcedConfiguration) { createSizes(forcedContext) }
        val typography = remember(forcedConfiguration, resolvedFont) {
            createBaseTypography(forcedContext, resolvedFont)
        }
        val flags = remember(fontFamily) {
            BaseFlags(
                isNotoFont = fontFamily is AppFontFamily.Noto,
                notoFontPadding = if (fontFamily is AppFontFamily.Noto) fontFamily.fontPadding else 0.dp,
            )
        }
        CompositionLocalProvider(
            LocalColors provides colors,
            LocalSizes provides sizes,
            LocalTypography provides typography,
            LocalFlags provides flags,
            content = content,
        )
    }
}

/**
 * Global theme accessor. All properties are @ReadOnlyComposable — safe to call from any
 * composable without triggering recomposition overhead. Use from non-composable code
 * (e.g. ViewModels) by converting at the composable boundary:
 *   val tint = Appspiriment.colors.primary.toUiColor()
 */
object Appspiriment {
    val colors: BaseColors
        @Composable @ReadOnlyComposable get() = LocalColors.current
    val sizes: Sizes
        @Composable @ReadOnlyComposable get() = LocalSizes.current
    val typography: BaseTextStyles
        @Composable @ReadOnlyComposable get() = LocalTypography.current
    val flags: BaseFlags
        @Composable @ReadOnlyComposable get() = LocalFlags.current
}
