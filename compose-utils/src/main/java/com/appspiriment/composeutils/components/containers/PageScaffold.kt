package com.appspiriment.composeutils.components.containers

import androidx.annotation.DrawableRes
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
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.containers.types.AppsTopBarButton
import com.appspiriment.composeutils.components.containers.types.ScaffoldColors
import com.appspiriment.composeutils.components.core.text.types.UiText

@Composable
fun AppsPageScaffold(
    modifier: Modifier = Modifier,
    navMode: NavigationMode = NavigationMode.EMPTY,
    navIconClick: (() -> Unit)? = null,
    @DrawableRes brandLogoId: Int?,
    title: UiText? = null,
    actions: List<AppsTopBarButton>? = null,
    actionsContent: @Composable RowScope.(Color) -> Unit = {},
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
                brandLogoId = brandLogoId,
                title = title,
                background = colors.topBarBackground,
                onTopBarColor = colors.onTopBarColor
            )
        }, modifier = modifier, backgroundColor = colors.backgroundColor
    ) { content() }
}


@Composable
fun PageScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    backgroundColor: Color = Color.LightGray,
    content: @Composable PaddingValues.() -> Unit,
) {
    Scaffold(
        topBar = {
            topBar?.invoke()
        }, modifier = modifier.fillMaxSize(),
        containerColor = backgroundColor
    ) { content.invoke(it) }
}

sealed class NavigationMode(val icon: ImageVector) {
    data object EMPTY : NavigationMode(Icons.Default.Home)
    data object BACK : NavigationMode(Icons.AutoMirrored.Filled.ArrowBack)
    data object CLOSE : NavigationMode(Icons.Default.Close)
    data object DRAWER : NavigationMode(Icons.Default.Menu)
    data object HOME : NavigationMode(Icons.Default.Home)
}