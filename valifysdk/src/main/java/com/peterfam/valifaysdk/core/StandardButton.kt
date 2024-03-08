package com.peterfam.valifaysdk.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.peterfam.valifaysdk.presentation.screen.ui.theme.DarkGreen
import com.peterfam.valifaysdk.presentation.screen.ui.theme.Green

@Composable
fun StandardButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = Green,
        contentColor = Green,
        disabledContainerColor = DarkGreen,
        disabledContentColor = DarkGreen,
    ),
    shape: Shape = RoundedCornerShape(12.dp),
    minHeight: Dp = 40.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp),
    textStyle: TextStyle = TextStyle(
        color = Color.White,
        fontSize = 15.sp
    ),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        shape = shape,
        colors = colors,
        enabled = enabled,
        contentPadding = contentPadding,
        modifier = modifier.height(minHeight),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                it()
                Spacer(modifier = Modifier.width(14.dp))
            }
            Text(text = text, style = textStyle)
            trailingIcon?.let {
                Spacer(modifier = Modifier.width(14.dp))
                it()
            }

        }
    }
}