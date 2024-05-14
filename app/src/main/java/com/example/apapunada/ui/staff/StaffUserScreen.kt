package com.example.apapunada.ui.staff

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.formattedDate
import com.example.apapunada.viewmodel.Gender
import com.example.apapunada.viewmodel.UserListState
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserStatus
import com.example.apapunada.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffUserScreen(
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var userState = viewModel.userState.collectAsState(initial = UserState())
    val userListState = viewModel.userListState.collectAsState(initial = UserListState())
    var users: List<User> = listOf()

    viewModel.loadAllUsers()

    if (userListState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.5f))
                .clickable { /* no action */ }
                .zIndex(2f)
            ,
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularIndicator()
        }
    } else {
        if (userListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading users: ${userListState.value.errorMessage}")
            Log.i("User", "StaffUserScreen: ${userListState.value.errorMessage}")
        } else {
            users = userListState.value.userList
        }
    }

    var search by remember { mutableStateOf("") }
    var addButton by remember { mutableStateOf(false) }
    var editButton by remember { mutableStateOf(false) }
    var detailButton by remember { mutableStateOf(false) }
    var currentUser by remember { mutableStateOf(User()) }

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
                viewModel.updateUserState(it)
                viewModel.saveUser()
                addButton = false
            },
            user = User()
        )
    }

    if (editButton) {
        DialogOfEditUser(
            onDismissRequest = { editButton = false },
            onConfirmation = {
                viewModel.updateUserState(it)
                viewModel.updateUser()
                editButton = false
            },
            user = currentUser
        )
    }

    if (detailButton) {
        DialogOfUserDetail(
            onDismissRequest = { detailButton = false },
            user = currentUser //TODO
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
    ) {
        TextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .padding(bottom = 10.dp),
            singleLine = true,
            placeholder = { Text(text = "Search") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            leadingIcon = {
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search Icon"
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
        Button(onClick = {
            addButton = true
        }) {
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

            items(users.size) { i ->
                val user = users[i]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Text(
                        text = user.userID.toString(), //TODO
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[0].second)
                            .padding(start = 20.dp)
                    )
                    Row(
                        modifier = Modifier.width(headerList[1].second)
                    ) {

                        Image(
                            painter = painterResource(R.drawable.ic_android_black_24dp), // TODO
                            contentDescription = "userImage",
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .size(50.dp)
                        )
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
//                        Icon(painter = painterResource(R.id))
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
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(
        imageUri.value.ifEmpty { user.image }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

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
                        Image(
                            painter = painter,
                            contentDescription = "",
                            modifier = Modifier
                                .clickable { launcher.launch("image/*") },
                            contentScale = ContentScale.Crop
                        )
                    }
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Username") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
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
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedG,
                            onDismissRequest = { /*TODO*/ }
                        ) {
                            userGender.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it.name) },
                                    onClick = {
                                        gender = it.name
                                        expandedG = false
                                    }
                                )
                            }
                        }
                    }
                    dob = DatePickerDialog(context, user)
                }

                // Buttons
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
                                image = imageUri.value,
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
    var email by remember { mutableStateOf(user.email) }
    var password by remember { mutableStateOf(user.password) }
    var phoneNo by remember { mutableStateOf(user.phoneNo) }
    var gender by remember { mutableStateOf(user.gender) }
    var dob by remember { mutableStateOf(user.dob) }
    var point by remember { mutableStateOf(user.point) }
    var status by remember { mutableStateOf(user.status) }

    val userGender: List<Gender> = enumValues<Gender>().toList()
    val userStatus: List<UserStatus> = enumValues<UserStatus>().toList()
    var expandedS by remember { mutableStateOf(false) }
    var expandedG by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(
        imageUri.value.ifEmpty { user.image }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

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
                        Image(
                            painter = painter,
                            contentDescription = "",
                            modifier = Modifier
                                .clickable { launcher.launch("image/*") },
                            contentScale = ContentScale.Crop
                        )
                    }
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Username") },
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
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
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedG,
                            onDismissRequest = { /*TODO*/ }
                        ) {
                            userGender.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it.name) },
                                    onClick = {
                                        gender = it.name
                                        expandedG = false
                                    }
                                )
                            }
                        }
                    }
                    dob = DatePickerDialog(context, user)
                    OutlinedTextField(
                        value = point.toString(),
                        onValueChange = { point = it.toInt() },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Point") },
                        readOnly = true,
                        modifier = Modifier.size(width = 280.dp, height = 60.dp),
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
                                    text = { Text(text = it.name) },
                                    onClick = {
                                        status = it.name
                                        expandedS = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Buttons
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
                                image = imageUri.value,
                                userID = user.userID,
                                username = username,
                                email = email,
                                password = password,
                                phoneNo = phoneNo,
                                gender = gender,
                                dob = dob,
                                role = "User", // TODO
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

fun getStringToDate(date: String): Long {
    val l = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    val unix = l.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
    Log.i("Change to Date", "getStringToDate: "+unix)
    return unix
}
fun convertDateToLong(dateString: String): Long {
    val format = SimpleDateFormat("dd-MM-yyyy") // Specify the date format
    format.timeZone = TimeZone.getTimeZone("UTC")
    val date = format.parse(dateString)
    return date.time
}

@Composable
fun DatePickerDialog(
    context: Context,
    user: User
): Long {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var date by remember { mutableStateOf(user.dob) }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val newDate = "$dayOfMonth-${month+1}-$year"
            date = convertDateToLong(newDate)
        }, year, month, day
    )
    ReadonlyTextField(
        value = formattedDate(date, "date"),
        onValueChange = { date = convertDateToLong(it) },
        textStyle = TextStyle(fontSize = 15.sp),
        label = { Text("Date of Birth") },
        modifier = Modifier.size(width = 280.dp, height = 60.dp),
        onClick = { datePickerDialog.show() },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.primary),
            unfocusedBorderColor = colorResource(R.color.primary),
        )
    )
    return date
}

@Composable
fun ReadonlyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: TextFieldColors,
    textStyle: TextStyle
) {
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label,
            colors = colors,
            textStyle = textStyle
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}

@Composable
fun DialogOfUserDetail(
    onDismissRequest: () -> Unit = {},
    user: User,
) {
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

    val imageUri = rememberSaveable { mutableStateOf(user.image) }
    val painter = rememberAsyncImagePainter(
        imageUri.value.ifEmpty { user.image }
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
                    Image(
                        painter = painter,
                        contentDescription = "",
                        modifier = Modifier,
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

                // Buttons
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

//@Composable
//private fun reloadUserList(viewModel: UserViewModel): UserListState {
//    viewModel.loadAllUsers()
//    return viewModel.userListState
//}
//
//@Composable
//private fun getUserByUserId(viewModel: UserViewModel, id: Int): User {
//    var userState: UserState = UserState()
//    var isLoading by remember { mutableStateOf(true) }
//    LaunchedEffect(Unit) {
//        viewModel.loadUserByUserId(id)
//        delay(5000)
//        userState = viewModel.userState
//        Log.i("User", "getUserByUserId: $userState")
//        isLoading = false
//    }
//
//    if(isLoading) {
//        IndeterminateCircularIndicator()
//    }
//
//    return userState.user
//}

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