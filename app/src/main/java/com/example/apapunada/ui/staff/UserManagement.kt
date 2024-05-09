package com.example.apapunada.ui.staff

import android.widget.DatePicker
import android.app.DatePickerDialog
import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
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
import coil.compose.rememberImagePainter
import com.example.apapunada.R
import com.example.apapunada.data.UserSample.Users
import com.example.apapunada.model.User
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserManagement(
    users: List<User> = Users
) {
    var search by remember { mutableStateOf("") }
    var editButton by remember { mutableStateOf(false) }
    var detailButton by remember { mutableStateOf(false) }
    var currentUser by remember { mutableStateOf(users[0]) }

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

    if (editButton) {
        DialogOfEditUser(
            onDismissRequest = { editButton = false },
            onConfirmation = {
                editButton = false
                // TODO save data
            },
            user = currentUser
        )
    }

    if (detailButton) {
        DialogOfUserDetail(
            onDismissRequest = { detailButton = false },
            user = currentUser
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
    ) {
        Text(
            text = "User Management",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        TextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .padding(bottom = 10.dp),
            singleLine = true,
            placeholder = { Text(text = "Search") },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search Icon"
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
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
                        text = user.id.toString(),
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[0].second)
                            .padding(start = 20.dp)
                    )
                    Row(
                        modifier = Modifier.width(headerList[1].second)
                    ) {

                        Image(
                            painter = painterResource(user.image),
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
                                currentUser = getUser(i)
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
                                currentUser = getUser(i)
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
fun DialogOfEditUser(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit,
    user: User,
) {
    val userGender = listOf("Male", "Female")
    val userStatus = listOf("Active", "Disabled")
    var editedStatus by remember { mutableStateOf(user.status) }
    var editedName by remember { mutableStateOf(user.username) }
    var editedPhone by remember { mutableStateOf(user.phoneNo) }
    var editedGender by remember { mutableStateOf(user.gender) }
    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val date = remember { mutableStateOf(user.dob) }
    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,
        {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth-${month+1}-$year"
        }, year, month, day
    )

    var expandedS by remember { mutableStateOf(false) }
    var expandedG by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(user.status) }
    var selectedGender by remember { mutableStateOf(user.gender) }

    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())
            user.image
        else
            imageUri.value
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
                    Image(
                        painter = painter,
                        contentDescription = "User Profile Pic",
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(70.dp)
                            .clickable { launcher.launch("image/*") }
                    )
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Username") },
                        modifier = Modifier.size(width = 280.dp, height = 55.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary)
                        )
                    )
                    OutlinedTextField(
                        value = user.email,
                        onValueChange = { },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Email") },
                        modifier = Modifier.size(width = 280.dp, height = 55.dp),
                        readOnly = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFadadad),
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary)
                        )
                    )
                    OutlinedTextField(
                        value = user.password,
                        onValueChange = { },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Password") },
                        modifier = Modifier.size(width = 280.dp, height = 55.dp),
                        readOnly = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFadadad),
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary)
                        )
                    )
                    OutlinedTextField(
                        value = editedPhone,
                        onValueChange = { editedPhone = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Phone Number") },
                        modifier = Modifier.size(width = 280.dp, height = 55.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary)
                        )
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedG,
                        onExpandedChange = { expandedG = !expandedG }
                    ) {
                        OutlinedTextField(
                            value = editedGender,
                            onValueChange = { editedGender = it },
                            textStyle = TextStyle(fontSize = 15.sp),
                            label = { Text("Gender") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedG)
                            },
                            modifier = Modifier
                                .size(width = 280.dp, height = 55.dp)
                                .menuAnchor(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedG,
                            onDismissRequest = { /*TODO*/ }
                        ) {
                            userGender.forEach { gender ->
                                DropdownMenuItem(
                                    text = { Text(text = gender) },
                                    onClick = {
                                        selectedGender = gender
                                        expandedG = false
                                        editedGender = gender
                                    }
                                )
                            }
                        }
                    }
                    ReadonlyTextField(
                        value = date.value,
                        onValueChange = { date.value = it },
                        textStyle = TextStyle(fontSize = 15.sp),
                        label = { Text("Date of Birth") },
                        modifier = Modifier
                            .size(width = 280.dp, height = 55.dp),
                        onClick = { datePickerDialog.show() },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary)
                        )
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedS,
                        onExpandedChange = { expandedS = !expandedS }
                    ) {
                        OutlinedTextField(
                            value = editedStatus,
                            onValueChange = { editedStatus = it },
                            textStyle = TextStyle(fontSize = 15.sp),
                            label = { Text("Status") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedS)
                            },
                            modifier = Modifier
                                .size(width = 280.dp, height = 55.dp)
                                .menuAnchor(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedS,
                            onDismissRequest = { /*TODO*/ }
                        ) {
                            userStatus.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(text = status) },
                                    onClick = {
                                        selectedStatus = status
                                        expandedS = false
                                        editedStatus = status
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

                    TextButton(onClick = { onConfirmation() }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
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
                Image(
                    painter = painterResource(user.image),
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(70.dp)
                )
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
                                text = dob,
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

private fun getUser(
    index: Int,
    users: List<User> = Users
): User {
    return users[index]
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun UserManagementPreview() {
    UserManagement()
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun UserManagementPhonePreview() {
    UserManagement()
}