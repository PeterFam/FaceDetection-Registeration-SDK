package com.peterfam.valifaysdk.util

import com.peterfam.valifaysdk.core.UiText

abstract class UiEffect

sealed class CommonUiEffect : UiEffect() {
    data class ShowSnackbar(val uiText: UiText, val isSuccess: Boolean = false) : CommonUiEffect()
    data class Navigate(
        val route: String,
        val popUpTo: String? = null,
        val inclusive: Boolean = false,
    ) : CommonUiEffect()

    data object NavigateUp : CommonUiEffect()
}