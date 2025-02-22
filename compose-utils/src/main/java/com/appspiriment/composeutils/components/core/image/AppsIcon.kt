package com.appspiriment.composeutils.components.core.image

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.appspiriment.composeutils.components.core.image.types.UiImage
import com.appspiriment.composeutils.theme.Appspiriment

@Composable
fun AppsIcon(
    icon: UiImage,
    modifier: Modifier = Modifier,
    iconHeight: Dp = Appspiriment.sizes.iconStandard,
) {
    icon.getImageVector()?.let {
        Icon(
            imageVector = it,
            contentDescription = icon.description,
            tint = icon.tint ?: LocalContentColor.current,
            modifier = modifier.height(iconHeight)
        )
    } ?: Icon(
        painter = icon.getPainter(),
        contentDescription = icon.description,
        tint = icon.tint ?: LocalContentColor.current,
        modifier = modifier.height(iconHeight)
    )
}