package com.appspiriment.composeutils.components.messages

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.appspiriment.composeutils.wrappers.UiText

@Composable
fun ToastMessage(
    message: UiText,
    duration: Int = Toast.LENGTH_SHORT,
) {
    val context = LocalContext.current
    Toast.makeText(context, message.asText(context), duration).show()
}


fun Context.showToast(
    message: UiText,
    duration: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(this, message.asText(this), duration).show()
}