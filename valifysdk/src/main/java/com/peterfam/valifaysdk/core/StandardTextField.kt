package com.peterfam.valifaysdk.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import javax.inject.Singleton

@Composable
fun StandardTextFiled(
    state: AbstractTextFieldState,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    height: Dp = 45.dp,
    trailingIcon: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    placeholderText: String? = null,
    label: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    maxLength: Int = Int.MAX_VALUE,
    textStyle: TextStyle = TextStyle(fontSize = 13.sp, color = Black),
    shape: Shape = RoundedCornerShape(12.dp),
    borderWidth: Dp = 1.dp,
    containerColor: Color = White,
    borderColor: Color = LightGray,
    isEnabled: Boolean = true,
    infoText: (@Composable () -> Unit)? = null,
){
    // Remember the border color
    var newBorderColor by remember { mutableStateOf(borderColor) }
    BasicTextField(
        value = state.text,
        onValueChange = {
            state.onValueChange(it.take(maxLength))
            onValueChange(it.take(maxLength))
        },
        modifier = modifier
            .onFocusChanged { focusState ->
                state.onFocusChange(focusState.isFocused)
                newBorderColor = if (focusState.isFocused) Black else borderColor
            },
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        textStyle = textStyle,
        enabled = isEnabled,
        decorationBox = { innerTextField ->
            // Create a column for the TextField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
            ) {
                // Display the label if it is not null
                label?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Gray, fontSize = 13.sp),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                // Create a row for the TextField
                Row(
                    modifier = Modifier
                        .border(
                            width = borderWidth,
                            color = newBorderColor,
                            shape = shape
                        )
                        .fillMaxWidth()
                        .heightIn(min = height)
                        .background(
                            color = if (isEnabled) containerColor else Color.LightGray,
                            shape = shape
                        )
                        .border(width = borderWidth, color = newBorderColor, shape = shape)
                        .padding(end = 16.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Display the leading icon if it is not null
                    leadingIcon?.let { leading->
                        leading()
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Box(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        // Display the placeholder text if the TextField is empty
                        if (placeholderText != null && state.text.isEmpty()) {
                            Text(
                                text = placeholderText,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = LightGray,
                                ),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                        innerTextField()
                    }
                    trailingIcon?.let { trailingIcon ->
                        Spacer(modifier = Modifier.weight(1f))
                        // Display the trailing icon if it is not null
                        trailingIcon()
                    }

                }
                // Display the error message if there is an error
                if (state.hasError) {
                    newBorderColor = Color.Red
                    StandardError(state.getError())
                }
                if (infoText != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    infoText()
                }
            }
        },
    )

}
@Composable
fun StandardError(
    error: UiText?,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(
        fontSize = 11.sp,
    ),
    color: Color = Color.Red,
) {
    Text(
        text = error?.asString(LocalContext.current).orEmpty(),
        style = style.copy(color = color),
        modifier = modifier
            .padding(start = 16.dp)
    )
}