package com.example.apapunada.ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apapunada.R
import com.example.apapunada.StartScreen
import com.example.apapunada.ui.components.EnableScreenOrientation
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.LoginUserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBackButtonClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    EnableScreenOrientation()

    val loginUserState = authViewModel.userState.collectAsState(initial = LoginUserState())

    val context = LocalContext.current
    val primaryColor = colorResource(R.color.primary)
    var passwordVisible by remember { mutableStateOf(false) }
    var invalidUser by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isVerifying by remember { mutableStateOf(false) }

    if (isVerifying) {
        LaunchedEffect(Unit) {
            launch {
                delay(1000)
                if (loginUserState.value.isExist) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Welcome Back", Toast.LENGTH_SHORT).show()
                    }
                    onLoginButtonClicked()
                } else {
                    invalidUser = true
                    username = ""
                    password = ""
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            if (isVerifying) {
                IndeterminateCircularIndicator("Logging in...")
            }

            // Logo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                ,
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
                        text = "Hello Again!",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "Log into your account",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Input Field
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                            Icon(imageVector = Icons.Filled.Face, contentDescription = "username")
                        },
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = "Password") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Lock, contentDescription = "password")
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
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = colorResource(R.color.primary),
                        )
                    )

                    if (invalidUser) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimensionResource(R.dimen.padding_large))
                        ) {
                            Text(
                                text = loginUserState.value.errorMessage,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(R.dimen.padding_large))
                    ) {
                        Text(
                            text = "Forget password?",
                            modifier = Modifier.clickable{
                                navController.navigate(StartScreen.SignUp.name)
                            }
                        )
                    }
                }
            }

            // Login Button
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
                            authViewModel.loginUser(
                                username = username,
                                password = password
                            )
                            isVerifying = true
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
                            text = stringResource(R.string.login),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
//    LoginScreen()
}