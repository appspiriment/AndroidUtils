package com.appspiriment.composeutils.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow


/**
 * Collects this [Flow] in a [LaunchedEffect] scoped to the composition, respecting the
 * [lifecycleOwner]'s lifecycle. The inner coroutine is cancelled automatically when the
 * composable leaves composition — no manual cleanup required.
 *
 * Previously this used `lifecycleOwner.lifecycleScope.launch { … }` inside
 * [LaunchedEffect], which created a child coroutine on the *lifecycle* scope rather than
 * the *composition* scope. That inner coroutine outlived the composable and was only
 * cancelled when the lifecycle itself was destroyed, causing a coroutine leak.
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