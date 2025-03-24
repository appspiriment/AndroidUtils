package com.appspiriment.composeutils.components.containers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.image.AppsIcon
import com.appspiriment.composeutils.components.containers.types.AppsTopBarButton
import com.appspiriment.composeutils.components.core.image.AppsImage
import com.appspiriment.composeutils.components.core.text.MalayalamText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.semiBold
import com.appspiriment.composeutils.wrappers.UiColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navMode: NavigationMode = NavigationMode.EMPTY,
    navIconClick: (() -> Unit)? = null,
    appBarTitle: AppBarTitle?,
    background: UiColor = Appspiriment.uiColors.topAppBar,
    onTopBarColor: UiColor = Appspiriment.uiColors.onTopAppBar,
    actions: List<AppsTopBarButton>? = null,
    actionsContent: @Composable RowScope.(Color) -> Unit = {},
) {
    val backgroundColor = background.asColor(LocalContext.current)
    val contentColor = onTopBarColor.asColor(LocalContext.current)
    TopAppBar(
        navigationIcon = {
            if (navMode != NavigationMode.EMPTY) {
                Icon(imageVector = navMode.icon,
                    contentDescription = "Back",
                    tint = contentColor,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { navIconClick?.invoke() })
            }
        },
        title = {
            appBarTitle?.let {
                AppBarTitleImage(
                    appBarTitle = it,
                    tintColor = onTopBarColor
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor,
            navigationIconContentColor = contentColor,
            titleContentColor = contentColor,
            actionIconContentColor = contentColor
        ),
        actions = {
            actions?.forEach { btn ->
                AppsIcon(
                    icon = btn.icon.setTint(tint = onTopBarColor),
                    modifier = btn.modifier
                        .padding(end = 4.dp)
                        .size(Appspiriment.sizes.actionButtonSize)
                        .clickable { btn.onClick.invoke() }
                        .padding(12.dp),
                )
            }
            actionsContent(contentColor)
        }
    )
}


@Composable
fun AppBarTitleImage(
    appBarTitle: AppBarTitle,
    tintColor: UiColor = Appspiriment.uiColors.onTopAppBar,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = appBarTitle.modifier
    ) {
        when (appBarTitle) {
            is AppBarTitle.BrandLogo -> {
                AppsImage(
                    image = appBarTitle.image,
                    modifier = Modifier.wrapContentWidth()
                )
            }

            is AppBarTitle.ScreenTitle -> {
                MalayalamText(
                    text = appBarTitle.title,
                    style = Appspiriment.typography.textMediumLarge.semiBold,
                    color = tintColor
                )
            }
        }
    }
}