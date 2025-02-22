package com.appspiriment.composeutils.components.messages

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.appspiriment.composeutils.components.core.text.types.UiText

@Composable
fun ToastMessage(
    message: UiText,
    duration: Int = Toast.LENGTH_SHORT,
) {
    val context = LocalContext.current
    Toast.makeText(context, message.asText(context), duration).show()
}