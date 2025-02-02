package com.appspiriment.composeutils.components.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable



inline fun <reified T : Any>  NavGraphBuilder.animatedComposable(
    noinline content: @Composable (NavBackStackEntry) -> Unit
) {
    composable<T> { backStackEntry ->
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(backStackEntry) {
            visible = true
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(initialOffsetX = { -600 }), // Slide in from left
            exit = slideOutHorizontally(targetOffsetX = { 600 }) // Slide out to right
        ) {
            content(backStackEntry)
        }
    }
}