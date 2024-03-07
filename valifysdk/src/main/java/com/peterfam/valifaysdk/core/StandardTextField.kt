package com.peterfam.valifaysdk.core

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import javax.inject.Singleton

@Composable
fun StandardTextFiled(
    state: AbstractTextFieldState,
    modifier: Modifier = Modifier,
    hint: String = "",
    label: String,
    onValueChanged: (String) -> Unit = {},
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    borderWidth: Dp = 1.dp,
    error: String? = null,
    hasError: Boolean = !error.isNullOrEmpty(),
){
    var borderColor by remember {
        mutableStateOf(if (hasError) Color.Red else Color.DarkGray)
    }
    BasicTextField(
        modifier = modifier.onFocusChanged { focusState ->
            borderColor = if (focusState.hasFocus) {
                Color.Black
            } else {
                Color.LightGray
            }
        },
        value = state.text,
        keyboardOptions =  KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = singleLine,
        maxLines = 1,
        minLines = 1,
        onValueChange = {
            onValueChanged(it)
        },
    )

}