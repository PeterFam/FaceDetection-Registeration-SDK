package com.peterfam.valifaysdk.presentation.screen.profile_pic_screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.peterfam.valifaysdk.core.PermissionDeniedContent
import com.peterfam.valifaysdk.core.StandardSuccessDialog
import com.peterfam.valifaysdk.core.UiText
import com.peterfam.valifaysdk.data.UserModel
import com.peterfam.valifaysdk.face_detection.DetectionTask
import com.peterfam.valifaysdk.face_detection.FaceAnalyzer
import com.peterfam.valifaysdk.face_detection.FaceDetector
import com.peterfam.valifaysdk.face_detection.SmileDetectionTask
import com.peterfam.valifaysdk.presentation.screen.profile_pic_screen.viewmodel.PhotoPicEvent
import com.peterfam.valifaysdk.presentation.screen.profile_pic_screen.viewmodel.PhotoPicUiEffect
import com.peterfam.valifaysdk.presentation.screen.profile_pic_screen.viewmodel.PhotoPicViewModel
import com.peterfam.valifysdk.R
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import java.io.File

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoPickerRoute( userModel: UserModel, activity: ComponentActivity){
    val viewModel: PhotoPicViewModel = koinViewModel()
    viewModel.setUserData(userModel)

    val showSuccessDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val showPermissionDialog = remember { mutableStateOf( false) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA){isGranted ->
       if(!isGranted){
          showPermissionDialog.value = true
       }
    }

    LaunchedEffect(key1 = Unit) {
        cameraPermissionState.launchPermissionRequest()
        viewModel.effectFlow.collectLatest {effect ->
            when(effect){
                is PhotoPicUiEffect.ShowSuccessDialog ->{
                showSuccessDialog.value = true
                }
            }

        }
    }
    if(showSuccessDialog.value){
        StandardSuccessDialog(setShowDialog = {
            showSuccessDialog.value = it
        }){
            activity.finish()
        }
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
            ProfilePickScreen(context, viewModel)
        }
    }
}

@Composable
fun ProfilePickScreen(context: Context, viewModel: PhotoPicViewModel) {
    val cameraController = remember { LifecycleCameraController(context) }
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val previewView: PreviewView = remember { PreviewView(context) }
    cameraController.bindToLifecycle(lifecycleOwner)
    cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    previewView.controller = cameraController
    cameraController.setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context), FaceAnalyzer(
        buildSmileDetector(context, viewModel, cameraController)
    ))

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.wrapContentSize()) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.profile_pic),
                style = TextStyle(
                color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold
            ), textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f)
                .padding(20.dp)
                .background(Color.Unspecified, shape = CircleShape)){
                AndroidView(factory = { previewView }, modifier = Modifier
                    .wrapContentSize()
                    .aspectRatio(1f)
                    .background(Color.Unspecified, shape = CircleShape)
                    .clip(CircleShape))
            }
            Spacer(modifier = Modifier.height(50.dp))

            Text(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.please_smile), style = TextStyle(
                color = Color.White, fontSize = 14.sp,
            ), textAlign = TextAlign.Center)

        }
    }
}

private fun buildSmileDetector(context: Context, viewModel: PhotoPicViewModel,
                               cameraController: CameraController): FaceDetector {
    val faceDetector = FaceDetector(
        SmileDetectionTask()
    )
    val listener = object : FaceDetector.Listener {
        @SuppressLint("SetTextI18n")
        override fun onTaskStarted(task: DetectionTask) {
            viewModel.viewState.commentText = task.taskDescription()
        }

        override fun onTaskCompleted(task: DetectionTask, isLastTask: Boolean) {
            takePhoto(
                cameraController,
                context,
                File(
                    context.cacheDir,
                    "${"valify" + "_" + task.taskName() + "_" + System.currentTimeMillis()}.jpg"
                )
            ) {
                if (isLastTask) {
                    //show success dialog
                    viewModel.onEvent(PhotoPicEvent.SaveDataToDatabase(it.absolutePath))
                }
            }
        }

        override fun onTaskFailed(task: DetectionTask, code: Int) {
            when (code) {
                FaceDetector.ERROR_MULTI_FACES -> {
                    Toast.makeText(context,
                        "Please make sure there is only one face on the screen.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                FaceDetector.ERROR_NO_FACE -> {
                    Toast.makeText(
                        context,
                        "Please make sure there is a face on the screen.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                FaceDetector.ERROR_OUT_OF_DETECTION_RECT -> {
                    Toast.makeText(
                        context,
                        "Please make sure there is a face in the Rectangle.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {
                    Toast.makeText(
                        context,
                        "${task.taskName()} Failed.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    return faceDetector.also { it.setListener(listener) }
}

private fun takePhoto(cameraController: CameraController,
                      context: Context,
                      file: File,
                      onSaved: (File) -> Unit) {
    cameraController.takePicture(
        ImageCapture.OutputFileOptions.Builder(file).build(),
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(e: ImageCaptureException) {
                e.printStackTrace()
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                onSaved(file)
            }
        }
    )
}
