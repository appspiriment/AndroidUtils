package com.appspiriment.composeutils.components.animation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

/**
 * A convenience wrapper around [composable] that wires up all four transition lambdas
 * from a [NavTransition] in a single call.
 *
 * ### Basic usage — default slide-from-right
 * ```kotlin
 * NavHost(startDestination = HomeRoute) {
 *     animatedComposable<HomeRoute>     { HomeScreen() }
 *     animatedComposable<DetailRoute>   { DetailScreen() }
 *     animatedComposable<ProfileRoute>  { ProfileScreen() }
 * }
 * ```
 *
 * ### Per-screen transition override
 * ```kotlin
 * NavHost(startDestination = HomeRoute) {
 *     animatedComposable<HomeRoute>    { HomeScreen() }
 *
 *     // Full-screen modal rises from the bottom
 *     animatedComposable<AddItemRoute>(NavTransitions.slideFromBottom()) { AddItemScreen() }
 *
 *     // Settings feels like a dialog overlay
 *     animatedComposable<SettingsRoute>(NavTransitions.scaleAndFade()) { SettingsScreen() }
 *
 *     // Instant switch — no animation
 *     animatedComposable<SplashRoute>(NavTransitions.none()) { SplashScreen() }
 * }
 * ```
 *
 * ### Setting a different global default on NavHost
 * If you want every destination to fade by default, configure [NavHost] directly:
 * ```kotlin
 * val t = NavTransitions.fade()
 * NavHost(
 *     startDestination   = HomeRoute,
 *     enterTransition    = t.enter,
 *     exitTransition     = t.exit,
 *     popEnterTransition = t.popEnter,
 *     popExitTransition  = t.popExit,
 * ) {
 *     // individual destinations can still override with animatedComposable(transition = …)
 * }
 * ```
 *
 * @param T          The route type. Must be `@Serializable` and registered as a nav route.
 * @param transition The [NavTransition] to apply. Defaults to [NavTransitions.Default]
 *                   ([NavTransitions.slideFromRight]).
 * @param content    The screen composable, receiving the [NavBackStackEntry].
 */
inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    transition: NavTransition = NavTransitions.Default,
    noinline content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition    = transition.enter,
        exitTransition     = transition.exit,
        popEnterTransition = transition.popEnter,
        popExitTransition  = transition.popExit,
    ) { backStackEntry ->
        content(backStackEntry)
    }
}
