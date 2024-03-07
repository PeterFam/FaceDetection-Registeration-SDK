package com.peterfam.valifaysdk.domain

import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.data.UsersDao
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val usersDao: UsersDao): UsersRepo {
    override suspend fun insertUser(user: User) {
        usersDao.insertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        usersDao.deleteUser(user)
    }

    override fun getUsers() = usersDao.getUsers()

}