package com.peterfam.valifaysdk.domain

import com.peterfam.valifaysdk.data.User
import kotlinx.coroutines.flow.Flow

interface UsersRepo {

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    fun getUsers(): Flow<List<User>>

}