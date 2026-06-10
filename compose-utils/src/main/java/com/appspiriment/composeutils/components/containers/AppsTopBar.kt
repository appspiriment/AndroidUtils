package com.appspiriment.composeutils.components.containers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.containers.types.AppBarTitle
import com.appspiriment.composeutils.components.containers.types.AppsTopBarButton
import com.appspiriment.composeutils.components.containers.types.NavigationMode
import com.appspiriment.composeutils.components.core.HorizontalSpacer
import com.appspiriment.composeutils.components.core.buttons.AppsIconButton
import com.appspiriment.composeutils.components.core.image.AppsIcon
import com.appspiriment.composeutils.components.core.image.AppsImage
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.Appspiriment.sizes
import com.appspiriment.composeutils.theme.noPadding
import com.appspiriment.composeutils.theme.semiBold
import com.appspiriment.composeutils.wrappers.toUiColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppsTopBar(
    navMode: NavigationMode = NavigationMode.EMPTY,
    navIconClick: (() -> Unit)? = null,
    appBarTitle: AppBarTitle?,
    background: Color = Appspiriment.colors.topAppBar,
    onTopBarColor: Color = Appspiriment.colors.onTopAppBar,
    actions: List<AppsTopBarButton>? = null,
    actionsContent: @Composable RowScope.(Color) -> Unit = {},
) {
    val contentColor = onTopBarColor
    TopAppBar(
        navigationIcon = {
            navMode.icon?.let { navIcon ->
                AppsIconButton(
                    icon = navIcon,
                    iconModifier = Modifier.size(sizes.iconStandard),
                    onClick = { navIconClick?.invoke() },
                )
            } ?: HorizontalSpacer()
        },
        title = {
            appBarTitle?.let {
                AppBarTitleImage(
                    appBarTitle = it,
                    tintColor = onTopBarColor,
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = background,
            scrolledContainerColor = background,
            navigationIconContentColor = contentColor,
            titleContentColor = contentColor,
            actionIconContentColor = contentColor
        ),
        actions = {
            actions?.forEach { btn ->
                AppsIcon(
                    icon = btn.icon.withTint(tint = onTopBarColor.toUiColor()),
                    modifier = btn.modifier
                        .padding(end = 4.dp)
                        .size(sizes.actionButtonSize)
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
    tintColor: Color = Appspiriment.colors.onTopAppBar,
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
                AppspirimentText(
                    text = appBarTitle.title,
                    style = Appspiriment.typography.textMediumLarge.semiBold,
                    color = tintColor
                )
            }

            is AppBarTitle.ScreenTitleWithIcon -> {
                AppsIcon(
                    icon = appBarTitle.icon,
                    modifier = Modifier
                        .size(appBarTitle.iconHeight)
                        .padding(end = appBarTitle.iconPadding),

                    iconHeight = null
                )
                Column(verticalArrangement = Arrangement.spacedBy(sizes.paddingXXSmall)) {
                    AppspirimentText(
                        text = appBarTitle.title,
                        style = appBarTitle.titleStyle?:Appspiriment.typography.textMediumLarge.semiBold.noPadding,
                        color = tintColor,
                        modifier = Modifier.offset(y=1.dp)
                    )
                    appBarTitle.subTitle?.let {
                        AppspirimentText(
                            text = it,
                            style = appBarTitle.subTitleStyle
                                ?: Appspiriment.typography.textSmall.noPadding,
                            color = tintColor,
                            modifier = Modifier.offset(y = 1.dp)
                        )
                    }
                }

            }

            AppBarTitle.None -> {}
        }
    }
}