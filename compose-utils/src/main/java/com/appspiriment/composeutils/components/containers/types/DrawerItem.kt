package com.appspiriment.composeutils.components.containers.types

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.appspiriment.composeutils.components.containers.DrawerIdentifier
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.theme.Appspiriment

data class DrawerItem(
    val menuTitle: UiText,
    val icon: UiImage? = null,
    val trailingIcon: UiImage? = null,
    val showTopDivider: Boolean = false,
    val showBottomDivider: Boolean = false,
    val closeDrawer: Boolean = true,
    val textStyle: TextStyle? = null,
    val verticalPadding: Dp? = null,
    val drawerIdentifier: () -> DrawerIdentifier,
) {
    companion object {
        @Composable
        fun <T>from(
            menuTitle: UiText,
            icon: UiImage? = null,
            trailingIcon: UiImage? = null,
            showTopDivider: Boolean = false,
            showBottomDivider: Boolean = false,
            closeDrawer: Boolean = true,
            textStyle: TextStyle = Appspiriment.typography.textMedium,
            verticalPadding: Dp = Appspiriment.sizes.paddingSmallMedium,
            drawerIdentifier: () -> DrawerIdentifier,
        ) = DrawerItem(
            menuTitle = menuTitle,
            icon = icon,
            trailingIcon = trailingIcon,
            showTopDivider = showTopDivider,
            showBottomDivider = showBottomDivider,
            closeDrawer = closeDrawer,
            textStyle = textStyle,
            verticalPadding = verticalPadding,
            drawerIdentifier = drawerIdentifier
        )
    }
}
