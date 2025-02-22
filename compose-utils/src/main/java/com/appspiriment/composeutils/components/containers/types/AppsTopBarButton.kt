package com.appspiriment.composeutils.components.containers.types

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.appspiriment.composeutils.components.core.image.types.UiImage

data class AppsTopBarButton(
    val icon: UiImage,
    val modifier: Modifier = Modifier,
    val tint: Color = Color.Gray,
    val onClick: () -> Unit,
)
