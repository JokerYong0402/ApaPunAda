package com.example.apapunada.ui.staff

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.apapunada.R
import com.example.apapunada.data.FeedbackSample.Feedbacks
import com.example.apapunada.data.OrderStatusSample
import com.example.apapunada.model.Feedback
import com.example.apapunada.model.Order
import com.example.apapunada.model.OrderStatus
import com.example.apapunada.ui.components.SearchBar
import com.example.apapunada.ui.components.SelectField
import com.example.apapunada.ui.components.formattedString

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StaffFeedbackScreen(
    feedbacks: List<Feedback> = Feedbacks
) {
    val headerList = listOf(
        // (Header name, Column width)
        Pair("  Id.", 80.dp),
        Pair("Party", 350.dp),
        Pair("Star", 100.dp),
        Pair("Category", 160.dp),
        Pair("Images", 270.dp),
        Pair("Comments", 350.dp)
    )

    var image = "content://media/picker/0/com.android.providers.media.photopicker/media/100000042,https://www.sidechef.com/recipe/bf2ae123-0553-4605-a564-e790d69d29fb.jpg?d=1408x1120,https://images.deliveryhero.io/image/foodpanda/recipes/chicken-chop-recipe-1.jpg"
    var images = image.split(",")

    var textInput by remember { mutableStateOf("") }

    val fieldList = listOf(
        "Party",
        "Star",
        "Category",
        "Comments"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
    ) {
        Row {
            SelectField(field = fieldList)
            Spacer(modifier = Modifier.size(10.dp))
            SearchBar(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
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
                        text = feedback.username,
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