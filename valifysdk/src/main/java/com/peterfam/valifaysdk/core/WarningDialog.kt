package com.peterfam.valifaysdk.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.peterfam.valifysdk.R

@Composable
fun StandardWarningDialog(modifier: Modifier = Modifier, msg : String, setShowDialog: (Boolean) -> Unit){
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 30.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(modifier = modifier) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(
                                    shape = RoundedCornerShape(size = 25.dp), color = Color.White
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.padding(16.dp).width(70.dp).height(70.dp),
                                painter = painterResource(id = R.drawable.attention),
                                contentDescription = ""
                            )
                        }

                        Text(
                            modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                            text = stringResource(id = R.string.attention),
                            fontSize = 20.sp,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Text(
                            modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                            text = msg,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        StandardButton(
                            modifier = modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                            text = stringResource(id = R.string.ok)
                        ) {
                            setShowDialog(false)
                        }
                    }
                }
            }
        }
    }
}