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
            positiveStyle: ButtonStyle = ButtonStyle.primary(),
            negativeStyle: ButtonStyle = ButtonStyle.outlined(),
            arrangement: Arrangement.Horizontal = Arrangement.SpaceAround
        ) = DialogButtonStyle(
            positiveStyle = positiveStyle,
            negativeStyle = negativeStyle,
            arrangement = arrangement
        )
    }
}