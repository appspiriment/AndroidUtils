package com.appspiriment.composeutils.components.core.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
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
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.toUiText
import com.appspiriment.composeutils.wrappers.uiTextResource
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.UiColor


@Composable
fun AppspirimentText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = Appspiriment.typography.textMedium,
    color: UiColor = Appspiriment.uiColors.onMainSurface,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = text,
        modifier = modifier.offset(y = 1.dp),
        style = style,
        letterSpacing = letterSpacing,
        color = color.asColor(LocalContext.current),
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}

@Composable
fun AppspirimentText(
    text: UiText,
    modifier: Modifier = Modifier,
    style: TextStyle = Appspiriment.typography.textMedium,
    color: UiColor = Appspiriment.uiColors.onMainSurface,
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
        modifier = modifier.offset(y = 1.dp),
        style = style,
        letterSpacing = letterSpacing,
        color = color.asColor(LocalContext.current),
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
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
        AppspirimentText(text = uiTextResource(id = R.string.sankara_smrithi))

        AppspirimentText(text = "Appspiriment Labs ആപ്സ്പിരിമെന്റ് ലാബ്സ്".toUiText())

        val annotatedString = buildAnnotatedString {
            append("Appspiriment ആപ്സ്പിരിമെന്റ് ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("Labs ലാബ്സ്\n")
            }
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("Sankara ശങ്കര ")
            }
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("Smrithi സ്മൃതി")
            }
        }
        AppspirimentText(text = annotatedString.toUiText())
    }
}