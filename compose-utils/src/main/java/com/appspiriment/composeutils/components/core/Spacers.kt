package com.appspiriment.composeutils.components.core

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.appspiriment.composeutils.theme.Appspiriment

@Composable
fun VerticalSpacer(height: Dp = Appspiriment.sizes.paddingMedium){
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun HorizontalSpacer(width: Dp  = Appspiriment.sizes.paddingMedium){
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun ColumnScope.FillerSpacer(){
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
fun RowScope.FillerSpacer(){
    Spacer(modifier = Modifier.weight(1f))
}