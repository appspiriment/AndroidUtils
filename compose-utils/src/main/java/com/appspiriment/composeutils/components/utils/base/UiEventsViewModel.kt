package com.appspiriment.composeutils.components.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Deprecated(
    message = "Use MviViewModel<Unit, EventType, UiEventType> or MviStateViewModel<Unit, UiEventType> instead.",
    replaceWith = ReplaceWith("MviViewModel<Unit, EventType, UiEventType>"),
    level = DeprecationLevel.WARNING,
)
abstract class UiEventsViewModel <EventType:Any, UiEventType:Any>: ViewModel() {


    private val _uiEventChannel = Channel<UiEventType>(Channel.BUFFERED)

    val uiEventFlow = _uiEventChannel.receiveAsFlow()

    protected fun sendUiEvent(uiEvent: UiEventType){
        viewModelScope.launch {
            _uiEventChannel.send(uiEvent)
        }
    }

    abstract fun onEvent(event: EventType)
}