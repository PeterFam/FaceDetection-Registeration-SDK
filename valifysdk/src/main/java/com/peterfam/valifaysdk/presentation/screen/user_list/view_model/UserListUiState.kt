package com.peterfam.valifaysdk.presentation.screen.user_list.view_model

import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.util.UiState

data class UserListUiState(
    var userList : List<User> = arrayListOf()
): UiState()
