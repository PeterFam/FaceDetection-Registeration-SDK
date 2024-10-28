package com.peterfam.valifaysdk.presentation.screen.user_list.view_model

import androidx.lifecycle.viewModelScope
import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserListViewModel @Inject constructor(private val usersRepo: UsersRepo): BaseViewModel<UserListEvent, UserListUiState>() {
    override fun initialState(): UserListUiState {
        return UserListUiState()
    }

    override fun onEvent(event: UserListEvent) {
        when(event)  {
            is UserListEvent.DeleteUser -> {
                deleteUser(event.user)
            }
        }
    }

    private fun deleteUser(user: User){
        viewModelScope.launch {
            usersRepo.deleteUser(user)
        }
        getUsersData()
    }

    fun getUsersData(){
        viewModelScope.launch {
           usersRepo.getUsers().collect{
              setState {copy(userList= it)
              }
            }
        }

    }

}