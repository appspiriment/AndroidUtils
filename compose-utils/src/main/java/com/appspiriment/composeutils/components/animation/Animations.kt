package com.appspiriment.composeutils.components.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavBackStackEntry

// ── Duration constant ─────────────────────────────────────────────────────────

/** Default animation duration used by all preset transitions (milliseconds). */
const val NAV_TRANSITION_DURATION = 300

private val navEasing = LinearOutSlowInEasing

// ── NavTransition ─────────────────────────────────────────────────────────────

/**
 * Bundles all four navigation transition lambdas into a single ergonomic object.
 *
 * Pass directly to [NavHost] for a global default, or to individual [animatedComposable]
 * destinations to override per-screen:
 *
 * ```kotlin
 * // Global default on NavHost
 * val t = NavTransitions.slideFromRight()
 * NavHost(
 *     enterTransition    = t.enter,
 *     exitTransition     = t.exit,
 *     popEnterTransition = t.popEnter,
 *     popExitTransition  = t.popExit,
 * ) { … }
 *
 * // Per-screen override
 * animatedComposable<SettingsRoute>(NavTransitions.scaleAndFade()) { SettingsScreen() }
 * ```
 *
 * | Property   | Fires when…                                                     |
 * |------------|-----------------------------------------------------------------|
 * | enter      | This destination is pushed onto the back stack (navigate forward) |
 * | exit       | A new destination is pushed on top of this one                  |
 * | popEnter   | This destination is revealed after the screen above it is popped |
 * | popExit    | This destination is popped off the back stack (go back)          |
 */
data class NavTransition(
    val enter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition,
    val exit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition,
    val popEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition,
    val popExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition,
)

// ── Preset factory ────────────────────────────────────────────────────────────

/**
 * Ready-made [NavTransition] factories for the most common navigation scenarios.
 *
 * | Preset          | Best for                                              |
 * |-----------------|-------------------------------------------------------|
 * | slideFromRight  | Forward push — main nav, master→detail (default)     |
 * | slideFromLeft   | RTL layouts or reverse-direction push                 |
 * | slideFromBottom | Full-screen modal / bottom-sheet destination          |
 * | slideFromTop    | Top tray, notification detail, contextual overlay     |
 * | fade            | Tab switches, peer-level screens (no hierarchy)       |
 * | scaleAndFade    | Settings overlays, dialog-like full-screen routes     |
 * | none            | Instant switch — splash → home, auth gate             |
 */
object NavTransitions {

    // ── Horizontal slides ─────────────────────────────────────────────────────

    /**
     * Standard Material-style forward push. New screen enters from the right;
     * the current screen slides slightly left (parallax depth).
     *
     * ```
     * navigate → [A  ←  B]   (B enters right, A exits left-third)
     * back     → [A  →  B]   (A returns from left-third, B exits right)
     * ```
     */
    fun slideFromRight(durationMs: Int = NAV_TRANSITION_DURATION) = NavTransition(
        enter    = { slideInHorizontally(tween(durationMs, easing = navEasing)) { it } },
        exit     = { slideOutHorizontally(tween(durationMs, easing = navEasing)) { -it / 3 } },
        popEnter = { slideInHorizontally(tween(durationMs, easing = navEasing)) { -it / 3 } },
        popExit  = { slideOutHorizontally(tween(durationMs, easing = navEasing)) { it } },
    )

    /**
     * Reverse-direction horizontal push. New screen enters from the left.
     * Useful for RTL layouts or deliberately reversed navigation flows.
     *
     * ```
     * navigate → [B  →  A]   (B enters left, A exits right-third)
     * back     → [B  ←  A]   (A returns from right-third, B exits left)
     * ```
     */
    fun slideFromLeft(durationMs: Int = NAV_TRANSITION_DURATION) = NavTransition(
        enter    = { slideInHorizontally(tween(durationMs, easing = navEasing)) { -it } },
        exit     = { slideOutHorizontally(tween(durationMs, easing = navEasing)) { it / 3 } },
        popEnter = { slideInHorizontally(tween(durationMs, easing = navEasing)) { it / 3 } },
        popExit  = { slideOutHorizontally(tween(durationMs, easing = navEasing)) { -it } },
    )

