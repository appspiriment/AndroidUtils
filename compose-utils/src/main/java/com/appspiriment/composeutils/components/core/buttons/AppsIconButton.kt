package com.appspiriment.composeutils.components.core.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.UiImage


@Composable
fun AppsIconButton(
    icon: UiImage,
    modifier: Modifier = Modifier.size(Appspiriment.sizes.actionButtonSize),
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    iconModifier: Modifier = Modifier,
    iconHeight: Dp? = Appspiriment.sizes.actionButtonSize,
    onClick: () -> Unit
) {
    val iconTint = icon.tint?.asColor(LocalContext.current) ?: LocalContentColor.current
    val heightAdjustedIconModifier = iconHeight?.let{iconModifier.height(iconHeight)} ?: modifier
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    ) {
        icon.getImageVector()?.let {
            Icon(
                imageVector = it,
                contentDescription = icon.description,
                tint = iconTint,
                modifier = heightAdjustedIconModifier
            )
        } ?: Icon(
            painter = icon.getPainter(),
            contentDescription = icon.description,
            tint = iconTint,
            modifier = heightAdjustedIconModifier
        )
    }
}

