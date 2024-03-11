package com.peterfam.valifaysdk.presentation.screen.profile_pic_screen.viewmodel

import androidx.lifecycle.viewModelScope
import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.data.UserModel
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.BaseViewModel
import com.peterfam.valifaysdk.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PhotoPicUiEffect: UiEffect(){
    data object ShowSuccessDialog: PhotoPicUiEffect()

}
@HiltViewModel
class PhotoPicViewModel @Inject constructor(private val userRepo: UsersRepo):
    BaseViewModel<PhotoPicEvent, PhotoPicState>() {
    override fun initialState(): PhotoPicState {
        return PhotoPicState()
    }
    private var userData: UserModel? = null

    override fun onEvent(event: PhotoPicEvent) {
       when(event){
           is PhotoPicEvent.SaveDataToDatabase -> {
               saveDataToDatabase(event.photo)
           }
       }
    }

    private fun saveDataToDatabase(userPhoto: String) {
        viewModelScope.launch {
            userData?.let { user ->
                userRepo.insertUser(
                    User(
                        userName = user.userName,
                        email = user.email,
                        password = user.password,
                        mob = user.mob,
                        imgUrl = userPhoto
                    )
                )
                setEffect { PhotoPicUiEffect.ShowSuccessDialog }
            }

        }
    }
    fun setUserData(userData: UserModel) {
        this.userData = userData
    }
}