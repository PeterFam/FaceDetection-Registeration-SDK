package com.peterfam.valifaysdk.di

import android.app.Application
import androidx.room.Room
import com.peterfam.valifaysdk.data.UsersDao
import com.peterfam.valifaysdk.data.UsersDatabase
import com.peterfam.valifaysdk.domain.UserRepoImpl
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.presentation.viewmodel.RegistrationViewModel
import com.peterfam.valifaysdk.util.Constants
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RegistrationViewModel)
}

fun provideRoomDatabase(application: Application) : UsersDatabase {
    return Room.databaseBuilder(
        application,
        UsersDatabase::class.java,
        Constants.DBName
    ).fallbackToDestructiveMigration().build()
}

fun provideDao(database: UsersDatabase): UsersDao = database.usersDao()

val databaseModule = module {
    single { provideDao(get()) }
    single { provideRoomDatabase(get()) }
}

val repositoryModule = module {
    factory<UsersRepo> { UserRepoImpl(get()) }
}