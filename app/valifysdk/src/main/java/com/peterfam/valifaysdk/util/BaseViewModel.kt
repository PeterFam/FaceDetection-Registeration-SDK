package com.peterfam.valifaysdk.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterfam.valifaysdk.core.UiText
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun <viewModel : LifecycleObserver> viewModel.ObserveLifecycleEvents(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@ObserveLifecycleEvents)
        onDispose {
            lifecycle.removeObserver(this@ObserveLifecycleEvents)
        }
    }
}

abstract class BaseViewModel<Event : UiEvent, State : UiState> : ViewModel(),
    DefaultLifecycleObserver {

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        if (viewModelScope.isActive) {
            viewModelScope.coroutineContext.cancelChildren()
        }
    }

    private val _effectFlow = MutableSharedFlow<UiEffect>()
    val effectFlow: SharedFlow<UiEffect> = _effectFlow

    private val initialState: State by lazy { initialState() }

    abstract fun initialState(): State

    var viewState by mutableStateOf(initialState)
        private set

    abstract fun onEvent(event: Event)

    fun setState(reducer: State.() -> State) {
        val newState = viewState.reducer()
        viewState = newState
    }

    fun showSnackBar(uiText: UiText, isSuccess: Boolean = false) {
        viewModelScope.launch {
            setEffect { CommonUiEffect.ShowSnackbar(uiText, isSuccess) }
        }
    }

    protected fun navigate(route: String, popUpTo: String? = null, inclusive: Boolean = false) {
        viewModelScope.launch {
            setEffect { CommonUiEffect.Navigate(route, popUpTo, inclusive) }
        }
    }

    protected fun navigateUp() {
        viewModelScope.launch { setEffect { CommonUiEffect.NavigateUp } }
    }

    protected suspend fun setEffect(builder: () -> UiEffect) {
        val effect = builder()
        _effectFlow.emit(effect)
    }

    suspend fun showSnackBar(@StringRes messageResId: Int, isSuccess: Boolean = false) {
        _effectFlow.emit(
            CommonUiEffect.ShowSnackbar(
                UiText.StringResource(messageResId), isSuccess
            )
        )
    }


    suspend fun showSnackBar(message: String) {
        _effectFlow.emit(
            CommonUiEffect.ShowSnackbar(
                UiText.DynamicText(message)
            )
        )
    }

}