package com.appspiriment.composeutils.components.containers.types

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.appspiriment.composeutils.wrappers.UiColor
import com.appspiriment.composeutils.wrappers.UiImage

data class AppsTopBarButton(
    val icon: UiImage,
    val modifier: Modifier = Modifier,
    val tint: UiColor = UiColor.DynamicColor(Color.Gray),
    val onClick: () -> Unit,
)
