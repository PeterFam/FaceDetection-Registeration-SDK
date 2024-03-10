package com.peterfam.valifaysdk.presentation.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.peterfam.valifaysdk.core.Screen
import com.peterfam.valifaysdk.data.UserModel
import com.peterfam.valifaysdk.presentation.screen.profile_pic_screen.PhotoPickerRoute
import com.peterfam.valifaysdk.presentation.screen.registration_screen.RegistrationRoute
import com.peterfam.valifaysdk.presentation.screen.ui.theme.ValifyRegistrationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ValifyRegistrationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination =  Screen.RegisterScreen.route){
                        composable(route = Screen.RegisterScreen.route){
                            RegistrationRoute(navController = navController)
                        }
                        composable(route = Screen.ProfilePicScreen.route){
                            val user = Gson().fromJson(it.arguments?.getString("user"), UserModel::class.java)
                            Log.d("userrr", user.toString())
                            PhotoPickerRoute(navController = navController, userModel = user)
                        }
                    }
                }
            }
        }
    }
}