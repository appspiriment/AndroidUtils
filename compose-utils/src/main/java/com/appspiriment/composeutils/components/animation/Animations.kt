package com.appspiriment.composeutils.components.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

private const val TIME_DURATION = 300

val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
    )
}

val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutHorizontally(
        targetOffsetX = { -it / 3 },
        animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
    )
}

object SlideInRightToLeft {
    val enter = slideInHorizontally(
        animationSpec = tween(200),
        initialOffsetX = {it}
    )
    val exit = slideOutHorizontally(
        animationSpec = tween(200),
        targetOffsetX = {-it}
    )

    val popEnter = slideInHorizontally(
        animationSpec = tween(200),
        initialOffsetX = {-it}
    )
    val popExit = slideOutHorizontally(
        animationSpec = tween(200),
        targetOffsetX = {it}
    )
}