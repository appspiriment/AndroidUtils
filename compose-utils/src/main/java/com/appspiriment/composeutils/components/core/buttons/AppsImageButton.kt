package com.appspiriment.composeutils.components.core.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.core.buttons.types.ButtonStyle
import com.appspiriment.composeutils.components.core.image.AppsImage
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.uiImageResouce
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.uiTextResource
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.Appspiriment.sizes


@Composable
fun AppsImageButton(
    icon: UiImage?,
    iconPosition: IconPosition = IconPosition.Start,
    text: UiText,
    modifier: Modifier = Modifier,
    iconPadding: Dp = Appspiriment.sizes.paddingXXXXLarge,
    textModifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.primary(),
    contentPadding: PaddingValues = PaddingValues(horizontal = sizes.paddingMedium, vertical = sizes.noPadding),
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = if(isPressed) buttonStyle.buttonPressedColor else buttonStyle.buttonColor
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor.asColor(context),
            contentColor = buttonStyle.textColor.asColor(context),
        ),
        modifier = modifier.border(
            width = 2.dp,
            color = buttonStyle.strokeColor.asColor(context),
            shape = buttonStyle.buttonShape
        ),
        contentPadding = contentPadding,
        shape = buttonStyle.buttonShape,
        onClick = { onClick.invoke() }
    ) {

        icon?.takeIf { iconPosition == IconPosition.Start }?.let {
            AppsImage(image = it, modifier = Modifier.padding(end = iconPadding))
        }

        AppspirimentText(
            text = text,
            style = buttonStyle.textStyle,
            color = buttonStyle.textColor,
            textAlign = TextAlign.Center,
            modifier = textModifier
        )
        icon?.takeIf { iconPosition == IconPosition.End }?.let {
            AppsImage(image = it, modifier = Modifier.padding(start = iconPadding))
        }

    }
}

sealed interface IconPosition{
    data object Start: IconPosition
    data object End: IconPosition
}

@Preview
@Composable
fun PreviewIconButton(){
    Column {
        AppsImageButton(icon = uiImageResouce(R.drawable.ic_action_config), text = uiTextResource(R.string.sankara_smrithi)) {}
        AppsImageButton(
            icon  = uiImageResouce(R.drawable.ic_action_config),
            iconPosition = IconPosition.End,
            text = uiTextResource(R.string.sankara_smrithi),
            iconPadding = 24.dp
        ) {}

        AppsImageButton(
            icon  = uiImageResouce(R.drawable.ic_action_config),
            iconPosition = IconPosition.End,
            text = uiTextResource(R.string.sankara_smrithi),
            iconPadding = 24.dp,
            buttonStyle = ButtonStyle.outlined()
        ) {}
    }
}