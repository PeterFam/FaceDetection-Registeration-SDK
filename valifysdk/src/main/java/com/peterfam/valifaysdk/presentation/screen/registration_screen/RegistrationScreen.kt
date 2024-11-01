package com.peterfam.valifaysdk.presentation.screen.registration_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.peterfam.valifaysdk.core.Screen
import com.peterfam.valifaysdk.core.StandardButton
import com.peterfam.valifaysdk.core.StandardTextFiled
import com.peterfam.valifaysdk.core.StandardWarningDialog
import com.peterfam.valifaysdk.presentation.screen.registration_screen.viewmodel.RegistrationEvent
import com.peterfam.valifaysdk.presentation.screen.registration_screen.viewmodel.RegistrationUiEffect
import com.peterfam.valifaysdk.presentation.screen.registration_screen.viewmodel.RegistrationViewModel
import com.peterfam.valifaysdk.presentation.screen.ui.theme.Cyan
import com.peterfam.valifaysdk.presentation.screen.ui.theme.DarkGreen
import com.peterfam.valifysdk.R
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationRoute(navController: NavController){


    val viewModel : RegistrationViewModel = koinViewModel()
    val showWarningDialog = remember { mutableStateOf(Pair("", false)) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = true) {
        viewModel.effectFlow.collectLatest { effect ->
            when(effect){
                is RegistrationUiEffect.HideKeyboard -> {
                    keyboardController?.hide()
                }
                is RegistrationUiEffect.ShowValidationMsg -> {
                    showWarningDialog.value = Pair(effect.msg.asString(context), true)
                }
                is RegistrationUiEffect.NavigateToPhotoPickScreen -> {
                    val userData = Gson().toJson(effect.userModel)
                    navController.navigate(Screen.ProfilePicScreen.route.replace("{user}", userData))
                }
                is RegistrationUiEffect.NavigateToUsersList -> {
                    navController.navigate(Screen.UsersScreen.route)
                }
            }

        }
    }
    if(showWarningDialog.value.second){
        StandardWarningDialog(msg = showWarningDialog.value.first) {
            showWarningDialog.value = Pair("", it)
        }
    }
    RegistrationScreen(viewModel = viewModel)
    
}

@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel){

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(color = Cyan)) {

        item {
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
                text = stringResource(id = R.string.registration),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, color= Color.Black)
                )
        }
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .border(width = 1.dp, shape = RoundedCornerShape(20.dp), color = Color.LightGray)
                .padding(16.dp)
            ) {
                StandardTextFiled(
                    state = viewModel.viewState.userNameFieldState,
                    placeholderText = "ex: FirstName_LastName",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = stringResource(id = R.string.username),
                    maxLines = 1,
                    onValueChange = {viewModel.viewState.userNameFieldState.onValueChange(it)}
                )

                Spacer(modifier = Modifier.height(16.dp))

                StandardTextFiled(
                    state = viewModel.viewState.emailFieldState,
                    placeholderText = "ex: name@name.com",
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = stringResource(id = R.string.email),
                    onValueChange = {viewModel.viewState.emailFieldState.onValueChange(it)}
                )

                Spacer(modifier = Modifier.height(16.dp))

                StandardTextFiled(
                    state = viewModel.viewState.mobNumFieldState,
                    placeholderText = "ex: 0123456789",
                    maxLength = 9,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(id = R.string.phone_number),
                    onValueChange = {viewModel.viewState.mobNumFieldState.onValueChange(it)}
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordTextField(
                    state = viewModel.viewState.passwordFieldState,
                    imeAction = ImeAction.Done,
                    label = stringResource(id = R.string.password),
                    hint = "********",
                )

                Spacer(modifier = Modifier.height(40.dp))

                StandardButton(
                    text = stringResource(id = R.string.registration),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = viewModel.viewState.enableRegisterBtn,
                    onClick = {
                        viewModel.onEvent(RegistrationEvent.ValidateRegistrationData)
                    }
                )
            }

        }
        item {
            StandardButton(
                text = stringResource(id = R.string.user_list),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.Black,
                    disabledContainerColor = DarkGreen,
                    disabledContentColor = DarkGreen,
                ),
                onClick = {
                    viewModel.onEvent(RegistrationEvent.NavigateToUsersList)
                }
            )
        }
    }
}