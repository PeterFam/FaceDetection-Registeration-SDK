package com.peterfam.valifaysdk

import android.app.Application
import com.peterfam.valifaysdk.di.databaseModule
import com.peterfam.valifaysdk.di.photoPicViewModelModule
import com.peterfam.valifaysdk.di.registrationViewModelModule
import com.peterfam.valifaysdk.di.repositoryModule
import com.peterfam.valifaysdk.di.userListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ValifySDKApp : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ValifySDKApp)
            modules(databaseModule)
            modules(repositoryModule)
            modules(registrationViewModelModule)
            modules(photoPicViewModelModule)
            modules(userListViewModel)
        }

    }

}