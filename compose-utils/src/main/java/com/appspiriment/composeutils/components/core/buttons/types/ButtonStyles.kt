package com.appspiriment.composeutils.components.core.buttons.types

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.buttons.AppsButton
import com.appspiriment.composeutils.wrappers.toUiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.UiColor
import com.appspiriment.composeutils.wrappers.toUiColor


data class ButtonStyle(
    val textStyle : TextStyle,
    val buttonColor: UiColor,
    val buttonPressedColor: UiColor = buttonColor,
    val textColor: UiColor,
    val strokeColor: UiColor,
    val buttonShape: Shape,
) {
    companion object {
        @Composable
        fun primary(
            textStyle : TextStyle = Appspiriment.typography.textMedium,
            buttonColor: UiColor = Appspiriment.uiColors.primary,
            buttonPressedColor: UiColor = Appspiriment.uiColors.primary,
            textColor: UiColor = Appspiriment.uiColors.onPrimary,
            strokeColor: UiColor = UiColor.Transparent,
            buttonShape: Shape = CircleShape,
        ): ButtonStyle {
            return ButtonStyle(
                buttonColor = buttonColor,
                textColor = textColor,
                textStyle = textStyle,
                buttonPressedColor = buttonPressedColor,
                strokeColor = strokeColor,
                buttonShape = buttonShape
            )
        }
        @Composable
        fun outlined(
            textStyle : TextStyle = Appspiriment.typography.textMedium,
            buttonColor: UiColor = Color.Transparent.toUiColor(),
            buttonPressedColor: UiColor = Appspiriment.uiColors.primary,
            textColor: UiColor = Appspiriment.uiColors.primary,
            strokeColor: UiColor = Appspiriment.uiColors.primary,
            buttonShape: Shape = CircleShape,
        ): ButtonStyle {
            return ButtonStyle(
                buttonColor = buttonColor,
                buttonPressedColor = buttonPressedColor,
                textColor = textColor,
                textStyle = textStyle,
                strokeColor = strokeColor,
                buttonShape = buttonShape
            )
        }
    }
}

@Preview
@Composable
fun ButtonStylePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AppsButton(
            "Okay".toUiText(),
            buttonStyle = ButtonStyle.primary()
        ) { }

        AppsButton(
            "Okay".toUiText(),
            buttonStyle = ButtonStyle.outlined()
        ) { }
    }
}