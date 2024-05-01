package com.example.apapunada.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyTopTitleBar
import androidx.compose.material3.ElevatedButton as ElevatedButton

@Composable
fun EditProfileScreen() {
    var textInput by remember { mutableStateOf("") }
    //var triggerPopUp by remember { mutableStateOf(true) }
    var able by remember { mutableStateOf(true) }
    var openAlertDialog by remember { mutableStateOf(false) }


    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.edit_profile)) },
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
                Column(//Profile Picture
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(//contain image
                        painter = painterResource(R.drawable.profile_image),
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            //.fillMaxSize()
                            .size(
                                width = 150.dp,
                                height = 150.dp
                            )
                            .clip(CircleShape)
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TextButton(//Change profile picture
                        onClick = {},
                        modifier = Modifier

                    ){
                        Text(
                            "Change Profile Picture",

                        )

                    }

                }
                Row(//EDIT NAME
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.nameicon),
                        contentDescription = "Name Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
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
                            value = textInput,
                            onValueChange = { textInput = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                Row(//EDIT GENDER
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.gendericon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
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
                            value = textInput,
                            onValueChange = { textInput = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                Row(//EDIT DATE OF BIRTH
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.dobicon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
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
                            value = textInput,
                            onValueChange = { textInput = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                Row(//EDIT EMAIL ADDRESS
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.emailicon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
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
                            value = textInput,
                            onValueChange = { textInput = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                Row(//EDIT PASSWORD
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.passwordicon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
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
                            value = textInput,
                            onValueChange = { textInput = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                Row(//EDIT PHONE NUMBER
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.phonenumbericon),
                        contentDescription = "Gender Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
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
                            value = textInput,
                            onValueChange = { textInput = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                ElevatedButton(
                    onClick = { openAlertDialog = true },
                    enabled = able,
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
fun EditTextFieldProfile(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        modifier = modifier
            //.padding(horizontal = 30.dp)
            .background(color = Color.Transparent)
            .height(100.dp)
            .width(300.dp)
            .clip(
                shape = RoundedCornerShape(
                    size = 20.dp,
                ),
            )
            .border(
                BorderStroke(width = 1.dp, colorResource(id = R.color.white)),
                shape = RoundedCornerShape(
                    size = 20.dp,
                )
            )
        ,
        placeholder = {
            Text(
            text = "F",
            fontSize = 14.sp,
            color = colorResource(id = R.color.black),
            modifier = modifier
                //.padding(bottom = 100.dp)
        )
                      },

        //Design for the text that user type in
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = colorResource(id = R.color.black)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
}

@Composable//TODO not yet done popup
fun EditProfilePopUp(onDismissRequest: () -> Unit,
          onConfirmation: () -> Unit,
          dialogTitle: String,
          dialogText: String )
{
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
                    onConfirmation()
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
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun ChangeProfilePic(){

}



@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen()
}