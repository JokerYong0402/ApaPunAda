package com.example.apapunada.ui.users

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserViewModel

@Composable
fun DeleteProfileScreen(
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onLogin: () -> Unit,
    onBackButtonClicked: () -> Unit
) {
    var textInputDelete by remember { mutableStateOf("") }
    var openAlertDialog by remember { mutableStateOf(false) }
    val dltAble by remember { mutableStateOf(true) }

    val userState = viewModel.userState.collectAsState(initial = UserState())
    viewModel.loadUserByUserId(3)

    var user = userState.value.user

    val currentUser by remember { mutableStateOf(User()) }

    if(openAlertDialog) {
        DeleteProfileAlertDialog(
            onDismissRequest = { openAlertDialog = false },
            onConfirmation = { deleteUser ->
                viewModel.updateUserState(deleteUser)

                viewModel.updateUser()

                openAlertDialog = false
            },
            dialogTitle = "Confirmation",
            dialogText = "Are you sure want to delete this account?",
            onLogin = onLogin,
            user = user
        )
    }

    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.delete_profile),onBackButtonClicked = onBackButtonClicked) },
        //bottomBar = { MyBottomNavBar() }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
        {
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_large))
                        .height(230.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ){
                    Text(
                        text = "We are really sad to see you go. Our goals has always " +
                                "been to serve you better.If you are experiencing any " +
                                "issues, we are here to help and would love to hear from " +
                                "you so that we can provide you a better service.\n\n",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Please take note of these permanent changes when deleting your account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp, vertical = 0.dp)
                        .height(270.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ){
                    Text(
                        text = "1. Your existing Apa Pun Ada Points and will be lost. If you join us again in the future, the Apa Pun Ada Points and in your previous account can't be reinstated." +
                                "\n\n" +
                                "2. Your progress on Loyalty Program will be lot. If you join us again in the future, your previous Loyalty Level can't be reinstated." +
                                "\n\n" +
                                "3. Your account will be inaccessible, unrecoverable,and permanently not available.",
                        fontSize = 13.sp
                    )

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_large))
                        .height(50.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ){
                    Text(
                        text = "If you would still like to delete your Apa Pun Ada account, please submit your request below:",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold

                    )

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small))
                        .height(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    DeleteAcctComment(
                        value = textInputDelete,
                        onValueChange = { textInputDelete = it },
                        modifier = Modifier
                        .fillMaxWidth()
                    )
                }
                Button(
                    onClick = {


                        openAlertDialog = true
                              },
                    enabled = dltAble,
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.primary)
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(250.dp)
                        .height(70.dp)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(R.string.submit),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}


@Composable
fun DeleteAcctComment(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(/* TODO  text box error when typing not same place with placeholder*/
        value = value,
        onValueChange = onValueChange,
        minLines = 1,
        maxLines = 5,
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = Color.White)
            .height(160.dp)
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    size = 20.dp,
                ),
            )
            .border(
                BorderStroke(width = 2.dp, colorResource(id = R.color.purple_500)),
                shape = RoundedCornerShape(
                    size = 20.dp,
                )
            )
        ,
        placeholder = {
            Text(
                text = "Give us some feedback...",
                fontSize = 14.sp,
                color = colorResource(id = R.color.black),
                modifier = modifier
                .padding(bottom = 60.dp)
            )
        },

    )
}


@Composable
fun DeleteProfileAlertDialog(
    //viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onDismissRequest: () -> Unit,
    onConfirmation: (User) -> Unit,
    user: User,
    dialogTitle: String,
    dialogText: String,
    onLogin: () -> Unit
) {
    //var userState = viewModel.userState.collectAsState(initial = UserState())
    //var user = userState.value.user
    val imageUrl = rememberSaveable{mutableStateOf(user.image)}
    val deletedStatus = "Deleted"

    val context = LocalContext.current
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val deleteUser = User(
                        image = imageUrl.value,
                        userID = user.userID,
                        username = user.username,
                        email = user.email,
                        password = user.password,
                        phoneNo = user.phoneNo,
                        gender = user.gender,
                        dob = user.dob,
                        role = user.role,
                        point = user.point,
                        status = "Deleted"
                    )

                    Toast.makeText(context, "Deleted Successfully",Toast.LENGTH_SHORT).show()
                    onConfirmation(deleteUser)
                    onLogin()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}


//@Preview(showBackground = true)
//@Composable
//fun DeleteProfileScreenPreview() {
//    DeleteProfileScreen(
//    onLogin = {}
//    )
//}
