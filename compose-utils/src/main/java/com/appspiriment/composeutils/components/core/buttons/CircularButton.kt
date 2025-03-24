package com.appspiriment.composeutils.components.core.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.core.image.AppsImage
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.uiImageResouce
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.UiColor


@Composable
fun CircularButton(
    icon: UiImage,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier.padding(Appspiriment.sizes.paddingXSmall),
    buttonColor: UiColor = Appspiriment.uiColors.primary,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .size(Appspiriment.sizes.floatingButtonSize)
            .background(buttonColor.asColor(LocalContext.current), shape = RoundedCornerShape(50)),
        contentAlignment = Alignment.Center
    ) {
        AppsImage(
            image = icon,
            modifier = iconModifier.clickable {
                onClick()
            },
        )
    }
}


@Preview
@Composable
fun PreviewCircularButton() {
    Column {
        CircularButton(icon = uiImageResouce(R.drawable.ic_action_config)) {}
    }
}