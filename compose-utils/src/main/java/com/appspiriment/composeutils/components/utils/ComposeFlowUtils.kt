package com.appspiriment.composeutils.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.appspiriment.composeutils.components.utils.base.MviStateViewModel
import com.appspiriment.composeutils.components.utils.base.MviViewModel
import kotlinx.coroutines.flow.Flow


/**
 * Collects this [Flow] in a [LaunchedEffect] scoped to the composition, respecting the
 * [lifecycleOwner]'s lifecycle. The inner coroutine is cancelled automatically when the
 * composable leaves composition — no manual cleanup required.
 */
@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect {
            action(it)
        }
    }
}

/**
 * Collects one-shot [MviViewModel.effects] inside a lifecycle-aware [LaunchedEffect].
 * Call this once in your screen composable alongside `collectAsStateWithLifecycle()`.
 *
 * ```
 * val vm: LoginViewModel = viewModel()
 * val state by vm.state.collectAsStateWithLifecycle()
 * vm.collectEffects { effect ->
 *     when (effect) {
 *         LoginEffect.NavigateHome -> navController.navigate(HomeRoute)
 *         is LoginEffect.ShowError -> snackbarHost.showSnackbar(effect.message)
 *     }
 * }
 * ```
 */
@Composable
fun <State : Any, Intent : Any, Effect : Any> MviViewModel<State, Intent, Effect>.collectEffects(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onEffect: suspend (Effect) -> Unit,
) {
    LaunchedEffect(this) {
        effects
            .flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
            .collect { onEffect(it) }
    }
}

/** Variant of [collectEffects] for [MviStateViewModel]. */
@Composable
fun <State : Any, Effect : Any> MviStateViewModel<State, Effect>.collectEffects(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onEffect: suspend (Effect) -> Unit,
) {
    LaunchedEffect(this) {
        effects
            .flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
            .collect { onEffect(it) }
    }
}