package com.peterfam.valifaysdk.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userRepo: UsersRepo): BaseViewModel<RegistrationEvent, RegistrationState>() {


    override fun initialState(): RegistrationState {
        return RegistrationState()
    }

    override fun onEvent(event: RegistrationEvent) {
        when(event){
            is RegistrationEvent.SavingData -> {
                savingUserData(
                    User(
                    userName = viewState.userNameFieldState.text,
                    email = viewState.emailFieldState.text,
                    mob = viewState.mobNumFieldState.text.toInt(),
                    password = viewState.passwordFieldState.text
                    )
                )
            }
        }
    }

    private fun savingUserData(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = false) }
            userRepo.insertUser(user)
            setState { copy(isLoading = false) }

            userRepo.getUsers().collect{
                Log.d("databaseee",it.joinToString())
            }
        }
    }
}