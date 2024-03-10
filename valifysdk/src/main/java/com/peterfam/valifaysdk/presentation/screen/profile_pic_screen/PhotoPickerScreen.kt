package com.peterfam.valifaysdk.presentation.screen.profile_pic_screen

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.peterfam.valifaysdk.core.PermissionDeniedContent
import com.peterfam.valifaysdk.core.UiText
import com.peterfam.valifaysdk.util.getCameraProvider
import com.peterfam.valifysdk.R
import java.io.File
import java.util.concurrent.Executor

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoPickerRoute(navController: NavController){
    val context = LocalContext.current
    val showPermissionDialog = remember { mutableStateOf( false) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA){isGranted ->
       if(!isGranted){
          showPermissionDialog.value = true
       }
    }

    LaunchedEffect(key1 = Unit) {
        cameraPermissionState.launchPermissionRequest()
    }
    if(showPermissionDialog.value){
        PermissionDeniedContent(context = context,
            rationaleMessage =
        UiText.StringResource(R.string.permission_needed_msg).asString(context)
        ) {
            showPermissionDialog.value = it
        }
    }

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        if(!cameraPermissionState.status.isGranted){
            Button(modifier = Modifier.wrapContentSize(), onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text(stringResource(id = R.string.allow_camera_permission))
            }
        }else{
            ProfilePickScreen()
        }
    }
}

@Composable
fun ProfilePickScreen(){
    Text("Camera Started", style = TextStyle(fontSize = 30.sp))
}

@Composable
fun CameraView(
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    // 1
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    // 2
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    // 3
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {
                Log.i("kilo", "ON CLICK")
//                takePhoto(
//                    filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
//                    imageCapture = imageCapture,
//                    outputDirectory = outputDirectory,
//                    executor = executor,
//                    onImageCaptured = onImageCaptured,
//                    onError = onError
//                )
            },
            content = {
                Icon(
                    imageVector = Icons.Sharp.Done,
                    contentDescription = "Take picture",
                    tint = Color.White,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                        .border(1.dp, Color.White, CircleShape)
                )
            }
        )
    }
}