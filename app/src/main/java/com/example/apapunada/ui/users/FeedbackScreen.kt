@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.apapunada.ui.users

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedbackPager(initialPage: Int = 0) {
    FeedbackScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    modifier: Modifier = Modifier
) {

    var textInput by remember { mutableStateOf("") }

    val options1 = listOf(
        "Packaging",
        "Menu",
        "Delivery",
    )

    val options2 = listOf(
        "App",
        "Environment",
        "Other"
    )

    var selectedOption by remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    val primaryColor = colorResource(R.color.primary)
    val primary100Color = colorResource(id = R.color.primary_100)

    var result by remember { mutableStateOf(1) }

    var imageResource1 = when (result) {
        1 -> R.drawable.feedback1
        2 -> R.drawable.feedback1
        3 -> R.drawable.feedback1
        4 -> R.drawable.feedback1
        else -> R.drawable.feedback1
    }
    var imageResource2 = when (result) {
        1 -> R.drawable.feedback2
        2 -> R.drawable.feedback1
        3 -> R.drawable.feedback1
        4 -> R.drawable.feedback1
        else -> R.drawable.feedback1
    }
    var imageResource3 = when (result) {
        1 -> R.drawable.feedback2
        2 -> R.drawable.feedback2
        3 -> R.drawable.feedback1
        4 -> R.drawable.feedback1
        else -> R.drawable.feedback1
    }
    var imageResource4 = when (result) {
        1 -> R.drawable.feedback2
        2 -> R.drawable.feedback2
        3 -> R.drawable.feedback2
        4 -> R.drawable.feedback1
        else -> R.drawable.feedback1
    }
    var imageResource5 = when (result) {
        1 -> R.drawable.feedback2
        2 -> R.drawable.feedback2
        3 -> R.drawable.feedback2
        4 -> R.drawable.feedback2
        else -> R.drawable.feedback1
    }
    //surface is to make it scrollable
    Surface(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {

        Column(
            modifier = modifier
                .fillMaxSize(),

            ) {
            TopAppBar(
                modifier = modifier
                    .shadow(2.dp),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            //.contentAlignment(Alignment.Center) // Centers horizontally
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            ) // Example padding for vertical centering
                    ) {
                        Text(text = stringResource(R.string.feedback))
                    }

                },

                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.feedback_1),
                    fontSize = 25.sp,
                    color = colorResource(R.color.primary),
                )

                Text(
                    text = stringResource(R.string.feedback_2),
                    fontSize = 16.sp,
                    color = colorResource(R.color.black)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = imageResource1),
                    contentDescription = "1",
                    modifier = Modifier
                        .size(
                            width = 60.dp,
                            height = 60.dp
                        )
                        .clickable(
                            onClick = {
                                result = 1
                            }
                        )
                )
                Image(
                    painter = painterResource(id = imageResource2),
                    contentDescription = "1",
                    modifier = Modifier
                        .size(
                            width = 60.dp,
                            height = 60.dp
                        )
                        .clickable(
                            onClick = {
                                result = 2
                            }
                        )
                )
                Image(
                    painter = painterResource(id = imageResource3),
                    contentDescription = "1",
                    modifier = Modifier
                        .size(
                            width = 60.dp,
                            height = 60.dp
                        )
                        .clickable(
                            onClick = {
                                result = 3
                            }
                        )
                )
                Image(
                    painter = painterResource(id = imageResource4),
                    contentDescription = "1",
                    modifier = Modifier
                        .size(
                            width = 60.dp,
                            height = 60.dp
                        )
                        .clickable(
                            onClick = {
                                result = 4
                            }
                        )
                )
                Image(
                    painter = painterResource(id = imageResource5),
                    contentDescription = "1",
                    modifier = Modifier
                        .size(
                            width = 60.dp,
                            height = 60.dp
                        )
                        .clickable(
                            onClick = {
                                result = 5
                            }
                        )
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 40.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.feedback_3),
                    fontSize = 15.sp,
                    color = colorResource(R.color.black),
                    modifier = modifier
                        .padding(bottom = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    options1.forEach { text ->
                        Row(
                            modifier = Modifier
                                .padding(
                                    all = 4.dp,
                                )
                                .shadow(
                                    elevation = 10.dp
                                ),
                        ) {
                            Text(
                                text = text,
                                textAlign = TextAlign.Center,
                                color = if (text == selectedOption) {
                                    Color.White
                                } else {
                                    primaryColor
                                },
                                modifier = Modifier
                                    .clip(
                                        shape = RoundedCornerShape(
                                            size = 12.dp,
                                        ),
                                    )
                                    .clickable {
                                        onSelectionChange(text)
                                    }
                                    .background(
                                        if (text == selectedOption) {
                                            primaryColor
                                        } else {
                                            primary100Color
                                        }
                                    )
                                    .padding(
                                        vertical = 12.dp,
                                        horizontal = 16.dp,
                                    )
                                    .widthIn(
                                        min = 90.dp
                                    )
                            )
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    options2.forEach { text ->
                        Row(
                            modifier = Modifier
                                .padding(
                                    all = 4.dp,
                                )
                                .shadow(
                                    elevation = 10.dp
                                ),
                        ) {
                            Text(
                                text = text,
                                textAlign = TextAlign.Center,
                                color = if (text == selectedOption) {
                                    Color.White
                                } else {
                                    primaryColor
                                },
                                modifier = Modifier
                                    .clip(
                                        shape = RoundedCornerShape(
                                            size = 12.dp,
                                        ),
                                    )
                                    .clickable {
                                        onSelectionChange(text)
                                    }
                                    .background(
                                        if (text == selectedOption) {
                                            primaryColor
                                        } else {
                                            primary100Color
                                        }
                                    )
                                    .padding(
                                        vertical = 12.dp,
                                        horizontal = 16.dp,
                                    )
                                    .widthIn(
                                        min = 90.dp
                                    )
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.feedback_4),
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .padding(top = 50.dp, start = 30.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.feedback3),
                    contentDescription = "1",
                    modifier = Modifier
                        .size(
                            width = 100.dp,
                            height = 70.dp
                        )
                        .clickable(
                            onClick = {}
                        )
                        .padding(start = 30.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Text(
                    text = stringResource(id = R.string.feedback_5),
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .padding(top = 30.dp, start = 30.dp)
                )
                EditTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 8.dp)
                )
                Button(

                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = modifier
                        .widthIn(min = 300.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {}
                ) {
                    Text(text = "Submit")
                }

            }
        }
    }
}

@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .padding(horizontal = 30.dp)
            .background(color = Color.Transparent)
            .height(150.dp)
            .clip(
                shape = RoundedCornerShape(
                    size = 12.dp,
                ),
            )
            .border(
                BorderStroke(width = 1.dp, colorResource(id = R.color.primary)),
                shape = RoundedCornerShape(
                    size = 12.dp,
                )
            )
            ,
        placeholder = { Text(
            text = stringResource(R.string.feedback_6),
            fontSize = 12.sp,
            color = colorResource(id = R.color.primary),
            modifier = modifier
                .padding(bottom = 100.dp)
        )},
        //Design for the text that user type in
        textStyle = TextStyle(
            fontSize = 12.sp,
            color = colorResource(id = R.color.primary)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun uploadImage() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val laucher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){uri: Uri? ->
        imageUri = uri
    }

   Row (
        modifier = Modifier
            .fillMaxSize()
    ) {
       Image(
           painter = painterResource(id = R.drawable.feedback3),
           contentDescription = "1",
           modifier = Modifier
               .size(
                   width = 100.dp,
                   height = 70.dp
               )
               .clickable(
                   onClick = {
                       laucher.launch("image/*")
                   }
               )
               .padding(start = 30.dp)
       )

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28){
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            width = 85.dp,
                            height = 70.dp
                        )
                        .padding(start = 10.dp)
                        .border(BorderStroke(1.dp, colorResource(id = R.color.primary)))
                )
            }
        }
    }
}
@Composable
fun successScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.feedback5),
                contentDescription = "feedback5",
                modifier = Modifier
                    .size(18.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.feedback4),
                contentDescription = "feedback4",
                modifier = Modifier
                    .size(100.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.feedback_7),
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 30.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.feedback_8),
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 30.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FeedbackScreenPreview() {
    FeedbackScreen(
        modifier = Modifier
    )
}