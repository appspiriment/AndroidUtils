package com.appspiriment.composeutils.components.core.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.core.buttons.types.ButtonStyle
import com.appspiriment.composeutils.components.core.image.AppsImage
import com.appspiriment.composeutils.components.core.image.types.UiImage
import com.appspiriment.composeutils.components.core.image.types.uiImageResouce
import com.appspiriment.composeutils.components.core.text.MalayalamText
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.components.core.text.types.toUiText
import com.appspiriment.composeutils.components.core.text.types.uiTextResource
import com.appspiriment.composeutils.theme.Appspiriment


@Composable
fun IconButton(
    startIcon: UiImage?,
    trailingIcon: UiImage? = null,
    text: UiText,
    modifier: Modifier = Modifier,
    iconPadding: Dp = Appspiriment.sizes.paddingXXXXLarge,
    textModifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.primaryPositive(),
    onClick: () -> Unit
) {

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonStyle.buttonColor,
        ),
        modifier = modifier.border(
            width = 2.dp,
            color = buttonStyle.strokeColor,
        ),
        shape = buttonStyle.buttonShape,
        onClick = { onClick.invoke() }
    ) {

        startIcon?.let {
            AppsImage(image = it, modifier = Modifier.padding(end = iconPadding))
        }

        MalayalamText(
            text = text,
            style = buttonStyle.textStyle,
            textAlign = TextAlign.Center,
            modifier = textModifier
        )
        trailingIcon?.let {
            AppsImage(image = it, modifier = Modifier.padding(start = iconPadding))
        }

    }
}


@Preview
@Composable
fun PreviewIconButton(){
    Column {
        IconButton(startIcon = uiImageResouce(R.drawable.ic_action_config), text = uiTextResource(R.string.sankara_smrithi)) {}
        IconButton(
            startIcon = null,
            trailingIcon = uiImageResouce(R.drawable.ic_action_config),
            text = uiTextResource(R.string.sankara_smrithi),
            iconPadding = 24.dp
        ) {}
    }
}