package com.appspiriment.composeutils.components.core.text

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.UiColor

@Composable
fun PrefixedText(
    text: UiText,
    modifier: Modifier = Modifier,
    prefix: UiText? = null,
    color: UiColor = Appspiriment.uiColors.onMainSurface,
    prefixColor: UiColor = Appspiriment.uiColors.onMainSurface,
    textStyle: TextStyle = Appspiriment.typography.textMedium,
    prefixStyle: TextStyle = Appspiriment.typography.textMedium,
    prefixPadding: Dp = Appspiriment.sizes.paddingSmall,
    prefixModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
) {

    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        prefix?.let {
            (prefixStyle).run {
                AppspirimentText(
                    text = it,
                    color = prefixColor,
                    style = prefixStyle,
                    modifier = prefixModifier.padding(end = prefixPadding)
                )
            }
        }
        textStyle.run {
            AppspirimentText(
                text = text,
                color = color,
                style = textStyle,
                modifier = textModifier
            )
        }
    }
}

