package com.example.apapunada.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun DeleteProfileScreen() {
    var textInputDelete by remember { mutableStateOf("") }
    var triggerDltPopUp by remember { mutableStateOf(true) }
    var dltable by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.delete_profile)) },
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
                        .padding(dimensionResource(R.dimen.padding_big))
                        .height(180.dp),
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
                        .height(160.dp),
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
                        .padding(dimensionResource(R.dimen.padding_big))
                        .height(40.dp),
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
                    onClick = { triggerDltPopUp = true },
                    enabled = dltable,
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
        if (triggerDltPopUp) {
            dltable = false
            DeletePopUp(onConfirm = {
                triggerDltPopUp = false
                dltable = true
            })
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
        singleLine = true,
        onValueChange = onValueChange,

        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = Color.White)
            .height(150.dp)
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
                //fontstyle = FontStyle.Italic,
                color = colorResource(id = R.color.black),
                modifier = modifier
                .padding(bottom = 70.dp)
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


@Composable
fun DeletePopUp(onConfirm: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(color = Color.Black.copy(alpha = 0.5f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_big))
                .height(180.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_big))
            ) {
                Text(
                    text = "CONFIRMATION",
                    fontSize = 17.sp
                )

                Text(
                    text = " Are you sure want to delete this account?",
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier
                    //.padding(horizontal = 20.dp, vertical = 50.dp)
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Button(//cancel button
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.white)
                    ),
                    //shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .height(65.dp)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .border(2.dp, colorResource(R.color.primary),RoundedCornerShape(50.dp))
                        .clip(
                        shape = RoundedCornerShape(
                            size = 50.dp,
                        ),
                )
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        fontSize = 16.sp,
                        color = colorResource(R.color.primary)
                    )
                }
                Button(//confirm button
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.primary)
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .height(65.dp)
                        //.border(2.dp, colorResource(R.color.primary),RoundedCornerShape(50.dp))
                        .clip(
                            shape = RoundedCornerShape(
                                size = 50.dp,
                            ),
                            )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DeleteProfileScreenPreview() {
    DeleteProfileScreen()
}
