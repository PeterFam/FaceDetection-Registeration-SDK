package com.peterfam.valifaysdk.presentation.viewmodel

import com.peterfam.valifaysdk.core.AbstractTextFieldState
import com.peterfam.valifaysdk.core.RequiredTextFieldState
import com.peterfam.valifaysdk.util.UiState

data class RegistrationState(

    val isLoading: Boolean = false,
    val userNameFieldState: AbstractTextFieldState = RequiredTextFieldState(),
    val emailFieldState: AbstractTextFieldState = RequiredTextFieldState(),
    val mobNumFieldState: AbstractTextFieldState = RequiredTextFieldState(),
    val passwordFieldState: AbstractTextFieldState = RequiredTextFieldState()
): UiState()
