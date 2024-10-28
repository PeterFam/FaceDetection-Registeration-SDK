package com.peterfam.valifaysdk.di

import android.app.Application
import androidx.room.Room
import com.peterfam.valifaysdk.data.UsersDao
import com.peterfam.valifaysdk.data.UsersDatabase
import com.peterfam.valifaysdk.domain.UserRepoImpl
import com.peterfam.valifaysdk.domain.UsersRepo
import com.peterfam.valifaysdk.presentation.screen.profile_pic_screen.viewmodel.PhotoPicViewModel
import com.peterfam.valifaysdk.presentation.screen.registration_screen.viewmodel.RegistrationViewModel
import com.peterfam.valifaysdk.presentation.screen.user_list.view_model.UserListViewModel
import com.peterfam.valifaysdk.util.Constants
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registrationViewModelModule = module {
    viewModel {  RegistrationViewModel(get()) }
}
val photoPicViewModelModule = module {
    viewModel { PhotoPicViewModel(get()) }
}

val userListViewModel = module {
    viewModel { UserListViewModel(get()) }
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