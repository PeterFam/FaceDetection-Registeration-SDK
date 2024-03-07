package com.peterfam.valifaysdk.domain

import androidx.lifecycle.LiveData
import com.peterfam.valifaysdk.data.User

interface UsersRepoImpl {

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun getUsers(): LiveData<List<User>>

}