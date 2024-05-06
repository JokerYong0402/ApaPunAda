package com.example.apapunada.ui.users

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun EditProfileScreen() {
    var editedname by remember { mutableStateOf("") }
    var editedgender by remember { mutableStateOf("") }
    var editeddob by remember { mutableStateOf("") }
    var editedemail by remember { mutableStateOf("") }
    var editedpassword by remember { mutableStateOf("") }
    var editedphonenum by remember { mutableStateOf("") }

    var openAlertDialog by remember { mutableStateOf(false) }

    var isValidEmail by remember { mutableStateOf(true) }
    var isValidDOB by remember { mutableStateOf(true) }

    val maxCharOfName = 20
    val maxCharPhoneNum = 12
    val genderMale = "Male"
    val genderFemale = "Female"


    if (openAlertDialog) {
        EditProfileAlertDialog(
            onDismissRequest = { openAlertDialog = false },
            onConfirmation = { openAlertDialog = false },
            dialogTitle = "SUCCESS",
            dialogText = "Your profile has been updated.",
        )
    }
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
                ChangeProfilePic()

                Row(//EDIT NAME
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),
                        /*.border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),*/

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
                            value = editedname,
                            onValueChange = { if(it.length <= maxCharOfName) editedname = it },
                            modifier = Modifier
                        )
                    }
                }

                Row(//EDIT GENDER
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),
                        /*.border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),*/

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
                            value = editedgender,
                            onValueChange = { editedgender = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                Row(//EDIT DATE OF BIRTH
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),
//                        .border(
//                            2.dp,
//                            colorResource(R.color.primary),
//                            shape = RoundedCornerShape(50.dp)
//                        ),

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
                            value = editeddob,
                            onValueChange = { editeddob = it },
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
                        ,

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
                            value = editedemail,
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
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),

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
                            value = editedpassword,
                            onValueChange = { editedpassword = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                Row(//EDIT PHONE NUMBER
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),

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
                            value = editedphonenum,
                            onValueChange = { if(it.length <= maxCharPhoneNum) editedphonenum = it },
                            modifier = Modifier
                            //    .fillMaxWidth()
                        )
                    }
                }

                Button(
                    onClick = { openAlertDialog = true },
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
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)

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
            .background(color = Color.Transparent)
            .height(100.dp)
            .width(300.dp)
            .clip(
                shape = RoundedCornerShape(
                    size = 20.dp,
                ),
            )
            .border(
                BorderStroke(width = 2.dp, colorResource(id = R.color.primary)),
                shape = RoundedCornerShape(
                    size = 20.dp,
                )
            )
        ,
        placeholder = {
            Text(
            text = "",
            fontSize = 14.sp,
            color = colorResource(id = R.color.black),
            modifier = modifier
        ) },
        )
}

@Composable
fun EditProfileAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
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
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        }
    )
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
    return emailRegex.matches(email)
}

fun isValidDOB(dob: String): Boolean {
    // Basic date format validation (YYYY-MM-DD)
    val dobRegex = Regex("^\\d{4}-\\d{2}-\\d{2}\$")
    return dobRegex.matches(dob)
}


@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen()
}