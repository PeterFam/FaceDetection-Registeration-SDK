package com.peterfam.valifaysdk.presentation.screen.registration_screen.viewmodel

import com.peterfam.valifaysdk.util.UiEvent

sealed class RegistrationEvent : UiEvent(){

    data object ValidateRegistrationData : RegistrationEvent()

}