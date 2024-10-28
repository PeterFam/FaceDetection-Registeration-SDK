package com.peterfam.valifaysdk.core

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.peterfam.valifaysdk.util.Utils
import com.peterfam.valifysdk.R

@Composable
fun PermissionDeniedContent(
    context: Context,
    rationaleMessage: String,
    onDismissRequest: (Boolean) -> Unit,
) {
        AlertDialog(
            onDismissRequest = {onDismissRequest(false)},
            title = {
                Text(
                    text = stringResource(id = R.string.permission_needed),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(rationaleMessage)
            },
            confirmButton = {
                Button(onClick = {
                    onDismissRequest(false)
                    Utils.openAppSettings(context)}) {
                    Text("Allow Permission")
                }
            }
        )
}