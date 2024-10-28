package com.peterfam.valify_registration

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.peterfam.valify_registration.ui.theme.ValifyRegistrationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.peterfam.valifaysdk.presentation.screen.RegistrationActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * here we can check if the user already filled the registration to not show again
         * this can be achieved using SharedPreferances
         */
        startActivity(Intent(this, RegistrationActivity::class.java))
        setContent {
            Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "MainActivity in the main app.. you exist the SDK",
        style = TextStyle(fontSize = 30.sp, color = Color.Black),
        modifier = modifier
    )
}