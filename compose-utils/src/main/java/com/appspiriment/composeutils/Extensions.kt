package com.appspiriment.composeutils

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


@Composable
@ReadOnlyComposable
fun textSizeResource(@DimenRes id: Int): TextUnit {
   return dimensionResource(id = id).value.sp
}