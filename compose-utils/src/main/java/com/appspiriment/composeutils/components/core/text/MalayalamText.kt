package com.appspiriment.composeutils.components.core.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.components.core.text.types.toUiText
import com.appspiriment.composeutils.components.core.text.types.uiTextResource
import com.appspiriment.composeutils.theme.Appspiriment

@Composable
fun MalayalamText(
    text: UiText,
    modifier: Modifier = Modifier,
    style: TextStyle = Appspiriment.typography.textNormal,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    isHtml: Boolean = false,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = text.asAnnotatedString(LocalContext.current, isHtml),
        modifier = modifier, style = style, letterSpacing = letterSpacing,
        textDecoration = textDecoration, textAlign = textAlign, lineHeight = lineHeight,
        overflow = overflow, softWrap = softWrap, maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}


@Preview
@Composable
fun PreviewMalayalamText() {
    Column(
        modifier = Modifier.background(Appspiriment.colors.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MalayalamText(text = uiTextResource(id = R.string.sankara_smrithi))

        MalayalamText(text = "Arun Shankar അരുൺ ശങ്കർ".toUiText())

        val annotatedString = buildAnnotatedString {
            append("Arun അരുൺ ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("Shankar ശങ്കർ\n")
            }
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("Sankara ശങ്കര ")
            }
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("Smrithi സ്മൃതി")
            }
        }
        MalayalamText(text = annotatedString.toUiText())
    }
}