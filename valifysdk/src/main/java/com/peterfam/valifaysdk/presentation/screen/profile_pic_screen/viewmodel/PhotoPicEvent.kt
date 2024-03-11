package com.peterfam.valifaysdk.presentation.screen.profile_pic_screen.viewmodel

import com.peterfam.valifaysdk.util.UiEvent

sealed class PhotoPicEvent : UiEvent(){

    data class SaveDataToDatabase( val photo: String): PhotoPicEvent()

}