package com.peterfam.valifaysdk.presentation.screen.user_list.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserListViewModel @Inject constructor(val usersRepo: UsersRepo): BaseViewModel<UserListEvent, UserListUiState>() {
    override fun initialState(): UserListUiState {
        return UserListUiState()
    }

    override fun onEvent(event: UserListEvent) {
        when(event)  {
            is UserListEvent.DeleteUser -> {

            }
        }
    }

    fun getUsersData(){
        viewModelScope.launch {
           usersRepo.getUsers().collect{
               Log.d("usersss", it.joinToString())
              setState {copy(userList= it)
              }
            }
        }

    }

}