package com.appspiriment.composeutils.components.core.buttons.types

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.wrappers.toUiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.UiColor


data class ButtonStyle(
    val textStyle : TextStyle,
    val buttonColor: UiColor,
    val textColor: UiColor,
    val strokeColor: UiColor,
    val buttonShape: Shape,
) {
    companion object {
        @Composable
        fun primaryPositive(
            textStyle : TextStyle = Appspiriment.typography.textLarge,
            buttonColor: UiColor = Appspiriment.uiColors.primary,
            textColor: UiColor = Appspiriment.uiColors.onPrimary,
            strokeColor: UiColor = UiColor.DynamicColor.Transparent,
            buttonShape: Shape = RoundedCornerShape(4.dp),
        ): ButtonStyle {
            return ButtonStyle(
                buttonColor = buttonColor,
                textColor = textColor,
                textStyle = textStyle,
                strokeColor = strokeColor,
                buttonShape = buttonShape
            )
        }

        @Composable
        fun primaryNegative(
            textStyle : TextStyle = Appspiriment.typography.textLarge,
            textColor: UiColor = Appspiriment.uiColors.primary,
            buttonColor: UiColor = UiColor.DynamicColor.Transparent,
            strokeColor: UiColor = Appspiriment.uiColors.primary,
            buttonShape: Shape = RoundedCornerShape(4.dp),
        ): ButtonStyle {
            return ButtonStyle(
                buttonColor = buttonColor,
                textColor = textColor,
                textStyle = textStyle,
                strokeColor = strokeColor,
                buttonShape = buttonShape
            )
        }

        @Composable
        fun transparentPositive(
            textStyle : TextStyle = Appspiriment.typography.textLarge,
            textColor: UiColor = Appspiriment.uiColors.primary,
            buttonColor: UiColor = UiColor.DynamicColor.Transparent,
            strokeColor: UiColor = Appspiriment.uiColors.primary,
            buttonShape: Shape = RoundedCornerShape(4.dp),
        ): ButtonStyle {
            return ButtonStyle(
                buttonColor = buttonColor,
                textColor = textColor,
                textStyle = textStyle,
                strokeColor = strokeColor,
                buttonShape = buttonShape
            )
        }

        @Composable
        fun transparentNegative(
            textStyle : TextStyle = Appspiriment.typography.textLarge,
            textColor: UiColor = Appspiriment.uiColors.onPrimary,
            buttonColor: UiColor = UiColor.DynamicColor.Transparent,
            strokeColor: UiColor = Appspiriment.uiColors.primary,
            buttonShape: Shape = RoundedCornerShape(4.dp),
        ): ButtonStyle {
            return ButtonStyle(
                buttonColor = buttonColor,
                textColor = textColor,
                textStyle = textStyle,
                strokeColor = strokeColor,
                buttonShape = buttonShape
            )
        }

        @Composable
        fun plain(
            textStyle : TextStyle = Appspiriment.typography.textMediumLarge,
            textColor: UiColor = Appspiriment.uiColors.onPrimary,
            buttonColor: UiColor = UiColor.DynamicColor.Transparent,
            strokeColor: UiColor = UiColor.DynamicColor.Transparent,
            buttonShape: Shape = RoundedCornerShape(4.dp),
        ): ButtonStyle {
            return ButtonStyle(
                buttonColor = buttonColor,
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
        com.appspiriment.composeutils.components.core.buttons.TextButton(
            "Okay".toUiText(),
            buttonStyle = ButtonStyle.primaryPositive()
        ) { }
        com.appspiriment.composeutils.components.core.buttons.TextButton(
            "Okay".toUiText(),
            buttonStyle = ButtonStyle.primaryNegative()
        ) { }
        com.appspiriment.composeutils.components.core.buttons.TextButton(
            "Okay".toUiText(),
            buttonStyle = ButtonStyle.transparentPositive()
        ) { }
        com.appspiriment.composeutils.components.core.buttons.TextButton(
            "Okay".toUiText(),
            buttonStyle = ButtonStyle.transparentNegative()
        ) { }
    }
}