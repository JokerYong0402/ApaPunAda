package com.example.apapunada.ui.users

import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.MyDatePickerDialog
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.Gender
import com.example.apapunada.viewmodel.UserRole
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserStatus
import com.example.apapunada.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onBackButtonClicked: () -> Unit,
    onSignUpButtonClicked: () -> Unit,
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val newUserState = userViewModel.userState.collectAsState(initial = UserState())

    val context = LocalContext.current
    val primaryColor = colorResource(R.color.primary)
    val defaultGender = getEnumList(Gender::class.java)
    var passwordVisible by remember { mutableStateOf(false) }
    var invalidUser by remember { mutableStateOf(false) }
    var isVerifying by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var tnc by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf(0L) }

    if (isVerifying) {
        LaunchedEffect(Unit) {
            launch {
                delay(3000)
                if (newUserState.value.isValid) {
                    userViewModel.saveUser()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Created Successfully", Toast.LENGTH_SHORT).show()
                    }
                    onSignUpButtonClicked()
                } else {
                    invalidUser = true
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Invalid Input", Toast.LENGTH_SHORT).show()
                    }
                }
                isVerifying = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBackButtonClicked
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "1",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isVerifying) {
            IndeterminateCircularIndicator("Creating your account...")
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                // Logo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Nice to see you!",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Text(
                            text = "Create your account",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Input Field
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .width(325.dp)
                    ) {
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text(text = "Username") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Face,
                                    contentDescription = "username"
                                )
                            },
                            shape = RoundedCornerShape(15.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "Email") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Filled.Email, contentDescription = "email")
                            },
                            shape = RoundedCornerShape(15.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "Password") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "password"
                                )
                            },
                            trailingIcon = {
                                if (!passwordVisible) {
                                    IconButton(onClick = { passwordVisible = true }) {
                                        Icon(
                                            painter = painterResource(R.drawable.password_show),
                                            contentDescription = "Show password",
                                            modifier = Modifier.size(25.dp, 25.dp)
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { passwordVisible = false }) {
                                        Icon(
                                            painter = painterResource(R.drawable.password_hide),
                                            contentDescription = "Hide password",
                                            modifier = Modifier.size(25.dp, 25.dp)
                                        )
                                    }
                                }
                            },
                            shape = RoundedCornerShape(15.dp),
                            visualTransformation =
                            if (passwordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = phoneNo,
                            onValueChange = { phoneNo = it },
                            label = { Text(text = "Phone Number") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Phone,
                                    contentDescription = "phoneNo"
                                )
                            },
                            shape = RoundedCornerShape(15.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.primary),
                                unfocusedBorderColor = colorResource(R.color.primary),
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = gender,
                                onValueChange = { gender = it },
                                textStyle = TextStyle(fontSize = 15.sp),
                                label = { Text("Gender") },
                                readOnly = true,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = "gender"
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorResource(R.color.primary),
                                    unfocusedBorderColor = colorResource(R.color.primary),
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { }
                            ) {
                                defaultGender.forEach {
                                    DropdownMenuItem(
                                        text = { Text(text = it) },
                                        onClick = {
                                            gender = it
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        dob = MyDatePickerDialog(context, User(), Modifier.fillMaxWidth())

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Checkbox(
                                checked = tnc,
                                onCheckedChange = { tnc = it },
                                colors = CheckboxColors(
                                    Color.White,
                                    Color.White,
                                    primaryColor,
                                    Color.Transparent,
                                    Color.LightGray,
                                    Color.LightGray,
                                    Color.LightGray,
                                    primaryColor,
                                    primaryColor,
                                    Color.Gray,
                                    Color.Gray,
                                    Color.Gray,
                                )
                            )

                            Text(
                                text = buildAnnotatedString {
                                    append("I agree with ")
                                    withStyle(
                                        style = SpanStyle(
                                            color = Color(0xFF71b7ed)
                                        )
                                    ) {
                                        append("Terms & Conditions")
                                    }
                                },
                                modifier = Modifier.clickable { /*TODO Navigate to tnc*/ }
                            )
                        }


                        if (invalidUser) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = dimensionResource(R.dimen.padding_large))
                            ) {
                                Text(
                                    text = newUserState.value.errorMessage,
                                    color = Color.Red
                                )
                            }
                        }

                        if (!tnc) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = dimensionResource(R.dimen.padding_large))
                            ) {
                                Text(
                                    text = "Please agree T&C to proceed",
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.width(325.dp)
                    ) {
                        Button(
                            onClick = {
                                if (tnc) {
                                    val newUser = User(
                                        username = username,
                                        email = email,
                                        password = password,
                                        phoneNo = phoneNo,
                                        gender = gender,
                                        dob = dob,
//                                        image = "", TODO default image
                                        role = getEnumList(UserRole::class.java)[0],
                                        status = getEnumList(UserStatus::class.java)[0]
                                    )
                                    isVerifying = true
                                    userViewModel.updateUserState(newUser)
                                }
                            },
                            enabled = !isVerifying,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.sign_up),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}