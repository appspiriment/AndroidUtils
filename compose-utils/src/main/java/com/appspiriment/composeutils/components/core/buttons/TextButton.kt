package com.appspiriment.composeutils.components.core.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.appspiriment.composeutils.components.core.buttons.types.ButtonStyle
import com.appspiriment.composeutils.components.core.text.types.UiText


@Composable
fun TextButton(
    text: UiText,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.primaryPositive(),
    onClick: () -> Unit
) {
    IconButton(
        startIcon = null,
        text = text,
        modifier = modifier,
        buttonStyle = buttonStyle,
        onClick = onClick
    )
}