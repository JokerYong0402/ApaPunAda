package com.example.apapunada.ui.users

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedDate
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onProfile: () -> Unit,
    onBackButtonClicked: () -> Unit
    ) {
    var userState = viewModel.userState.collectAsState(initial = UserState())
    viewModel.loadUserByUserId(3)

    var user = userState.value.user

    //Log.i("Profile", "EditProfileScreen: " + user)
    val imageUrl = rememberSaveable{mutableStateOf("")}
    var editedname by remember { mutableStateOf("") }
    var editedgender by remember { mutableStateOf("") }
    var editeddob by remember { mutableLongStateOf(user.dob) }
    var editedemail by remember { mutableStateOf("") }
    var editedpassword by remember { mutableStateOf("") }
    var editedphonenum by remember { mutableStateOf("") }

    var openAlertDialog by remember { mutableStateOf(false) }
    val maxCharPhoneNum = 12

    val context = LocalContext.current

    var expandedChangeGender by remember { mutableStateOf(false) }
    val options = listOf("Male","Female")
    var changeGender by remember { mutableStateOf("") }

    if (user != null){
        editedname = user.username
        editedgender = user.gender
        editeddob = user.dob
        editedemail = user.email
        editedpassword = user.password
        editedphonenum = user.phoneNo
    }

    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.edit_profile), onBackButtonClicked = onBackButtonClicked) },
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
                Column (
                    modifier = Modifier.padding(top = 10.dp)
                ){
                    ChangeProfilePic()
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
                                label = { Text(text = "Gender") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedChangeGender)
                                },
                                colors = OutlinedTextFieldDefaults.colors(),
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
                         editeddob = DatePickerDialog(context, user)

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
                                isValidEmail(editedemail)
                            },
                            modifier = Modifier
                        )
                        if (!isValidEmail(editedemail)) {
                            Text(
                                text = "Please enter a valid email address",
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
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
                            //    .fillMaxWidth()
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
                            //.fillMaxSize()
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
                            onValueChange = { if(it.length <= maxCharPhoneNum) editedphonenum = it },
                            modifier = Modifier
                        )
                    }
                }

                Button(
                    onClick = {
                        val latestUser = User(
                            image = imageUrl.value,
                            userID = user.userID,
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

                        openAlertDialog = true

                              },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.primary)
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(65.dp)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)

                ) {
                    Text(
                        text = stringResource(R.string.save),
                        fontSize = 18.sp
                    )
                }

            }


        }

    }
}

@Composable
fun ChangeProfilePic() {
    val imageUrl = rememberSaveable{mutableStateOf("")}
    val painter = rememberImagePainter(
        if (imageUrl.value.isEmpty())
        R.drawable.ic_android_black_24dp
        else
        imageUrl.value
    )
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        uri: Uri? ->
        uri?.let { imageUrl.value = it.toString() }

    }
    Column(
        modifier = Modifier
            //.padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                //.padding(8.dp)
                .size(80.dp)

        ){
            Image(
                painter = painter,
                contentDescription = "",
                 modifier = Modifier
                     .wrapContentSize(),
                contentScale = ContentScale.Crop
            )

        }
        Text(
            text = "Change profile picture",
            color = colorResource(R.color.primary),
            fontSize = 14.sp,
            modifier = Modifier
                .clickable { launcher.launch("image/*") }
        )
    }
}


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
        modifier = modifier
            .height(120.dp)
            .width(300.dp),
        label = {
            Text(text = textlabel)
                },
        )
}

@Composable
fun ReadonlyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: TextFieldColors,
) {
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label,
            colors = colors,
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
        //textStyle = androidx.compose.ui.text.TextStyle(fontSize = 15.sp),
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
fun convertDateToLong(dateString: String): Long {
    val format = SimpleDateFormat("dd-MM-yyyy") // Specify the date format
    format.timeZone = TimeZone.getTimeZone("UTC")
    val date = format.parse(dateString)
    return date.time
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
    return emailRegex.matches(email)
}

//@Preview(showBackground = true)
//@Composable
//fun EditProfileScreenPreview() {
//    EditProfileScreen(
//        onProfile = {}
//    )
//}