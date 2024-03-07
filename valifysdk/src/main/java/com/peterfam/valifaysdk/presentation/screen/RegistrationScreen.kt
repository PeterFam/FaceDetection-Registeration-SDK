package com.peterfam.valifaysdk.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.peterfam.valifaysdk.presentation.screen.ui.theme.Cyan
import com.peterfam.valifaysdk.presentation.viewmodel.RegistrationViewModel
import com.peterfam.valifysdk.R
@Composable
fun RegistrationScreen(){
    val viewModel: RegistrationViewModel = hiltViewModel()
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(color = Cyan)) {

        item {
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
                text = stringResource(id = R.string.registration),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
                )
        }
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .background(color = Color.Unspecified, shape = RoundedCornerShape(20.dp))
                .border(width = 1.dp, shape = RoundedCornerShape(20.dp), color= Color.LightGray)
            ) {
                BasicTextField(
                    modifier = Modifier.onFocusChanged { focusState ->
//                        borderColor = if (focusState.hasFocus) {
//                            Color.Black
//                        } else {
//                            Color.LightGray
//                        }
                    },
                    value = viewModel.viewState.userNameFieldState.text,
                    keyboardOptions =  KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    maxLines = 1,
                    minLines = 1,
                    onValueChange = {
                        viewModel.viewState.userNameFieldState.text = it
                    },
                )
            }

        }

    }
}