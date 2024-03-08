package com.peterfam.valifaysdk.presentation.screen.ui

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.peterfam.valifaysdk.core.AbstractTextFieldState
import com.peterfam.valifaysdk.core.EyeVisibilityIcon
import com.peterfam.valifaysdk.core.StandardTextFiled


@Composable
fun PasswordTextField(
    state: AbstractTextFieldState,
    hint: String = "",
    label: String? = null,
    onValueChanged: ((newValue: String) -> Unit)? = null,
    maxLength: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next,
) {
    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }
    StandardTextFiled(
        state = state,
        onValueChange = {
            onValueChanged?.invoke(it.take(maxLength))
        },
        trailingIcon = {
            EyeVisibilityIcon(
                change = !showPassword,
                modifier = Modifier.size(20.dp),
                onClick = { showPassword = !showPassword })
        },
        placeholderText = hint,
        label = label,
        visualTransformation = if (!showPassword) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
    )

}