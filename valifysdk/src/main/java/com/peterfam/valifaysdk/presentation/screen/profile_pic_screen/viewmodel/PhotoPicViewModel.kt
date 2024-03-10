package com.peterfam.valifaysdk.presentation.screen.profile_pic_screen.viewmodel

import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoPicViewModel @Inject constructor(private val userRepo: UsersRepo):
    BaseViewModel<PhotoPicEvent, PhotoPicState>() {
    override fun initialState(): PhotoPicState {
        return PhotoPicState()
    }

    override fun onEvent(event: PhotoPicEvent) {
       when(event){
           else -> {}
       }
    }
}