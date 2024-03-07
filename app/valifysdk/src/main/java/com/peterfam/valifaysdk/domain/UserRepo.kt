package com.peterfam.valifaysdk.domain

import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.data.UsersDao
import javax.inject.Inject

class UserRepo @Inject constructor(private val usersDao: UsersDao): UsersRepoImpl {
    override suspend fun insertUser(user: User) {
        usersDao.insertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        usersDao.deleteUser(user)
    }

    override suspend fun getUsers() = usersDao.getUsers()

}