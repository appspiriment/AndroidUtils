package com.appspiriment.composeutils.components.containers

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.image.AppsIcon
import com.appspiriment.composeutils.components.containers.types.AppsTopBarButton
import com.appspiriment.composeutils.components.core.text.MalayalamText
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.theme.Appspiriment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navMode: NavigationMode = NavigationMode.EMPTY,
    navIconClick: (() -> Unit)? = null,
    @DrawableRes brandLogoId: Int?,
    title: UiText?,
    background: Color = Appspiriment.colors.topAppBar,
    onTopBarColor: Color = Appspiriment.colors.onTopAppBar,
    actions: List<AppsTopBarButton>? = null,
    actionsContent: @Composable RowScope.(Color) -> Unit = {}
) {

    TopAppBar(
        navigationIcon = {
            if (navMode != NavigationMode.EMPTY) {
                Icon(imageVector = navMode.icon,
                    contentDescription = "Back",
                    tint = onTopBarColor,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable { navIconClick?.invoke() })
            }
        },
        title = { AppBarTitleImage(
            brandLogoId, title, onTopBarColor
        ) },
        colors = TopAppBarColors(
            containerColor = background,
            scrolledContainerColor = background,
            navigationIconContentColor = onTopBarColor,
            titleContentColor = onTopBarColor,
            actionIconContentColor = onTopBarColor
        ),
        actions = {
            actions?.forEach { btn ->
                AppsIcon(
                    icon = btn.icon.setTint(tint = onTopBarColor),
                    modifier = btn.modifier
                        .padding(end = 4.dp)
                        .size(Appspiriment.sizes.iconLarge)
                        .clickable { btn.onClick.invoke() }
                        .padding(12.dp),
                )
            }
            actionsContent(onTopBarColor)
        }
    )
}


@Composable
fun AppBarTitleImage(
    @DrawableRes logoResId: Int?,
    title: UiText?,
    tintColor: Color = Appspiriment.colors.onTopAppBar,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, top = 6.dp, bottom = 6.dp)
    ) {
        logoResId?.let {
            Image(painter = painterResource(id = it),
                contentDescription = "",
                modifier = Modifier.wrapContentWidth()
            )
        } ?: title?.let {
            MalayalamText(
                text = title,
                style = Appspiriment.typography.textMediumLargeSemiBold.copy(color = tintColor)
            )
        }
    }
}