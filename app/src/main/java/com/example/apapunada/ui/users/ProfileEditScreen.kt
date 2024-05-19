package com.example.apapunada.ui.users

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DisplayImagesFromByteArray
import com.example.apapunada.ui.components.MyDatePickerDialog
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.uriToByteArray
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    authViewModel: AuthViewModel,
    onProfile: () -> Unit,
    onBackButtonClicked: () -> Unit
    ) {
    val user = authViewModel.userState.value.user

    var editedname by remember { mutableStateOf(user.username) }
    var editedgender by remember { mutableStateOf(user.gender) }
    var editeddob by remember { mutableStateOf(user.dob) }
    var editedemail by remember { mutableStateOf(user.email) }
    var editedpassword by remember { mutableStateOf(user.password) }
    var editedphonenum by remember { mutableStateOf(user.phoneNo) }

    var openAlertDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var expandedChangeGender by remember { mutableStateOf(false) }
    val options = listOf("Male", "Female")
    var changeGender by remember { mutableStateOf("") }

    var checkImage by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val multiplePhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri ?: Uri.EMPTY
        }
    )


    var byteArray = uriToByteArray(context, selectedImageUri)
    if (selectedImageUri == Uri.EMPTY){
        byteArray = user.image
    }

//    if (!checkImage && selectedImageUri == Uri.EMPTY){
//        byteArray = user.image
//        checkImage = true
//    } else if(checkImage && selectedImageUri != Uri.EMPTY){
//        byteArray = user.image
//
//    }

    val nonNullableByteArray = byteArray?:ByteArray(0)



    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.edit_profile), onBackButtonClicked = onBackButtonClicked) },
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
                Column (
                   horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ){
                    DisplayImagesFromByteArray(
                        byteArray = nonNullableByteArray,
                        modifier = Modifier.size(100.dp),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Change profile picture",
                        color = colorResource(R.color.primary),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable {
                                multiplePhotosPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                    )
                }


                Row(//EDIT NAME
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.nameicon),
                        contentDescription = "Name Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            //.fillMaxSize()
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        EditTextFieldProfile(
                            value = editedname,
                            textlabel = "Name",
                            onValueChange = { editedname = it },
                            modifier = Modifier
                        )
                    }
                }

                Row(//EDIT GENDER
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.gendericon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        ExposedDropdownMenuBox(
                            expanded = expandedChangeGender,
                            onExpandedChange = { expandedChangeGender = !expandedChangeGender },
                            modifier = Modifier
                        ) {
                            OutlinedTextField(
                                readOnly = true,
                                value = editedgender,
                                onValueChange = { editedgender = it },
                                shape = RoundedCornerShape(15.dp),
                                label = { Text(text = "Gender") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedChangeGender)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorResource(R.color.primary),
                                    unfocusedBorderColor = colorResource(R.color.primary),
                                ),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(expanded = expandedChangeGender, onDismissRequest = { expandedChangeGender = false }) {
                                options.forEach { option: String ->
                                    DropdownMenuItem(
                                        text = { Text(text = option) },
                                        onClick = {
                                            expandedChangeGender = false
                                            editedgender = option
                                            changeGender = option
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Row(//EDIT DATE OF BIRTH
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.dobicon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Log.i("User", "ProfileEditScreen1: $editeddob")
                         editeddob = MyDatePickerDialog(context, user)
                        Log.i("User", "ProfileEditScreen2: $editeddob")
                    }
                }

                Row(//EDIT EMAIL ADDRESS
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        ,

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.emailicon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        EditTextFieldProfile(
                            value = editedemail,
                            textlabel = "Email Address",
                            onValueChange = {
                                editedemail = it
                            },
                            modifier = Modifier
                        )
                    }
                }

                Row(//EDIT PASSWORD
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.passwordicon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        EditTextFieldProfile(
                            value = editedpassword,
                            textlabel = "Password",
                            onValueChange = { editedpassword = it },
                            modifier = Modifier
                        )
                    }
                }

                Row(//EDIT PHONE NUMBER
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.phonenumbericon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        EditTextFieldProfile(
                            value = editedphonenum,
                            textlabel = "Phone Number",
                            onValueChange = { editedphonenum = it },
                            modifier = Modifier
                        )
                    }
                }

                Button(
                    onClick = {
                        val latestUser = User(
//                            image = imageUrl.value, TODO
                            userID = user.userID,
                            image = nonNullableByteArray,
                            username = editedname,
                            email = editedemail,
                            password = editedpassword,
                            phoneNo = editedphonenum,
                            gender = editedgender,
                            dob = editeddob,
                            role = user.role,
                            point = user.point,
                            status = user.status
                        )
                        viewModel.updateUserState(latestUser)

                        viewModel.updateUser()
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        onProfile()
                        openAlertDialog = true

                              },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.primary)
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(350.dp)
                        .height(65.dp)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)

                ) {
                    Text(
                        text = stringResource(R.string.save),
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

            }


        }

    }
}

//@Composable
//fun ChangeProfilePic() {
//    val imageUrl = rememberSaveable{mutableStateOf("")}
//    val painter = rememberImagePainter(
//        if (imageUrl.value.isEmpty())
//        R.drawable.ic_android_black_24dp
//        else
//        imageUrl.value
//    )
//    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
//        uri: Uri? ->
//        uri?.let { imageUrl.value = it.toString() }
//
//    }
//    Column(
//        modifier = Modifier
//            //.padding(8.dp)
//            .fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Card(
//            shape = CircleShape,
//            modifier = Modifier
//                //.padding(8.dp)
//                .size(80.dp)
//
//        ){
//            Image(
//                painter = painter,
//                contentDescription = "",
//                 modifier = Modifier
//                     .wrapContentSize(),
//                contentScale = ContentScale.Crop
//            )
//
//        }
//        Text(
//            text = "Change profile picture",
//            color = colorResource(R.color.primary),
//            fontSize = 14.sp,
//            modifier = Modifier
//                .clickable { launcher.launch("image/*") }
//        )
//    }
//}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditTextFieldProfile(
    value: String,
    textlabel: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.primary),
            unfocusedBorderColor = colorResource(R.color.primary),
        ),
        modifier = modifier
            .height(120.dp)
            .width(300.dp),
        label = {
            Text(text = textlabel)
                },
        )
}



//@Preview(showBackground = true)
//@Composable
//fun ProfileEditScreenPreview() {
//    ProfileEditScreen(
//        onProfile = {}
//    )
//}