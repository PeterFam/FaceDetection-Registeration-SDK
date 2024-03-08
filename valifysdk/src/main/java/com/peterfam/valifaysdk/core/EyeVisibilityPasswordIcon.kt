package com.peterfam.valifaysdk.core

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.peterfam.valifysdk.R

@Composable
fun EyeVisibilityIcon(
    change: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val imageVector = when (change) {
        true -> ImageVector.vectorResource(id = R.drawable.eye_pw_show)
        false -> ImageVector.vectorResource(id = R.drawable.eye_pw_hide)
    }

    val tint = when (change) {
        true -> Color.Gray
        false -> Color.Black
    }
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            tint = tint,
            contentDescription = ""
        )
    }

}