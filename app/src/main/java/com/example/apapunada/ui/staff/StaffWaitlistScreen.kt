package com.example.apapunada.ui.staff

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.data.WaitlistSample.Waitlists
import com.example.apapunada.model.WaitList
import com.example.apapunada.ui.components.DropDownMenu
import com.example.apapunada.ui.components.SearchBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffWaitlistScreen(
    waitlists: List<WaitList> = Waitlists
) {
    val config  = LocalConfiguration.current
    val width by remember(config) {
        mutableStateOf(config.screenWidthDp)
    }

    var callAccept by remember { mutableStateOf(false) }
    var callCancel by remember { mutableStateOf(false) }

    var isSelected by remember { mutableStateOf("current") }

    val primaryColor = colorResource(R.color.primary)

    var textInput by remember { mutableStateOf("") }

    if (callAccept) {
        AcceptWaitlist()
    }

    if (callCancel) {
        CancelWaitlist()
    }

    val currentHeaderList = listOf(
        // (Header name, Column width)
        Pair("  No. ", 80.dp),
        Pair("Party", 400.dp),
        Pair("Size", 200.dp),
        Pair("Quoted", 180.dp),
        Pair("Wait", 170.dp),
        Pair("Actions", 200.dp)
    )

    val historyHeaderList = listOf(
        // (Header name, Column width)
        Pair("  No. ", 80.dp),
        Pair("Party", 400.dp),
        Pair("Size", 200.dp),
        Pair("Quoted", 180.dp),
        Pair("Wait", 170.dp),
        Pair("Status", 200.dp)
    )

    val currentList = listOf(
        "Party",
        "Size"
    )

    val historyList = listOf(
        "Party",
        "Size",
        "Status"
    )

    var count = 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
    ) {
        Row (
            modifier = Modifier
                .padding(bottom = 5.dp)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(top = 12.dp),
                border = BorderStroke(width = 1.dp, color = primaryColor),
                colors = ButtonDefaults.outlinedButtonColors( // Access default colors
                    containerColor = if (isSelected == "current") primaryColor else Color.White, // Set custom background color
                    contentColor = if (isSelected == "current") Color.White else primaryColor,
                ),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    bottomStart = 16.dp
                ),
                onClick = { count = 0; isSelected = "current" }
            ) {
                Text(text = "Current")
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(top = 12.dp),
                border = BorderStroke(width = 1.dp, color = primaryColor),
                colors = ButtonDefaults.buttonColors( // Access default colors
                    containerColor = if (isSelected == "current") Color.White else primaryColor, // Set custom background color
                    contentColor = if (isSelected == "current") primaryColor else Color.White // Set custom text color
                ),
                shape = RoundedCornerShape(
                    topEnd = 16.dp,
                    bottomEnd = 16.dp
                ),
                onClick = { count = 0; isSelected = "history" }
            ) {
                Text(text = "History")
            }
            Spacer(modifier = Modifier.size(10.dp))
            if (width > 600 && isSelected == "current") {
                DropDownMenu(currentList)
                Spacer(modifier = Modifier.size(10.dp))
                SearchBar(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                )

            } else if (width > 600 && isSelected == "history") {
                DropDownMenu(historyList)
                Spacer(modifier = Modifier.size(10.dp))
                SearchBar(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                )
            }
        }
        if (width <= 600 && isSelected == "current") {
            Row {
                DropDownMenu(currentList)
                Spacer(modifier = Modifier.size(10.dp))
                SearchBar(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                )
            }
        } else if (width <= 600 && isSelected == "history") {
            Row {
                DropDownMenu(historyList)
                Spacer(modifier = Modifier.size(10.dp))
                SearchBar(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                )
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
                    if (isSelected == "current") {
                        currentHeaderList.forEach { header ->
                            Text(
                                text = header.first,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .width(header.second)
                            )
                        }
                    } else {
                        historyHeaderList.forEach { header ->
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
            }

            items(waitlists.size) { i ->
                val waitlist = waitlists[i]
                val status =
                    if (isSelected == "current") {
                        "Queue"
                    } else {
                        "Accepted"
                    }
                if (waitlist.status == status) {
                    count += 1
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Text(
                            text = count.toString(),
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(currentHeaderList[0].second)
                                .padding(start = 20.dp)
                        )

                        Text(
                            text = waitlist.username,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(currentHeaderList[1].second)
                        )

                        Text(
                            text = waitlist.size.toString(),
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(currentHeaderList[2].second)
                        )

                        Text(
                            text = waitlist.dateTime,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(currentHeaderList[3].second)
                        )

                        Text(
                            text = waitlist.waitTime.toString() + " mins",
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(currentHeaderList[4].second)
                        )
                        if (waitlist.status == "Queue") {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.width(currentHeaderList[5].second)
                            ) {
                                IconButton(
                                    onClick = {callAccept = true}
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.waitlist5),
                                        contentDescription = "Edit button",
                                        modifier = Modifier.size(30.dp)
                                    )

                                }

                                IconButton(
                                    onClick = {callCancel = true}
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.waitlist6),
                                        contentDescription = "Detail button",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = waitlist.status,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(currentHeaderList[4].second)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AcceptWaitlist() {

}

@Composable
fun CancelWaitlist() {

}

@Composable
@Preview(showBackground = true, device = Devices.TABLET)
fun StaffWaitlistTabletPreview() {
    StaffWaitlistScreen()
}

@Composable
@Preview(showBackground = true, device = Devices.PHONE)
fun StaffWaitlistPhonePreview() {
    StaffWaitlistScreen()
}