    // ── Vertical slides ───────────────────────────────────────────────────────

    /**
     * Full-screen modal style — new screen rises from the bottom.
     * The background screen fades slightly to convey layering.
     *
     * ```
     * navigate → screen slides up from bottom
     * back     → screen slides back down
     * ```
     */
    fun slideFromBottom(durationMs: Int = NAV_TRANSITION_DURATION) = NavTransition(
        enter    = { slideInVertically(tween(durationMs, easing = navEasing)) { it } },
        exit     = { fadeOut(tween(durationMs / 2)) },
        popEnter = { fadeIn(tween(durationMs / 2)) },
        popExit  = { slideOutVertically(tween(durationMs, easing = navEasing)) { it } },
    )

    /**
     * Top-tray style — new screen drops down from the top.
     * Useful for notification details, contextual overlays, or search expansion.
     *
     * ```
     * navigate → screen drops from top
     * back     → screen retracts upward
     * ```
     */
    fun slideFromTop(durationMs: Int = NAV_TRANSITION_DURATION) = NavTransition(
        enter    = { slideInVertically(tween(durationMs, easing = navEasing)) { -it } },
        exit     = { fadeOut(tween(durationMs / 2)) },
        popEnter = { fadeIn(tween(durationMs / 2)) },
        popExit  = { slideOutVertically(tween(durationMs, easing = navEasing)) { -it } },
    )

    // ── Non-directional ───────────────────────────────────────────────────────

    /**
     * Cross-fade — no implied hierarchy or direction.
     * Use for tab switches or sibling destinations at the same level.
     */
    fun fade(durationMs: Int = NAV_TRANSITION_DURATION) = NavTransition(
        enter    = { fadeIn(tween(durationMs)) },
        exit     = { fadeOut(tween(durationMs)) },
        popEnter = { fadeIn(tween(durationMs)) },
        popExit  = { fadeOut(tween(durationMs)) },
    )

    /**
     * Scale + fade — feels like a dialog appearing over the current screen.
     * Ideal for settings overlays, confirmation screens, or full-screen dialogs.
     *
     * Forward: screen scales up from 92 % and fades in.
     * Back:    screen scales down to 92 % and fades out.
     */
    fun scaleAndFade(durationMs: Int = NAV_TRANSITION_DURATION) = NavTransition(
        enter    = {
            scaleIn(tween(durationMs), initialScale = 0.92f) +
            fadeIn(tween(durationMs))
        },
        exit     = {
            scaleOut(tween(durationMs / 2), targetScale = 0.95f) +
            fadeOut(tween(durationMs / 2))
        },
        popEnter = {
            scaleIn(tween(durationMs / 2), initialScale = 0.95f) +
            fadeIn(tween(durationMs / 2))
        },
        popExit  = {
            scaleOut(tween(durationMs), targetScale = 0.92f) +
            fadeOut(tween(durationMs))
        },
    )

    /**
     * No animation — instant screen switch.
     * Use for splash → home, authentication gates, or any switch where animation
     * would feel jarring (e.g. the user already tapped through a loading screen).
     */
    fun none() = NavTransition(
        enter    = { EnterTransition.None },
        exit     = { ExitTransition.None },
        popEnter = { EnterTransition.None },
        popExit  = { ExitTransition.None },
    )

    // ── Default ───────────────────────────────────────────────────────────────

    /** The default transition used by [animatedComposable] when none is specified. */
    val Default: NavTransition = slideFromRight()
}

// ── Convenience top-level vals for NavHost ────────────────────────────────────

/**
 * Drop-in lambdas for setting a global default on [NavHost].
 *
 * ```kotlin
 * NavHost(
 *     startDestination   = StartRoute,
 *     enterTransition    = defaultEnterTransition,
 *     exitTransition     = defaultExitTransition,
 *     popEnterTransition = defaultPopEnterTransition,
 *     popExitTransition  = defaultPopExitTransition,
 * ) { … }
 * ```
 */
val defaultEnterTransition    = NavTransitions.Default.enter
val defaultExitTransition     = NavTransitions.Default.exit
val defaultPopEnterTransition = NavTransitions.Default.popEnter
val defaultPopExitTransition  = NavTransitions.Default.popExit
