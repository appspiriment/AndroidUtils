package com.appspiriment.composeutils.components.containers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.appspiriment.composeutils.components.containers.types.AppBarTitle
import com.appspiriment.composeutils.components.containers.types.AppsTopBarButton
import com.appspiriment.composeutils.components.containers.types.NavigationMode
import com.appspiriment.composeutils.components.containers.types.ScaffoldColors
import com.appspiriment.composeutils.components.core.progress.FullscreenLoader
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.Appspiriment.sizes
import com.appspiriment.composeutils.wrappers.toUiImage
import com.appspiriment.composeutils.wrappers.toUiText

@Composable
fun AppsPageScaffold(
    modifier: Modifier = Modifier,
    navMode: NavigationMode = NavigationMode.EMPTY,
    navIconClick: (() -> Unit)? = null,
    appBarTitle: AppBarTitle? = null,
    actions: List<AppsTopBarButton>? = null,
    actionsContent: @Composable RowScope.(Color) -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    isLoading: Boolean = false,
    colors: ScaffoldColors = ScaffoldColors.defaults(),
    contentPadding: PaddingValues = PaddingValues(all = sizes.noPadding),
    content: @Composable ColumnScope.() -> Unit,
) {
    PageScaffold(
        topBar = {
            AppsTopBar(
                navMode = navMode,
                navIconClick = navIconClick,
                actions = actions,
                actionsContent = actionsContent,
                appBarTitle = appBarTitle,
                background = colors.topBarBackground,
                onTopBarColor = colors.onTopBarColor,
            )
        },
        bottomBar = bottomBar,
        modifier = modifier,
        isLoading = isLoading,
        floatingActionButton = floatingActionButton,
        backgroundColor = colors.backgroundColor,
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            content()
        }
    }
}

@Composable
fun PageScaffold(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    topBar: (@Composable () -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    backgroundColor: Color = Appspiriment.colors.background,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = { topBar?.invoke() },
        bottomBar = bottomBar,
        modifier = modifier.fillMaxSize(),
        floatingActionButton = floatingActionButton,
        containerColor = backgroundColor,
    ) { innerPadding ->
        content(innerPadding)
        if (isLoading) FullscreenLoader()
    }
}

@Preview
@Composable
fun PreviewPageScaffold() {
    AppsPageScaffold(
        isLoading = false,
        appBarTitle = AppBarTitle.ScreenTitleWithIcon(
            icon = Icons.Filled.Person3.toUiImage(),
            title = "Test Application".toUiText(),
        ),
        actions = listOf(
            AppsTopBarButton(icon = Icons.Filled.Menu.toUiImage(), onClick = {}),
        ),
    ) {
        AppspirimentText("Appspiriment Labs")
    }
}
