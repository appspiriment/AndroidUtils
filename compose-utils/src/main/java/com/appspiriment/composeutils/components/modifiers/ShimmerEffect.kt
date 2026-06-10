package com.appspiriment.composeutils.components.modifiers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Applies an animated shimmer loading effect. Colors are derived from [MaterialTheme]
 * so the effect adapts correctly to both light and dark themes.
 *
 * @param highlightColor  The bright sweep colour. Defaults to the surface container color.
 * @param baseColor       The dimmer base colour. Defaults to a semi-transparent version of
 *                        [highlightColor].
 */
@Composable
fun Modifier.shimmerEffect(
    highlightColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    baseColor: Color = highlightColor.copy(alpha = 0.3f),
): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "shimmer_offset"
    )

    val shimmerColors = listOf(baseColor, highlightColor, baseColor)

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    return this.background(brush)
}