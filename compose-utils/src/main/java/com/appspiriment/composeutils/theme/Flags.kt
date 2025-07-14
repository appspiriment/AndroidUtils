package com.appspiriment.composeutils.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class BaseFlags(
    val isNotoFont: Boolean = false
)
val LocalFlags  by lazy { staticCompositionLocalOf { BaseFlags() } }
