package com.peterfam.valifaysdk.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peterfam.valifaysdk.data.UsersDao
import com.peterfam.valifaysdk.data.UsersDatabase
import com.peterfam.valifaysdk.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) : UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            Utils.DBName
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: UsersDatabase): UsersDao = database.usersDao()

}