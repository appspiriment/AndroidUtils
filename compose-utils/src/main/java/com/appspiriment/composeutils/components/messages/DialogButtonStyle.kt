package com.appspiriment.composeutils.components.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import com.appspiriment.composeutils.components.core.buttons.types.ButtonStyle


data class DialogButtonStyle(
    val positiveStyle: ButtonStyle,
    val negativeStyle: ButtonStyle,
    val arrangement: Arrangement.Horizontal = Arrangement.SpaceAround
) {
    companion object {
        @Composable
        fun primary(
            positiveStyle: ButtonStyle = ButtonStyle.primaryPositive(),
            negativeStyle: ButtonStyle = ButtonStyle.primaryNegative(),
            arrangement: Arrangement.Horizontal = Arrangement.SpaceAround
        ) = DialogButtonStyle(
            positiveStyle = positiveStyle,
            negativeStyle = negativeStyle,
            arrangement = arrangement
        )
        @Composable
        fun transparent(
            positiveStyle: ButtonStyle = ButtonStyle.transparentPositive(),
            negativeStyle: ButtonStyle = ButtonStyle.transparentNegative(),
            arrangement: Arrangement.Horizontal = Arrangement.SpaceAround
        ) = DialogButtonStyle(
            positiveStyle = positiveStyle,
            negativeStyle = negativeStyle,
            arrangement = arrangement
        )
    }
}