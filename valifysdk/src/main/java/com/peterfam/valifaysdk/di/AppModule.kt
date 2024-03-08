package com.peterfam.valifaysdk.di

import android.content.Context
import androidx.room.Room
import com.peterfam.valifaysdk.data.UsersDao
import com.peterfam.valifaysdk.data.UsersDatabase
import com.peterfam.valifaysdk.domain.UserRepoImpl
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) : UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            Constants.DBName
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: UsersDatabase): UsersDao = database.usersDao()

    @Singleton
    @Provides
    fun provideUserRepository(usersDao: UsersDao): UsersRepo{
        return UserRepoImpl(usersDao)
    }

}