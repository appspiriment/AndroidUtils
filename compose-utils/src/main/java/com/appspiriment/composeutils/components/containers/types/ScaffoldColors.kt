package com.appspiriment.composeutils.components.containers.types

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.appspiriment.composeutils.theme.Appspiriment


data class ScaffoldColors (
    val backgroundColor: Color,
    val drawerBackgroundColor: Color,
    val drawerItemColor: Color,
    val scrimColor: Color,
    val topBarBackground: Color,
    val onTopBarColor: Color
){
    companion object {
        @Composable
        fun defaults(
            backgroundColor: Color = Appspiriment.colors.background,
            drawerBackgroundColor: Color = Appspiriment.colors.background,
            drawerItemColor: Color = Appspiriment.colors.drawerItem,
            topBarBackground: Color = Appspiriment.colors.topAppBar,
            onTopBarColor: Color = Appspiriment.colors.onTopAppBar,
            scrimColor: Color = Appspiriment.colors.scrimColor
        ) : ScaffoldColors = ScaffoldColors (
            backgroundColor= backgroundColor,
            drawerBackgroundColor= drawerBackgroundColor,
            drawerItemColor= drawerItemColor,
            topBarBackground = topBarBackground,
            onTopBarColor = onTopBarColor,
            scrimColor = scrimColor
        )
    }
}