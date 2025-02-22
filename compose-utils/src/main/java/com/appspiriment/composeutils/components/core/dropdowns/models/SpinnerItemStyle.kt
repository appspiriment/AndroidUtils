package com.appspiriment.composeutils.components.core.dropdowns.models

import android.graphics.drawable.shapes.Shape
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appspiriment.composeutils.components.core.image.types.UiImage
import com.appspiriment.composeutils.theme.Appspiriment


data class SpinnerStyle(
    val background: Color,
    val textColor: Color,
    val modifier: Modifier = Modifier.fillMaxWidth(),
    val leadingIcon: UiImage? = null,
    val trailingIcon: UiImage? = null,
    val iconPadding: Dp = 0.dp,
    val textStyle: TextStyle,
    val textAlign: TextAlign = TextAlign.Start,
    val innerPadding: PaddingValues = PaddingValues(0.dp),
    val showBottomBorder: Boolean = false,
)

object SpinnerStyleDefaults {
    val defaultSpinner
        @Composable get() = SpinnerStyle(
            background = Appspiriment.colors.primaryCardContainer,
            textColor = Appspiriment.colors.onMainSurface,
            textStyle = Appspiriment.typography.textMedium
        )
}