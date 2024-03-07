package com.peterfam.valifaysdk.presentation.viewmodel

import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.util.UiEvent

sealed class RegistrationEvent : UiEvent(){

    data class SavingData(val user: User): RegistrationEvent()

}