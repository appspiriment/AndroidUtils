package com.appspiriment.composeutils.components.core.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.appspiriment.composeutils.components.core.buttons.types.ButtonStyle
import com.appspiriment.composeutils.wrappers.UiText


@Composable
fun AppsButton(
    text: UiText,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonStyle: ButtonStyle = ButtonStyle.primary(),
    onClick: () -> Unit
) {
    AppsImageButton(
        icon = null,
        text = text,
        modifier = modifier,
        textModifier = textModifier,
        enabled = enabled,
        buttonStyle = buttonStyle,
        onClick = onClick
    )
}