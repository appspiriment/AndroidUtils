package com.appspiriment.composeutils.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.appspiriment.composeutils.components.core.buttons.AppsButton
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.semiBold

@Composable
fun MessageDialog(
    modifier: Modifier = Modifier,
    title: UiText? = null,
    message: UiText? = null,
    titleStyle: TextStyle = Appspiriment.typography.textLarge.semiBold,
    messageStyle: TextStyle = Appspiriment.typography.textMedium,
    titleAlign: TextAlign = TextAlign.Center,
    messageAlign: TextAlign = TextAlign.Center,
    messageContent: (@Composable (UiText) -> Unit)? = null,
    positiveText: UiText? = UiText.DynamicString("OK"),
    negativeText: UiText? = null,
    buttonStyle: DialogButtonStyle = DialogButtonStyle.primary(),
    listener: (Boolean) -> Unit = {},
    cancellable: Boolean = true,
    dialogBackground: Color = Appspiriment.colors.mainSurface,
    onDismissRequest: () -> Unit,
    dialogProperties: DialogProperties = DialogProperties(),
    customviewContent: (@Composable () -> Unit)? = null
) {
    Dialog(
        onDismissRequest = {
            if(cancellable) onDismissRequest()
        },
        properties = dialogProperties
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(
                    color = dialogBackground,
                    shape = RoundedCornerShape(12.dp)
                )
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            title?.let {
                AppspirimentText(
                    text = it,
                    style = titleStyle,
                    textAlign = titleAlign,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            message?.let {
                messageContent?.invoke(it) ?: AppspirimentText(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    style = messageStyle,
                    textAlign = messageAlign,
                )
            }

            customviewContent?.invoke()

            Row(
                horizontalArrangement = buttonStyle.arrangement,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            ) {
                negativeText?.let {
                    AppsButton(
                        text = it,
                        buttonStyle = buttonStyle.negativeStyle,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .defaultMinSize(minWidth = 96.dp)
                    ) {
                        onDismissRequest()
                        listener.invoke(false)
                    }
                }
                positiveText?.let {
                    AppsButton(
                        text = it,
                        buttonStyle = buttonStyle.positiveStyle,
                        modifier = Modifier.defaultMinSize(minWidth = 96.dp)
                    ) {
                        onDismissRequest()
                        listener.invoke(true)
                    }
                }
            }
        }
    }
}
