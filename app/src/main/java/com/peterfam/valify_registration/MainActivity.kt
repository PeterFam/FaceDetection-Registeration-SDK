package com.peterfam.valify_registration

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.peterfam.valifaysdk.presentation.screen.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, RegistrationActivity::class.java))
    }
}
