package com.peterfam.valifaysdk.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UsersDatabase : RoomDatabase(){

    abstract fun usersDao(): UsersDao

}