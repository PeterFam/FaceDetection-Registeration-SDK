package com.peterfam.valifaysdk.presentation.viewmodel

import com.peterfam.valifaysdk.core.AbstractTextFieldState
import com.peterfam.valifaysdk.util.UiState

data class RegistrationState(

    val isLoading: Boolean = false,
    val userNameFieldState: AbstractTextFieldState,
    val emailFieldState: AbstractTextFieldState,
    val mobNumFieldState: AbstractTextFieldState,
    val passwordFieldState: AbstractTextFieldState
): UiState()
