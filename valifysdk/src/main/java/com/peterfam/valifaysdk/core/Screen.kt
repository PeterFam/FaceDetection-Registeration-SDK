package com.peterfam.valifaysdk.core

import com.peterfam.valifaysdk.util.Constants

sealed class Screen (val route: String){
    object RegisterScreen: Screen(Constants.registerScreenRoute)
    object ProfilePicScreen: Screen(Constants.profilePicScreen)
    object UsersScreen: Screen(Constants.usersScreen)
}