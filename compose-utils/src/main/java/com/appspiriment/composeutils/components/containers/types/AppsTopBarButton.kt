package com.appspiriment.composeutils.components.containers.types

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.appspiriment.composeutils.wrappers.UiColor
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.withTint

data class AppsTopBarButton(
    val icon: UiImage,
    val modifier: Modifier = Modifier,
    /**
     * @deprecated This field is not read by [AppsTopBar]. The top-bar always tints icons
     * using its own [onTopBarColor] parameter via [UiImage.withTint]. To apply a tint,
     * set it on the [UiImage] directly:
     *   `icon = myIcon.withTint(myColor.toUiColor())`
     */
    @Deprecated(
        message = "Not read by AppsTopBar. Set the tint on the UiImage via icon.withTint(color.toUiColor()) instead.",
        level = DeprecationLevel.WARNING
    )
    val tint: Color = Color.Gray,
    val onClick: () -> Unit,
)
