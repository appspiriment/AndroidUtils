package com.appspiriment.composeutils.components.core.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.uiImageResource
import com.appspiriment.composeutils.wrappers.uiVectorResource
import com.appspiriment.composeutils.wrappers.toUiColor
import com.appspiriment.composeutils.theme.Appspiriment


@Composable
fun AppsImage(
    image: UiImage,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    // false = auto-route vectors through AppsIcon (ImageVector) and bitmaps through Painter.
    // Set true to force the Painter path for all image types.
    usePainter: Boolean = false,
) {
    // isVectorBased() is a non-composable O(1) check — no resource loading.
    // When the vector path is taken, AppsIcon calls getImageVector() exactly once internally.
    if (usePainter || !image.isVectorBased()) {
        Image(
            painter = image.getPainter(),
            modifier = modifier,
            contentDescription = image.description,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = image.tint?.asColor()?.let { ColorFilter.tint(it) } ?: colorFilter,
        )
    } else {
        AppsIcon(icon = image, modifier = modifier)
    }
}


@Preview
@Composable
fun PreviewSsImage() {
    Column(
        modifier = Modifier.background(Appspiriment.colors.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppsImage(
            image = uiVectorResource(R.drawable.ic_arrow_up),
            modifier = Modifier.height(88.dp)
        )
        AppsImage(
            image = uiImageResource(R.drawable.ic_action_config, tint = Appspiriment.colors.error.toUiColor()),
        )
    }
}