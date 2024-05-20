package com.example.apapunada.ui.staff

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DisplayImagesFromByteArray
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.MyDatePickerDialog
import com.example.apapunada.ui.components.SearchBar
import com.example.apapunada.ui.components.drawableResourceToByteArray
import com.example.apapunada.ui.components.formattedDate
import com.example.apapunada.ui.components.uriToByteArray
import com.example.apapunada.viewmodel.Gender
import com.example.apapunada.viewmodel.UserListState
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserStatus
import com.example.apapunada.viewmodel.UserViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffUserScreen(
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val userState = viewModel.userState.collectAsState(initial = UserState())
    val userListState = viewModel.userListState.collectAsState(initial = UserListState())
    var users: List<User> = listOf()

    viewModel.loadAllUsers()

    if (userListState.value.isLoading) {
        IndeterminateCircularIndicator("Loading user...")
    } else {
        if (userListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading users: ${userListState.value.errorMessage}")
            Log.i("User", "StaffUserScreen: ${userListState.value.errorMessage}")
        } else {
            users = userListState.value.userList
        }
    }

    val context = LocalContext.current
    var search by remember { mutableStateOf("") }
    var addButton by remember { mutableStateOf(false) }
    var editButton by remember { mutableStateOf(false) }
    var detailButton by remember { mutableStateOf(false) }
    var currentUser by remember { mutableStateOf(User()) }
    var onConfirmAdd by remember { mutableStateOf(false) }
    var onConfirmEdit by remember { mutableStateOf(false) }
    var editedUser by remember { mutableStateOf(User()) }
    var addUser by remember { mutableStateOf(User()) }
    var launchAll by remember { mutableStateOf(false) }
    var launchUser by remember { mutableStateOf(false) }

    val headerList = listOf(
        // (Header name, Column width)
        Pair("  No.", 80.dp),
        Pair("Username", 300.dp),
        Pair("Email", 250.dp),
        Pair("Phone No.", 200.dp),
        Pair("Points", 130.dp),
        Pair("Status", 150.dp),
        Pair("Action", 150.dp)
    )

    if (addButton) {
        DialogOfAddUser(
            onDismissRequest = { addButton = false },
            onConfirmation = {
                addUser = it
                onConfirmAdd = true
                addButton = false
            },
            user = User()
        )
    }

    if (editButton) {
        DialogOfEditUser(
            onDismissRequest = { editButton = false },
            onConfirmation = {
                editedUser = it
                onConfirmEdit = true
                editButton = false
            },
            user = currentUser
        )
    }

    if (onConfirmAdd) {
        onConfirmAdd = false
        val validate = viewModel.validateInput()
        Log.i("Check Add User State", "UserScreen: $addUser")
        viewModel.updateUserState(addUser)
        if (!validate) {
            LaunchedEffect(userState) {
                Log.i("Add Updated User State", "UserScreen: ${userState}")
                if (userState.value.errorMessage.isNotEmpty()) {
                    Log.i("Check User Error Message", "UserScreen: Invalid Username $addUser")
                    Toast.makeText(context, userState.value.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.saveUser()
    }

    if (onConfirmEdit) {
        onConfirmEdit = false
        val validate = viewModel.validateInput()
        Log.i("Check Edit User State", "UserScreen: $editedUser")
        viewModel.updateUserState(editedUser)
        if (!validate) {
            LaunchedEffect(userState) {
                Log.i("Edit Updated User State", "UserScreen: ${userState}")
                if (userState.value.errorMessage.isNotEmpty()) {
                    Log.i("Check User Error Message", "UserScreen: Invalid Username $editedUser")
                    Toast.makeText(context, userState.value.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.updateUser()
    }

    if (detailButton) {
        DialogOfUserDetail(
            onDismissRequest = { detailButton = false },
            user = currentUser
        )
    }

    if (launchAll) {
        viewModel.loadAllUsers()
        launchAll = false
    }

    if (launchUser) {
        viewModel.loadUsersByName(search)
        launchUser = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
    ) {
        SearchBar(
            value = search,
            onValueChange = { search = it },
        )
        Button(
            onClick = {
                addButton = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary)
            )
        ) {
            Text(text = "Add User")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
        ) {
            // header row
            stickyHeader {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            colorResource(R.color.primary_200),
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    headerList.forEach { header ->
                        Text(
                            text = header.first,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .width(header.second)
                        )
                    }
                }
            }
            if (search == "") {
                launchAll = true
                items(users.size) { i ->
                    val user = users[i]
                    val image by remember { mutableStateOf(user.image) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Text(
                            text = user.userID.toString(),
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[0].second)
                                .padding(start = 20.dp)
                        )
                        Row(
                            modifier = Modifier.width(headerList[1].second)
                        ) {
                            Card(
                                shape = CircleShape,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .size(70.dp)
                            ){
                                DisplayImagesFromByteArray(
                                    byteArray =
                                    if (image.isNotEmpty()) image
                                    else drawableResourceToByteArray(context, R.drawable.defaultprofilepicture),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Text(
                                text = user.username,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 5.dp)
                            )
                        }
                        Text(
                            text = user.email,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[2].second)
                        )

                        Row {
                            Text(
                                text = user.phoneNo,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(headerList[3].second)
                            )
                        }

                        Text(
                            text = user.point.toString(),
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[4].second)
                        )

                        Text(
                            text = user.status,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[5].second)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.width(headerList[6].second)
                        ) {
                            IconButton(
                                onClick = {
                                    currentUser = user
                                    editButton = true
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.editicon),
                                    contentDescription = "Edit button",
                                    modifier = Modifier.size(30.dp)
                                )

                            }
                            IconButton(
                                onClick = {
                                    currentUser = user
                                    detailButton = true
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.emailicon),
                                    contentDescription = "Detail button",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                launchUser = true
                if (userListState.value.userList.isNotEmpty()) {
                    items(userListState.value.userList.size) { i ->
                        val user = users[i]
                        val image by remember { mutableStateOf(user.image) }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        ) {
                            Text(
                                text = user.userID.toString(),
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(headerList[0].second)
                                    .padding(start = 20.dp)
                            )
                            Row(
                                modifier = Modifier.width(headerList[1].second)
                            ) {
                                Card(
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp)
                                        .size(70.dp)
                                ) {
                                    DisplayImagesFromByteArray(
                                        byteArray =
                                        if (image.isNotEmpty()) image
                                        else drawableResourceToByteArray(context, R.drawable.defaultprofilepicture),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Text(
                                    text = user.username,
                                    fontSize = 22.sp,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                            Text(
                                text = user.email,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(headerList[2].second)
                            )

                            Row {
                                Text(
                                    text = user.phoneNo,
                                    fontSize = 22.sp,
                                    modifier = Modifier
                                        .width(headerList[3].second)
                                )
                            }

                            Text(
                                text = user.point.toString(),
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(headerList[4].second)
                            )

                            Text(
                                text = user.status,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(headerList[5].second)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.width(headerList[6].second)
                            ) {
                                IconButton(
                                    onClick = {
                                        currentUser = user
                                        editButton = true
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.editicon),
                                        contentDescription = "Edit button",
                                        modifier = Modifier.size(30.dp)
                                    )

                                }
                                IconButton(
                                    onClick = {
                                        currentUser = user
                                        detailButton = true
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.emailicon),
                                        contentDescription = "Detail button",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                else {
                    items(1) { _ ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        ) {
                            Text(
                                text = "No User Found!!!",
                                fontSize = 22.sp,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogOfAddUser(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (User) -> Unit,
    user: User
) {
    var username by remember { mutableStateOf(user.username) }
    var email by remember { mutableStateOf(user.email) }
    var password by remember { mutableStateOf(user.password) }
    var phoneNo by remember { mutableStateOf(user.phoneNo) }
    var gender by remember { mutableStateOf(user.gender) }
    var dob by remember { mutableStateOf(user.dob) }
    val context = LocalContext.current
    val userGender: List<Gender> = enumValues<Gender>().toList()
    var expandedG by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val byteArray = uriToByteArray(context, imageUri)
    val userImage = byteArray ?: ByteArray(0)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri: Uri? ->
            imageUri = uri ?: Uri.EMPTY
        }
    )

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {

                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(70.dp)
                    ){
                        DisplayImagesFromByteArray(
                            byteArray =
                            if (imageUri != Uri.EMPTY) userImage
                            else drawableResourceToByteArray(context, R.drawable.defaultprofilepicture),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { launcher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                ) },
                            contentScale = ContentScale.Crop
                        )
                    }
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Username") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Email") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Password") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )
                    OutlinedTextField(
                        value = phoneNo,
                        onValueChange = { phoneNo = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Phone Number") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedG,
                        onExpandedChange = { expandedG = !expandedG }
                    ) {
                        OutlinedTextField(
                            value = gender,
                            onValueChange = { gender = it },
                            textStyle = TextStyle(fontSize = 15.sp),
                            label = { Text("Gender") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedG)
                            },
                            modifier = Modifier
                                .size(width = 280.dp, height = 60.dp)
                                .menuAnchor(),
                            shape = RoundedCornerShape(15.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedG,
                            onDismissRequest = {  }
                        ) {
                            userGender.forEach {
                                Log.i("Check Gender", "DialogOfAddUser: "+it.fullName)
                                DropdownMenuItem(
                                    text = { Text(text = it.fullName) },
                                    onClick = {
                                        gender = it.fullName
                                        expandedG = false
                                    }
                                )
                            }
                        }
                    }
                    dob = MyDatePickerDialog(context, user)
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Dismiss")
                    }
                    TextButton(onClick = {
                        onConfirmation(
                            User(
                                image = userImage,
                                userID = user.userID,
                                username = username,
                                email = email,
                                password = password,
                                phoneNo = phoneNo,
                                gender = gender,
                                dob = dob,
                                role = "User",
                                point = 0,
                                status = "Active",
                            )
                        )
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogOfEditUser(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (User) -> Unit,
    user: User
) {
    var username by remember { mutableStateOf(user.username) }
    val email by remember { mutableStateOf(user.email) }
    val password by remember { mutableStateOf(user.password) }
    var phoneNo by remember { mutableStateOf(user.phoneNo) }
    var gender by remember { mutableStateOf(user.gender) }
    var dob by remember { mutableStateOf(user.dob) }
    var point by remember { mutableStateOf(user.point) }
    var status by remember { mutableStateOf(user.status) }
    val image by remember { mutableStateOf(user.image) }
    val role by remember { mutableStateOf(user.role) }

    var imageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }

    val byteArray = uriToByteArray(LocalContext.current, imageUri)
    var userImage = byteArray ?: ByteArray(0)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri: Uri? ->
            imageUri = uri ?: Uri.EMPTY
        }
    )

    val userGender: List<Gender> = enumValues<Gender>().toList()
    val userStatus: List<UserStatus> = enumValues<UserStatus>().toList()
    var expandedS by remember { mutableStateOf(false) }
    var expandedG by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {

                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(70.dp)
                    ) {
                        Log.i("Check User Image", "DialogOfEditUser: "+userImage.size)
                        DisplayImagesFromByteArray(
                            byteArray =
                            if (imageUri != Uri.EMPTY) userImage
                            else if (image.isNotEmpty()) image
                            else drawableResourceToByteArray(context, R.drawable.defaultprofilepicture),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { launcher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                ) },
                            contentScale = ContentScale.Crop
                        )
                    }
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Username") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )
                    OutlinedTextField(
                        value = user.email,
                        onValueChange = { },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Email") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        readOnly = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )
                    OutlinedTextField(
                        value = user.password,
                        onValueChange = { },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Password") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        readOnly = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )
                    OutlinedTextField(
                        value = phoneNo,
                        onValueChange = { phoneNo = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Phone Number") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedG,
                        onExpandedChange = { expandedG = !expandedG }
                    ) {
                        OutlinedTextField(
                            value = gender,
                            onValueChange = { gender = it },
                            textStyle = TextStyle(fontSize = 15.sp),
                            label = { Text("Gender") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedG)
                            },
                            modifier = Modifier
                                .size(width = 280.dp, height = 60.dp)
                                .menuAnchor(),
                            shape = RoundedCornerShape(15.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedG,
                            onDismissRequest = {  }
                        ) {
                            userGender.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it.fullName) },
                                    onClick = {
                                        gender = it.fullName
                                        expandedG = false
                                    }
                                )
                            }
                        }
                    }
                    dob = MyDatePickerDialog(context, user)
                    OutlinedTextField(
                        value = point.toString(),
                        onValueChange = { point = it.toInt() },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Point") },
                        readOnly = true,
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
                        shape = RoundedCornerShape(15.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedS,
                        onExpandedChange = { expandedS = !expandedS }
                    ) {
                        OutlinedTextField(
                            value = status,
                            onValueChange = { status = it },
                            textStyle = TextStyle(fontSize = 15.sp),
                            label = { Text("Status") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedS)
                            },
                            modifier = Modifier
                                .size(width = 280.dp, height = 60.dp)
                                .menuAnchor(),
                            shape = RoundedCornerShape(15.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedS,
                            onDismissRequest = {  }
                        ) {
                            userStatus.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it.fullName) },
                                    onClick = {
                                        status = it.fullName
                                        expandedS = false
                                    }
                                )
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Dismiss")
                    }
                    TextButton(onClick = {
                        if (userImage.isEmpty()) {
                            userImage = user.image
                        }
                        onConfirmation(
                            User(
                                image = userImage,
                                userID = user.userID,
                                username = username,
                                email = email,
                                password = password,
                                phoneNo = phoneNo,
                                gender = gender,
                                dob = dob,
                                role = role,
                                point = point,
                                status = status,
                            )
                        )
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun DialogOfUserDetail(
    onDismissRequest: () -> Unit = {},
    user: User,
) {
    val context = LocalContext.current
    val image = user.image
    val username = user.username
    val email = user.email
    val password = user.password
    val phoneNumber = user.phoneNo
    val gender = user.gender
    val dob = user.dob
    val points = user.point
    val status = user.status

    val userHeader = listOf(
        // (Header name, Column width)
        Pair("Username", 100.dp),
        Pair("Email", 100.dp),
        Pair("Password", 100.dp),
        Pair("Phone Number", 100.dp),
        Pair("Gender", 100.dp),
        Pair("Date of Birth", 100.dp),
        Pair("Points", 100.dp),
        Pair("Status", 100.dp)
    )

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(70.dp)

                ){
                    DisplayImagesFromByteArray(
                        byteArray =
                        if (image.isNotEmpty()) image
                        else drawableResourceToByteArray(context, R.drawable.defaultprofilepicture),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier
                            .width(150.dp)
                    ) {
                        Column {
                            Text(
                                text = userHeader[0].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = username,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(
                                text = userHeader[1].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = email,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(
                                text = userHeader[2].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = password,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(
                                text = userHeader[3].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = phoneNumber,
                                fontSize = 20.sp
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier.width(150.dp)
                    ) {
                        Column {
                            Text(
                                text = userHeader[4].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = gender,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(
                                text = userHeader[5].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = formattedDate(dob, "date"),
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(
                                text = userHeader[6].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = points.toString(),
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(
                                text = userHeader[7].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = status,
                                fontSize = 20.sp
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Dismiss")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun StaffUserScreenPreview() {
    StaffUserScreen()
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun StaffUserScreenPhonePreview() {
    StaffUserScreen()
}