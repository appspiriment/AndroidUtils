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
import com.appspiriment.composeutils.components.core.text.types.toUiText
import com.appspiriment.composeutils.theme.Appspiriment


data class ButtonStyle(
    var textStyle : TextStyle,
    var buttonColor: Color,
    var strokeColor: Color = Color.Transparent,
    var buttonShape: Shape = RoundedCornerShape(4.dp),
) {
    companion object {
        @Composable
        fun primaryPositive(): ButtonStyle {
            return ButtonStyle(
                buttonColor = Appspiriment.colors.primary,
                textStyle = Appspiriment.typography.textLarge,
            )
        }

        @Composable
        fun primaryNegative(): ButtonStyle {
            return ButtonStyle(
                buttonColor = Color.Transparent,
                textStyle = Appspiriment.typography.textLarge.copy(color = Appspiriment.colors.primary),
                strokeColor = Appspiriment.colors.primary
            )
        }

        @Composable
        fun transparentPositive(): ButtonStyle {
            return ButtonStyle(
                buttonColor = Color.Transparent,
                textStyle = Appspiriment.typography.textLarge.copy(color = Appspiriment.colors.primary),
            )
        }

        @Composable
        fun transparentNegative(): ButtonStyle {
            return ButtonStyle(
                buttonColor = Color.Transparent,
                textStyle = Appspiriment.typography.textLarge,
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