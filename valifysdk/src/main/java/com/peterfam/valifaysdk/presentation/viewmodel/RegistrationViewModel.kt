package com.peterfam.valifaysdk.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.peterfam.valifaysdk.core.UiText
import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.BaseViewModel
import com.peterfam.valifaysdk.util.UiEffect
import com.peterfam.valifaysdk.util.Utils
import com.peterfam.valifysdk.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class RegistrationUiEffect : UiEffect() {
    data object HideKeyboard : RegistrationUiEffect()
    data class ShowValidationMsg(val msg: UiText.StringResource): RegistrationUiEffect()
}
class RegistrationViewModel(private val userRepo: UsersRepo): BaseViewModel<RegistrationEvent, RegistrationState>() {
    
    override fun initialState(): RegistrationState {
        return RegistrationState()
    }

    override fun onEvent(event: RegistrationEvent) {
        when(event){
            is RegistrationEvent.SavingData -> {
                viewModelScope.launch {
                    setEffect { RegistrationUiEffect.HideKeyboard }
                }

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

    private suspend fun isFormValid(viewState: RegistrationState): Boolean{
        if(!Utils.isMobileNumberValid(viewState.mobNumFieldState.text)){
            setEffect { RegistrationUiEffect.ShowValidationMsg(UiText.StringResource(R.string.invalid_mobile)) }
            return false
        }
        if(!Utils.isEmailValid(viewState.emailFieldState.text)){
            setEffect { RegistrationUiEffect.ShowValidationMsg(UiText.StringResource(R.string.invalid_password)) }
            return false
        }
        if(!Utils.isPasswordValid(viewState.passwordFieldState.text)){
            setEffect { RegistrationUiEffect.ShowValidationMsg(UiText.StringResource(R.string.invalid_password)) }
            return false
        }
        return true
    }

    private fun savingUserData(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            if(isFormValid(viewState)){
                setState { copy(isLoading = false) }
                userRepo.insertUser(user)
                clearData()
                setState { copy(isLoading = false) }

                userRepo.getUsers().collect{
                    Log.d("databaseee",it.joinToString())
                }
            }
        }
    }
    private fun clearData(){
        viewModelScope.launch {
            setState { copy(
                userNameFieldState = userNameFieldState.clearText(),
                emailFieldState = emailFieldState.clearText(),
                mobNumFieldState = mobNumFieldState.clearText(),
                passwordFieldState = passwordFieldState.clearText()) }
        }
    }
}