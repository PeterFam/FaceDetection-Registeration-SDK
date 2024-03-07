package com.peterfam.valifaysdk.di

import android.content.Context
import androidx.room.Room
import com.peterfam.valifaysdk.data.UsersDao
import com.peterfam.valifaysdk.data.UsersDatabase
import com.peterfam.valifaysdk.util.Utils
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
    fun provideRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        UsersDatabase::class.java,
        Utils.DBName
    )

    @Singleton
    @Provides
    fun provideDao(database: UsersDatabase): UsersDao = database.usersDao()

}