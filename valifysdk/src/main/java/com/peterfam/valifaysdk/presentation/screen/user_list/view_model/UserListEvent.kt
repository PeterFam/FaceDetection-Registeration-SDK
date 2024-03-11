package com.peterfam.valifaysdk.presentation.screen.user_list.view_model

import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.util.UiEvent

sealed class UserListEvent: UiEvent() {

    data class DeleteUser(val user: User): UserListEvent()

}