package com.appspiriment.composeutils.components.containers

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerDefaults.scrimColor
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.core.VerticalSpacer
import com.appspiriment.composeutils.components.core.image.AppsIcon
import com.appspiriment.composeutils.components.core.image.types.toUiImage
import com.appspiriment.composeutils.components.containers.types.AppsTopBarButton
import com.appspiriment.composeutils.components.containers.types.DrawerItem
import com.appspiriment.composeutils.components.containers.types.ScaffoldColors
import com.appspiriment.composeutils.components.core.text.MalayalamText
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.components.core.text.types.toUiText
import com.appspiriment.composeutils.theme.Appspiriment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DrawerScaffold(
    @DrawableRes brandLogoId: Int? = null,
    title: UiText? = null,
    colors: ScaffoldColors = ScaffoldColors.defaults(),
    gestureEnabled: Boolean = true,
    actions: List<AppsTopBarButton>? = null,
    drawerOptions: List<DrawerItem>? = null,
    drawerHeader: (@Composable () -> Unit)? = null,
    drawerFooter: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {

    DrawerScaffold(
        colors = colors,
        gestureEnabled = gestureEnabled,
        topBar = { _: DrawerState, _: CoroutineScope, onNavClick: () -> Unit ->
            TopBar(
                navMode = NavigationMode.DRAWER,
                navIconClick = onNavClick,
                actions = actions,
                brandLogoId = brandLogoId,
                title = title,
                background = colors.topBarBackground,
                onTopBarColor = colors.onTopBarColor
            )
        },
        drawerContent = { drawerState: DrawerState, coroutineScope: CoroutineScope ->
            drawerHeader?.invoke()
            VerticalSpacer()
            drawerState.run {
                drawerOptions?.forEach {
                    DrawerLayoutMenuItem(
                        scope = coroutineScope, item = it
                    )
                }
            }
            VerticalSpacer()
            drawerFooter?.invoke()
        },
        content = content
    )
}

@Composable
fun DrawerScaffold(
    colors: ScaffoldColors = ScaffoldColors.defaults(),
    gestureEnabled: Boolean = true,
    topBar: (@Composable (state: DrawerState, scope: CoroutineScope, drawerIconAction: () -> Unit) -> Unit)? = null,
    drawerContent: (@Composable ColumnScope.(state: DrawerState, scope: CoroutineScope) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = scrimColor,
        gesturesEnabled = gestureEnabled && drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape,
                modifier = Modifier.wrapContentWidth(),
                drawerContainerColor = colors.drawerBackgroundColor
            ) {
                drawerContent?.invoke(this, drawerState, scope)
            }
        },
    ) {
        PageScaffold(
            backgroundColor = colors.backgroundColor,
            topBar = {
                topBar?.invoke(drawerState, scope) {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            }) {

            content.invoke(this)
        }

    }
}

@Composable
fun DrawerState.DrawerLayoutMenuItem(
    scope: CoroutineScope = rememberCoroutineScope(),
    item: DrawerItem
) {

    if (item.showTopDivider) {
        HorizontalDivider(
            color = Appspiriment.colors.onBackground,
            thickness = 0.5.dp,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (item.closeDrawer) {
                    scope.launch {
                        if (isOpen) close()
                    }
                }
                item.onMenuClick.invoke()
            }
            .padding(start = 32.dp, top = item.verticalPadding, bottom = item.verticalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            item.icon?.let {
                AppsIcon(
                    icon = it,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp)
                )
            }
            MalayalamText(
                text = item.menuTitle,
                style = item.textStyle,
                modifier = Modifier.padding()
            )
        }
        item.trailingIcon?.let {
            AppsIcon(
                icon = it,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }

    if (item.showBottomDivider) {
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 0.5.dp,
            color = Appspiriment.colors.dividerColor
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DrawerScaffoldPreview() {
    DrawerScaffold(
        title = "Drawer Example".toUiText(),
        colors = ScaffoldColors.defaults(),
        actions = listOf(
            AppsTopBarButton(
                icon = Icons.Filled.Info.toUiImage(),
                onClick = { /* Handle action click */ }
            )
        ),
        drawerOptions = listOf(
            DrawerItem.from(
                menuTitle = "Home".toUiText(),
                icon = Icons.Filled.Home.toUiImage(),
                onMenuClick = { /* Handle Home click */ },
                closeDrawer = true
            ),
            DrawerItem.from(
                menuTitle = "Account".toUiText(),
                icon = Icons.Filled.AccountCircle.toUiImage(),
                onMenuClick = { /* Handle Home click */ },
                closeDrawer = true
            ),
            DrawerItem.from(
                menuTitle = "Settings".toUiText(),
                icon = Icons.Filled.Settings.toUiImage(),
                onMenuClick = { /* Handle Home click */ },
                closeDrawer = true
            ),
        ),
        drawerHeader = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Drawer Header", color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Additional info", color = Color.Gray)
            }
        },
        drawerFooter = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Drawer Footer", color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Copyright 2024", color = Color.Gray)
            }
        },
        content = { paddingValues ->
            Scaffold(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Text(text = "Main Content", color = Color.Black)
                }
            }
        }
    )
}