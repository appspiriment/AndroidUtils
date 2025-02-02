package com.appspiriment.composeutils.components.core.text

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.components.core.text.types.toUiText
import com.appspiriment.composeutils.theme.Appspiriment

@Composable
fun PrefixedText(
    text: UiText,
    modifier: Modifier = Modifier,
    prefix: UiText? = null,
    textStyle: TextStyle = Appspiriment.typography.textNormal,
    prefixStyle: TextStyle = Appspiriment.typography.textMedium,
    prefixPadding: Dp = Appspiriment.sizes.paddingSmall,
    prefixModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
) {

    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        prefix?.let {
            (prefixStyle).run {
                MalayalamText(
                    text = it,
                    style = prefixStyle,
                    modifier = prefixModifier.padding(end = prefixPadding)
                )
            }
        }
        textStyle.run {
            MalayalamText(
                text = text,
                style = textStyle,
                modifier = textModifier
            )
        }
    }
}

