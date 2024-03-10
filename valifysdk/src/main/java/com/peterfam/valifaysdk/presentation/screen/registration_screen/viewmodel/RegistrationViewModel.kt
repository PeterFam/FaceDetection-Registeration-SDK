package com.peterfam.valifaysdk.presentation.screen.registration_screen.viewmodel

import androidx.lifecycle.viewModelScope
import com.peterfam.valifaysdk.core.UiText
import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.BaseViewModel
import com.peterfam.valifaysdk.util.UiEffect
import com.peterfam.valifaysdk.util.Utils
import com.peterfam.valifysdk.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegistrationUiEffect : UiEffect() {
    data object HideKeyboard : RegistrationUiEffect()
    data class ShowValidationMsg(val msg: UiText.StringResource): RegistrationUiEffect()
    data object NavigateToPhotoPickScreen: RegistrationUiEffect()
}
@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userRepo: UsersRepo): BaseViewModel<RegistrationEvent, RegistrationState>() {


    override fun initialState(): RegistrationState {
        return RegistrationState()
    }

    override fun onEvent(event: RegistrationEvent) {
        when(event){
            is RegistrationEvent.SavingData -> {
                viewModelScope.launch {
                    setEffect { RegistrationUiEffect.NavigateToPhotoPickScreen }
                }
//                savingUserData(
//                    User(
//                    userName = viewState.userNameFieldState.text,
//                    email = viewState.emailFieldState.text,
//                    mob = viewState.mobNumFieldState.text.toInt(),
//                    password = viewState.passwordFieldState.text
//                    )
//                )
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
            setEffect { RegistrationUiEffect.HideKeyboard }
//            if(isFormValid(viewState)){
//                setState { copy(isLoading = false) }
//                userRepo.insertUser(user)
//                clearData()
//                setState { copy(isLoading = false) }
//            }
            setEffect { RegistrationUiEffect.NavigateToPhotoPickScreen }
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