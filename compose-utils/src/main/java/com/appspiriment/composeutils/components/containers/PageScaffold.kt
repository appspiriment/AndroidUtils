package com.appspiriment.composeutils.components.containers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.containers.types.AppsTopBarButton
import com.appspiriment.composeutils.components.containers.types.ScaffoldColors
import com.appspiriment.composeutils.components.core.progress.FullscreenLoader
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.toUiColor

@Composable
fun AppsPageScaffold(
    modifier: Modifier = Modifier,
    navMode: NavigationMode = NavigationMode.EMPTY,
    navIconClick: (() -> Unit)? = null,
    appBarTitle: AppBarTitle? = null,
    actions: List<AppsTopBarButton>? = null,
    actionsContent: @Composable RowScope.(Color) -> Unit = {},
    bottomBar: (@Composable () -> Unit) = {},
    isLoading: Boolean = false,
    colors: ScaffoldColors = ScaffoldColors.defaults(),
    content: @Composable PaddingValues.() -> Unit,
) {
    PageScaffold(
        topBar = {
            TopBar(
                navMode = navMode,
                navIconClick = navIconClick,
                actions = actions,
                actionsContent = actionsContent,
                appBarTitle = appBarTitle,
                background = colors.topBarBackground.toUiColor(),
                onTopBarColor = colors.onTopBarColor.toUiColor()
            )
        },
        bottomBar = bottomBar,
        modifier = modifier,
        isLoading = isLoading,
        backgroundColor = colors.backgroundColor
    ) { content() }
}


@Composable
fun PageScaffold(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit) = {},
    backgroundColor: Color = Appspiriment.colors.background,
    content: @Composable PaddingValues.() -> Unit,
) {
    Scaffold(
        topBar = {
            topBar?.invoke()
        },
        bottomBar = bottomBar,
        modifier = modifier.fillMaxSize(),
        containerColor = backgroundColor
    ) {
        content.invoke(it)
        if (isLoading) {
            FullscreenLoader()
        }
    }
}

sealed class NavigationMode(val icon: ImageVector, val contentDescription: String? = null) {
    data object EMPTY : NavigationMode(Icons.Default.Home, null)
    data object BACK : NavigationMode(Icons.AutoMirrored.Filled.ArrowBack, "Back")
    data object CLOSE : NavigationMode(Icons.Default.Close, "Close")
    data object DRAWER : NavigationMode(Icons.Default.Menu, "Menu")
    data object HOME : NavigationMode(Icons.Default.Home, "Home")
}

sealed class AppBarTitle(open val modifier: Modifier) {
    data class BrandLogo(val image: UiImage, override val modifier: Modifier = Modifier) :
        AppBarTitle(modifier)

    data class ScreenTitle(val title: UiText, override val modifier: Modifier = Modifier) :
        AppBarTitle(modifier)

    data class ScreenTitleWithIcon(
        val icon: UiImage,
        val iconHeight: Dp = 40.dp,
        val title: UiText,
        val subTitle: UiText,
        override val modifier: Modifier = Modifier
    ) :
        AppBarTitle(modifier)
}