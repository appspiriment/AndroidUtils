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


data class ButtonStyle(
    val textStyle : TextStyle,
    val buttonColor: Color,
    val buttonPressedColor: Color = buttonColor,
    val textColor: Color,
    val strokeColor: Color,
    val buttonShape: Shape,
) {
    companion object {
        @Composable
        fun primary(
            textStyle : TextStyle = Appspiriment.typography.textMedium,
            buttonColor: Color = Appspiriment.colors.primary,
            buttonPressedColor: Color = Appspiriment.colors.primary,
            textColor: Color = Appspiriment.colors.onPrimary,
            strokeColor: Color = Color.Transparent,
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
            buttonColor: Color = Color.Transparent,
            buttonPressedColor: Color = Appspiriment.colors.primary,
            textColor: Color = Appspiriment.colors.primary,
            strokeColor: Color = Appspiriment.colors.primary,
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

        @Composable
        fun transparent(
            textStyle : TextStyle = Appspiriment.typography.textMedium,
            buttonColor: Color = Color.Transparent,
            buttonPressedColor: Color = Appspiriment.colors.primary,
            textColor: Color = Appspiriment.colors.primary,
            strokeColor: Color = Color.Transparent,
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