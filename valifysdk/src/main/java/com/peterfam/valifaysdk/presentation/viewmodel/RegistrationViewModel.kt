package com.peterfam.valifaysdk.presentation.viewmodel

import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userRepo: UsersRepo): BaseViewModel<RegistrationEvent, RegistrationState>() {


    override fun initialState(): RegistrationState {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: RegistrationEvent) {
        TODO("Not yet implemented")
    }
}