package com.appspiriment.composeutils.components.core.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.core.FillerSpacer
import com.appspiriment.composeutils.components.core.HorizontalSpacer
import com.appspiriment.composeutils.components.core.image.AppsImage
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.uiTextResource
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.uiImageResouce
import com.appspiriment.composeutils.wrappers.uiVectorResouce
import com.appspiriment.composeutils.wrappers.toUiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.UiColor


@Composable
fun ImageText(
    text: UiText,
    modifier: Modifier = Modifier,
    color: UiColor = Appspiriment.uiColors.onMainSurface,
    style: TextStyle = Appspiriment.typography.textMedium,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    startingImage: UiImage? = null,
    trailingImage: UiImage? = null,
    startingImageHeight: Dp? = null,
    trailingImageHeight: Dp? = null,
    textModifier: Modifier = Modifier,
    iconPadding: Dp? = 8.dp,
    usePainter:Boolean = false,
    isHtml: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.apply {
            onClick?.let { clickable { it.invoke() } }
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        startingImage?.let {
            AppsImage(
                image = startingImage,
                modifier = startingImageHeight?.let { h ->
                    Modifier.height(h)
                } ?: Modifier,
                usePainter = usePainter
            )

            iconPadding?.let { iconPadding ->
                HorizontalSpacer(iconPadding)
            }
        }
        MalayalamText(
            text = text,
            color = color,
            style = style, letterSpacing = letterSpacing,
            textDecoration = textDecoration, textAlign = textAlign, lineHeight = lineHeight,
            overflow = overflow, softWrap = softWrap, maxLines = maxLines,
            onTextLayout = onTextLayout, modifier = textModifier.offset(y=1.dp), isHtml = isHtml
        )

        trailingImage?.let {

            iconPadding?.let {padding ->
                FillerSpacer(minWidth = padding)
            }
            AppsImage(
                image = it,
                modifier = trailingImageHeight?.let { h ->
                    Modifier.height(h).offset( y=(-1).dp)
                } ?: Modifier,
                usePainter = usePainter
            )
        }
    }
}

@Preview
@Composable
fun PreviewImageText() {
    Column(modifier = Modifier.background(Appspiriment.colors.background), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ImageText(
            startingImage = uiImageResouce(R.drawable.ic_action_config),
            text = uiTextResource(id = R.string.sankara_smrithi)
        )

        ImageText(
            startingImage = uiVectorResouce(R.drawable.ic_action_config),
            text = "Appspiriment Labs".toUiText()
        )

       val annotatedString = buildAnnotatedString {
            append("Appspiriment ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("Labs ")
            }
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("Sankara ")
            }
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("Smrithi")
            }
        }
        ImageText(
            startingImage = uiVectorResouce(R.drawable.ic_action_config),
            text = annotatedString.toUiText()
        )
    }
}