package com.peterfam.valifaysdk.presentation.screen.user_list.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.peterfam.valifaysdk.data.User
import com.peterfam.valifaysdk.presentation.screen.user_list.view_model.UserListEvent
import com.peterfam.valifaysdk.presentation.screen.user_list.view_model.UserListViewModel
import com.peterfam.valifysdk.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserListRoute(){

    val viewModel: UserListViewModel = koinViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.getUsersData()
    }
    UserListScreen(viewModel = viewModel)
}

@Composable
fun UserListScreen(viewModel: UserListViewModel){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {

        Spacer(modifier = Modifier.height(30.dp))

       Text(modifier = Modifier.padding(10.dp),
           text = stringResource(id = R.string.user_list),
           style = TextStyle(fontSize = 30.sp,
           fontWeight = FontWeight.Bold), color = Color.Black)

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(items = viewModel.viewState.userList){
                UserItemRow(user = it){userModel ->
                    viewModel.onEvent(UserListEvent.DeleteUser(userModel))
                }
            }
        }
    }
}

@Composable
fun UserItemRow(user: User, onDelete: (User) -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
        ){
        AsyncImage(
            modifier = Modifier
                .padding(16.dp)
                .width(70.dp)
                .height(70.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .allowHardware(false)
                .data(user.imgUrl).build(),
            contentDescription = "")
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(2f)) {
            Text(text = user.userName, style = TextStyle(fontSize = 18.sp, color = Color.Black))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = user.email, style = TextStyle(fontSize = 18.sp, color = Color.Black))
        }
        IconButton(modifier = Modifier.weight(1f),
            onClick = { onDelete.invoke(user) }) {
            Icon(painterResource(id = R.drawable.ic_delete), contentDescription = "")
        }
    }
}
