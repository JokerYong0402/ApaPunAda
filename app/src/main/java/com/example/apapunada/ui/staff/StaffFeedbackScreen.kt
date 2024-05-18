package com.example.apapunada.ui.staff

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.Feedback
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DropDownMenu
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.SearchBar
import com.example.apapunada.viewmodel.FeedbackListState
import com.example.apapunada.viewmodel.FeedbackViewModel
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffFeedbackScreen(
    viewModel: FeedbackViewModel = viewModel(factory = AppViewModelProvider.Factory),
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val feedbackListState = viewModel.feedbackListState.collectAsState(initial = FeedbackListState())
    var feedbacks: List<Feedback> = listOf()

    viewModel.loadAllFeedbacks()

    if (feedbackListState.value.isLoading) {
        IndeterminateCircularIndicator("Loading Feedback...")
    } else {
        if (feedbackListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading feedbacks: ${feedbackListState.value.errorMessage}")
            Log.i("Feedback", "StaffFeedbackScreen: ${feedbackListState.value.errorMessage}")
        } else {
            feedbacks = feedbackListState.value.feedbackList
        }
    }

    var selectField by remember { mutableStateOf("Field") }
    val context = LocalContext.current
    var textInput by remember { mutableStateOf("") }

    val headerList = listOf(
        // (Header name, Column width)
        Pair("  Id.", 80.dp),
        Pair("Party", 350.dp),
        Pair("Star", 100.dp),
        Pair("Category", 160.dp),
        Pair("Images", 270.dp),
        Pair("Comments", 350.dp)
    )

    //TODO delete
    var image = "content://media/picker/0/com.android.providers.media.photopicker/media/100000042,https://www.sidechef.com/recipe/bf2ae123-0553-4605-a564-e790d69d29fb.jpg?d=1408x1120,https://images.deliveryhero.io/image/foodpanda/recipes/chicken-chop-recipe-1.jpg"
    var images = image.split(",")

    val fieldList = listOf(
        "Party",
        "Star",
        "Category",
        "Comments"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = dimensionResource(R.dimen.padding_large))
    ) {
        Row {
            selectField = DropDownMenu(itemList = fieldList)
            Spacer(modifier = Modifier.size(10.dp))
            SearchBar(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier
            )
            if (selectField == "Category" && textInput != "") {
                viewModel.loadFeedbacksByCategory("%" + textInput + "%")
            } else if (selectField == "Star" && textInput != "") {
                viewModel.loadFeedbacksByStar(textInput.toInt())
            } else if (selectField == "Comments" && textInput != ""){
                viewModel.loadFeedbacksByComment("%" + textInput + "%")
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
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

            items(feedbacks.size) { i ->
                val feedback = feedbacks[i]

                userViewModel.loadUserByUserId(feedback.userID)
                var user: User

                do {
                    user = userViewModel.userState.value.user
                } while (user.userID != feedback.userID)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Text(
                        text = (i+1).toString(),
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[0].second)
                            .padding(start = 20.dp)
                    )
                    Text(
                        text = user.username,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[1].second)
                    )
                    Text(
                        text = feedback.star.toString(),
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[2].second)
                    )
                    Text(
                        text = feedback.category,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[3].second)
                    )

//                    LazyRow (
//                        modifier = Modifier
//                            .width(headerList[4].second)
//                    ) {
//                        items(feedback.images){selectedImageUri->
//                            DisplayImagesFromByteArray(
//                                byteArray = uriToByteArray(context = context, selectedImageUri),
//                                modifier = Modifier
//                                    .size(
//                                        width = 85.dp,
//                                        height = 70.dp
//                                    )
//                                    .padding(end = 10.dp)
//                                    .border(
//                                        BorderStroke(
//                                            1.dp,
//                                            colorResource(id = R.color.primary)
//                                        )
//                                    ),
//                                contentDescription = "Image",
//                                contentScale = ContentScale.FillBounds
//                            )
//                        }
//                    }
                    //TODO delete below and uncomment above
                    Row(
                        modifier = Modifier
                            .width(headerList[4].second)
                    ){
                        images.forEach { imageUrl ->
                            Column {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(
                                            width = 85.dp,
                                            height = 70.dp
                                        )
                                        .padding(end = 10.dp)
                                        .border(
                                            BorderStroke(
                                                1.dp,
                                                colorResource(id = R.color.primary)
                                            )
                                        ),
                                    model = imageUrl,
                                    contentDescription = "testing",
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }

                    }

                    Text(
                        text = feedback.comments,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[5].second)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

//TODO callUser
//@Composable
//fun callUser(
//    userID: Int,
//    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
//) :User {
//    userViewModel.loadUserByUserId(userID)
//    var user: User
//
//    do {
//        user = userViewModel.userState.value.user
//    } while (user.userID != userID)
//
//    return user
//}

@Composable
fun callUser(
    userID: Int,
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) : User {
//    val userState = userViewModel.userState.collectAsState(initial = UserState())
//    var user by remember { mutableStateOf(User()) }
//    userViewModel.loadUserByUserId(userID)
//    LaunchedEffect(Unit) {
//        launch {
//            delay(0)
//            user = userState.value.user
//        }
//    }
    val userState by userViewModel.userState.collectAsState(initial = UserState())

    // Trigger loading the user data
    LaunchedEffect(userID) {
        userViewModel.loadUserByUserId(userID)
    }

    // Observe user state
    var user by remember { mutableStateOf(User()) }
    LaunchedEffect(userState) {
            user = userState.user

    }
    Log.i("User","${userState.user}")

    return user
}
//    do {
//
//        user = userState.value.user
//
//    } while (user.userID != userID)

@Composable
@Preview(showBackground = true, device = Devices.TABLET)
fun StaffFeedbackTabletPreview() {
    StaffFeedbackScreen()
}

@Composable
@Preview(showBackground = true, device = Devices.PHONE)
fun StaffFeedbackPhonePreview() {
    StaffFeedbackScreen()
}