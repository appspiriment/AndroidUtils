package com.appspiriment.composeutils.components.core.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.image.AppsIcon
import com.appspiriment.composeutils.components.core.image.types.UiImage
import com.appspiriment.composeutils.components.core.image.types.uiImageResouce
import com.appspiriment.composeutils.components.core.image.types.uiVectorResouce
import com.appspiriment.composeutils.theme.Appspiriment


@Composable
fun AppsImage(
    image: UiImage,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    usePainter: Boolean = true
){
    if(usePainter || image.getImageVector() == null) {
        Image(
            painter = image.getPainter(),
            modifier = modifier,
            contentDescription = image.description,
            alignment = alignment, contentScale = contentScale, alpha = alpha,
            colorFilter = image.tint?.let { ColorFilter.tint(it) } ?: colorFilter
        )
    } else AppsIcon(
        icon = image,
        modifier = modifier
    )
}


@Preview
@Composable
fun PreviewSsImage() {
    Column(
        modifier = Modifier.background(Appspiriment.colors.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppsImage(
            image = uiVectorResouce(R.drawable.ic_arrow_up),
            modifier = Modifier.height(88.dp)
        )
        AppsImage(
            image = uiImageResouce(R.drawable.ic_action_config, tint = Color.Red),
        )
    }
}