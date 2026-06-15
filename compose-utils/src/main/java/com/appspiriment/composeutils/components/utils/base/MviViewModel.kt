package com.appspiriment.composeutils.components.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base MVI ViewModel — KMP-compatible (androidx.lifecycle >= 2.8.0 targets commonMain).
 *
 * [State]  – immutable snapshot of what the UI renders. Exposed as a [StateFlow].
 * [Intent] – user actions dispatched via [dispatch] and handled in [onIntent].
 * [Effect] – one-shot side-effects (navigation, toasts) that must not be replayed
 *             on recomposition. Collect with [collectEffects] in your screen composable.
 *
 * Usage:
 * ```
 * class LoginViewModel : MviViewModel<LoginState, LoginIntent, LoginEffect>(LoginState()) {
 *     override suspend fun onIntent(intent: LoginIntent) = when (intent) {
 *         is LoginIntent.Submit -> {
 *             updateState { copy(loading = true) }
 *             val ok = repo.login(intent.email, intent.password)
 *             updateState { copy(loading = false) }
 *             if (ok) sendEffect(LoginEffect.NavigateHome)
 *             else    sendEffect(LoginEffect.ShowError("Invalid credentials"))
 *         }
 *     }
 * }
 * ```
 */
abstract class MviViewModel<State : Any, Intent : Any, Effect : Any>(
    initialState: State,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effects = Channel<Effect>(Channel.BUFFERED)
    val effects: Flow<Effect> = _effects.receiveAsFlow()

    fun dispatch(intent: Intent) {
        viewModelScope.launch { onIntent(intent) }
    }

    protected abstract suspend fun onIntent(intent: Intent)

    protected fun updateState(transform: State.() -> State) {
        _state.update { it.transform() }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

/**
 * MVI ViewModel variant for screens that have effects and state but no user intents
 * (e.g. a detail screen driven entirely by a saved-state argument).
 */
abstract class MviStateViewModel<State : Any, Effect : Any>(
    initialState: State,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effects = Channel<Effect>(Channel.BUFFERED)
    val effects: Flow<Effect> = _effects.receiveAsFlow()

    protected fun updateState(transform: State.() -> State) {
        _state.update { it.transform() }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}
