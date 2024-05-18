@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.apapunada.ui.users

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.PrepopulateData
import com.example.apapunada.data.dataclass.Feedback
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DisplayImagesFromByteArray
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.PopupWindowAlert
import com.example.apapunada.ui.components.PopupWindowDialog
import com.example.apapunada.ui.components.drawableResourceToByteArray
import com.example.apapunada.ui.components.uriToByteArray
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.FeedbackListState
import com.example.apapunada.viewmodel.FeedbackViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    onSubmit: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedbackViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    //TODO hardcode user
    var user = PrepopulateData.users[0]

    var feedbackListState =
        viewModel.feedbackListState.collectAsState(initial = FeedbackListState())
    var feedbacks: List<Feedback> = listOf()

    viewModel.loadAllFeedbacks()

    if (feedbackListState.value.isLoading) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable {}
            .zIndex(2f),
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularIndicator()
        }
    } else {
        if (feedbackListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading users: ${feedbackListState.value.errorMessage}")
            Log.i("User", "StaffUserScreen: ${feedbackListState.value.errorMessage}")
        } else {
            feedbacks = feedbackListState.value.feedbackList
        }
    }

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

    //color
    val primaryColor = colorResource(R.color.primary)
    val primary100Color = colorResource(id = R.color.primary_100)

    //data
    var selectedOption by remember { mutableStateOf("") }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }
    var textInput by remember { mutableStateOf("") }
    var star by remember { mutableStateOf(1) }

    //context
    val context = LocalContext.current

    //upload image
    var selectedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val multiplePhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri ?: Uri.EMPTY
        }
    )

    //popup checking
    var callPopupWindowDialog by remember { mutableStateOf(false) }
    var callPopupWindowAlert by remember { mutableStateOf(false) }

    if (callPopupWindowDialog) {
        //val byteArrays: MutableList<ByteArray?> = mutableListOf()
        val byteArray = uriToByteArray(context, selectedImageUri)
        val nonNullableByteArray = byteArray ?: ByteArray(0)
        PopupWindowDialog(
            onDismissRequest = { callPopupWindowDialog = false },
            onConfirmation = {
//                for (selectedImageUri in selectedImageUris) {
//                    byteArrays.add(uriToByteArray(context, selectedImageUri))
//                }
                viewModel.updateFeedbackState(
                    Feedback(
                        //TODO
                        userID = 1,
                        star = star,
                        category = selectedOption,
//                        images = selectedImageUris.toString(),TODO
                        images = nonNullableByteArray,
                        comments = textInput
                    )
                )
                viewModel.saveFeedback()
                //TODO navigation
                onSubmit()
            },
            dialogTitle = stringResource(id = R.string.feedback_9),
            confirmMessage = "Submit",
            containerColor = primaryColor
        )
    }
    if (callPopupWindowAlert) {
        PopupWindowAlert(
            onDismissRequest = { callPopupWindowAlert = false },
            onConfirmation = {
                callPopupWindowAlert = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.feedback_10),
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 15.dp
                        ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.feedback_11),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
            },
            buttonModifier = Modifier
                .padding(start = 5.dp)
                .size(width = 75.dp, height = 35.dp),
            buttonText = "Dismiss",
            buttonColor = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        )
    }
    //star images
    var imageResource1 = when (star) {
        1 -> R.drawable.feedback1
        2 -> R.drawable.feedback1
        3 -> R.drawable.feedback1
        4 -> R.drawable.feedback1
        else -> R.drawable.feedback1
    }
    var imageResource2 = when (star) {
        1 -> R.drawable.feedback2
        2 -> R.drawable.feedback1
        3 -> R.drawable.feedback1
        4 -> R.drawable.feedback1
        else -> R.drawable.feedback1
    }
    var imageResource3 = when (star) {
        1 -> R.drawable.feedback2
        2 -> R.drawable.feedback2
        3 -> R.drawable.feedback1
        4 -> R.drawable.feedback1
        else -> R.drawable.feedback1
    }
    var imageResource4 = when (star) {
        1 -> R.drawable.feedback2
        2 -> R.drawable.feedback2
        3 -> R.drawable.feedback2
        4 -> R.drawable.feedback1
        else -> R.drawable.feedback1
    }
    var imageResource5 = when (star) {
        1 -> R.drawable.feedback2
        2 -> R.drawable.feedback2
        3 -> R.drawable.feedback2
        4 -> R.drawable.feedback2
        else -> R.drawable.feedback1
    }
    //surface is to make it scrollable
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
                            )
                    ) {
                        Text(text = stringResource(R.string.feedback))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                                star = 1
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
                                star = 2
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
                                star = 3
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
                                star = 4
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
                                star = 5
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
                Row {
//                    DisplayImagesFromByteArray(
//                        byteArray = drawableResourceToByteArray(context, R.drawable.feedback7),
//                        modifier = Modifier
//                            .size(
//                                width = 82.dp,
//                                height = 67.dp
//                            )
//                            .padding(start = 10.dp, top = 2.dp)
//                            .border(BorderStroke(1.dp, colorResource(id = R.color.primary))),
//                        contentDescription = "Image",
//                        contentScale = ContentScale.FillBounds
//                    )
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
                                    multiplePhotosPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                            )
                            .padding(start = 30.dp)
                    )
                    Row {
                        if (selectedImageUri != Uri.EMPTY) {
                            DisplayImagesFromByteArray(
                                byteArray = uriToByteArray(context = context, selectedImageUri),
                                modifier = Modifier
                                    .size(
                                        width = 82.dp,
                                        height = 67.dp
                                    )
                                    .padding(start = 10.dp, top = 2.dp)
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            colorResource(id = R.color.primary)
                                        )
                                    ),
                                contentDescription = "Image",
                                contentScale = ContentScale.FillBounds
                            )
                        }

//                        items(selectedImageUris){selectedImageUri->
//
//                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
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
                    onClick = {
                        if (selectedOption == "") {
                            callPopupWindowAlert = true
                        } else {
                            callPopupWindowDialog = true
                        }
                    }
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
    val maxChar = 100

    Text(
        text = stringResource(id = R.string.feedback_5,value.length),
        textAlign = TextAlign.Start,
        modifier = modifier
            .padding(
                top = 30.dp,
                start = 30.dp
            )
    )
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            //to stop the user input in the textfield if the number of character is exceed the maxChar
            if (newValue.length <= maxChar) {
                onValueChange(newValue)
            }
        },
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