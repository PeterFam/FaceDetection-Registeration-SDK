package com.peterfam.valifaysdk.domain

import androidx.lifecycle.LiveData
import com.peterfam.valifaysdk.data.User
import kotlinx.coroutines.flow.Flow

interface UsersRepoImpl {

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    fun getUsers(): Flow<List<User>>

}