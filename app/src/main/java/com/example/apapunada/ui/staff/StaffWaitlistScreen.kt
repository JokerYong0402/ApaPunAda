package com.example.apapunada.ui.staff

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.data.dataclass.Waitlist
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DropDownMenu
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.SearchBar
import com.example.apapunada.ui.components.formattedDate
import com.example.apapunada.viewmodel.UserListState
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserViewModel
import com.example.apapunada.viewmodel.WaitlistListState
import com.example.apapunada.viewmodel.WaitlistState
import com.example.apapunada.viewmodel.WaitlistViewModel
import com.example.apapunada.viewmodel.WaitlistWithUsername
import com.example.apapunada.viewmodel.WaitlistWithUsernameState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffWaitlistScreen(
    waitlistViewModel: WaitlistViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var isSelected by remember { mutableStateOf("current") }
    var isSearch by remember { mutableStateOf(false) }

    var waitlistWithUsernameState = waitlistViewModel.waitlistWithUsernameState.collectAsState(initial = WaitlistWithUsernameState())
    var waitlistsWithUsername: List<WaitlistWithUsername> = listOf()

    if (waitlistWithUsernameState.value.isLoading) {
        Box( modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable {}
            .zIndex(2f)
            ,
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularIndicator()
        }
    } else {
        if (waitlistWithUsernameState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading users: ${waitlistWithUsernameState.value.errorMessage}")
            Log.i("User", "StaffUserScreen: ${waitlistWithUsernameState.value.errorMessage}")
        } else {
            waitlistsWithUsername = waitlistWithUsernameState.value.waitlistWithUsername
        }
    }

    if (isSelected == "current" && !isSearch) {
        waitlistViewModel.loadWaitlistsByCurrentStatus()
    } else if (isSelected == "history" && !isSearch) {
        waitlistViewModel.loadWaitlistsByHistoryStatus()
    }

    //screen width
    val config  = LocalConfiguration.current
    val width by remember(config) {
        mutableStateOf(config.screenWidthDp)
    }

    var callAccept by remember { mutableStateOf(false) }
    var callCancel by remember { mutableStateOf(false) }

    val primaryColor = colorResource(R.color.primary)
    var textInput by remember { mutableStateOf("") }
    var currentWaitlist by remember { mutableStateOf(WaitlistWithUsername()) }
    var selectField by remember { mutableStateOf("Field") }

    if (callAccept) {
        AcceptStatus(
            onDismissRequest = {callAccept = false},
            onConfirmation = {
                waitlistViewModel.updateWaitlistState(it)
                waitlistViewModel.updateWaitlist()
                callAccept = false
            },
            dialogTitle = stringResource(id = R.string.waitlist_14),
            waitlist = currentWaitlist
        )
    }
    if (callCancel) {
        CancelStatus(
            onDismissRequest = {callCancel = false},
            onConfirmation = {
                waitlistViewModel.updateWaitlistState(it)
                waitlistViewModel.updateWaitlist()
                callCancel = false
            },
            dialogTitle = stringResource(id = R.string.waitlist_15),
            waitlist = currentWaitlist
        )
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
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isSelected == "current") primaryColor else Color.White,
                    contentColor = if (isSelected == "current") Color.White else primaryColor,
                ),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    bottomStart = 16.dp
                ),
                onClick = { isSelected = "current" }
            ) {
                Text(text = "Current")
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(top = 12.dp),
                border = BorderStroke(width = 1.dp, color = primaryColor),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected == "current") Color.White else primaryColor,
                    contentColor = if (isSelected == "current") primaryColor else Color.White
                ),
                shape = RoundedCornerShape(
                    topEnd = 16.dp,
                    bottomEnd = 16.dp
                ),
                onClick = { isSelected = "history" }
            ) {
                Text(text = "History")
            }
            Spacer(modifier = Modifier.size(10.dp))
            if (width > 600 && isSelected == "current") {
                selectField = DropDownMenu(currentList)
                Spacer(modifier = Modifier.size(10.dp))
                SearchBar(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                )
                if (selectField == "Party" && textInput != "") {
                    waitlistViewModel.loadWaitlistByParty("Queue", "Queue", "%" + textInput + "%")
                } else if (selectField == "Size" && textInput != ""){
                    waitlistViewModel.loadWaitlistBySize("Queue", "Queue", textInput.toInt())
                }
            } else if (width > 600 && isSelected == "history") {
                selectField = DropDownMenu(historyList)
                Spacer(modifier = Modifier.size(10.dp))
                SearchBar(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                )
                if (selectField == "Party" && textInput != "") {
                    waitlistViewModel.loadWaitlistByParty("Accepted", "Cancelled", "%" + textInput + "%")
                } else if (selectField == "Size" && textInput != ""){
                    waitlistViewModel.loadWaitlistBySize("Accepted", "Cancelled",  textInput.toInt())
                } else if (selectField == "Status" && textInput != "") {
                    waitlistViewModel.loadWaitlistByStatus("%" + textInput + "%")
                }
            }
        }
        if (width <= 600 && isSelected == "current") {
            Row {
                selectField = DropDownMenu(currentList)
                Spacer(modifier = Modifier.size(10.dp))
                SearchBar(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                )
                if (selectField == "Party" && textInput != "") {
                    waitlistViewModel.loadWaitlistByParty("Queue", "Queue", "%" + textInput + "%")
                } else if (selectField == "Size" && textInput != ""){
                    waitlistViewModel.loadWaitlistBySize("Queue", "Queue",  textInput.toInt())
                }
            }
        } else if (width <= 600 && isSelected == "history") {
            Row {
                selectField = DropDownMenu(historyList)
                Spacer(modifier = Modifier.size(10.dp))
                SearchBar(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                )
                if (selectField == "Party" && textInput != "") {
                    waitlistViewModel.loadWaitlistByParty("Accepted", "Cancelled", "%" + textInput + "%")
                } else if (selectField == "Size" && textInput != ""){
                    waitlistViewModel.loadWaitlistBySize("Accepted", "Cancelled",  textInput.toInt())
                } else if (selectField == "Status" && textInput != "") {
                    waitlistViewModel.loadWaitlistByStatus("%" + textInput + "%")
                }
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
            items(waitlistsWithUsername.size) { i ->
                val waitlist = waitlistsWithUsername[i]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Text(
                            text = (i+1).toString(),
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
                            text = formattedDate(waitlist.datetime, "date"),
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(currentHeaderList[3].second)
                        )
                        if (isSelected == "current") {
                            Text(
                                text = (((System.currentTimeMillis() - (waitlist.datetime))/60000).toString() + " mins"),
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(currentHeaderList[4].second)
                            )
                        }
                        if (waitlist.status == "Queue") {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.width(currentHeaderList[5].second)
                            ) {
                                IconButton(
                                    onClick = {
                                        currentWaitlist = waitlist
                                        callAccept = true
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.waitlist5),
                                        contentDescription = "Edit button",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        currentWaitlist = waitlist
                                        callCancel = true
                                    }
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

@Composable
fun AcceptStatus(
    onDismissRequest: () -> Unit,
    onConfirmation: (Waitlist) -> Unit,
    dialogTitle: String,
    waitlist: WaitlistWithUsername
) {
    val primaryColor =  colorResource(id = R.color.primary)
    val context = LocalContext.current

    AlertDialog(
        containerColor = Color.White,
        shape = RoundedCornerShape(5.dp),
        title = {
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 30.dp
                            ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        text = dialogTitle
                    )
                }
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        dismissButton = {
            TextButton(
                modifier = Modifier
                    .padding(
                        start = 5.dp
                    )
                    .size(width = 75.dp, height = 35.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = primaryColor
                ),
                border = BorderStroke(1.dp,primaryColor),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(
                modifier = Modifier
                    .padding(
                        start = 5.dp
                    )
                    .size(width = 75.dp, height = 35.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                ),
                onClick = {
                    onConfirmation(
                        Waitlist(
                            waitlistID = waitlist.waitlistID,
                            userID = waitlist.userID,
                            size = waitlist.size,
                            datetime = waitlist.datetime,
                            status = "Accepted"
                        )
                    )
                    Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT)
                        .show()
                }
            ) {
                Text("Accept")
            }
        }
    )
}

@Composable
fun CancelStatus(
    onDismissRequest: () -> Unit,
    onConfirmation: (Waitlist) -> Unit,
    dialogTitle: String,
    waitlist: WaitlistWithUsername
) {
    val primaryColor =  colorResource(id = R.color.primary)
    val context = LocalContext.current

    AlertDialog(
        containerColor = Color.White,
        shape = RoundedCornerShape(5.dp),
        title = {
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 30.dp
                            ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        text = dialogTitle
                    )
                }
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        dismissButton = {
            TextButton(
                modifier = Modifier
                    .padding(
                        start = 5.dp
                    )
                    .size(width = 75.dp, height = 35.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = primaryColor
                ),
                border = BorderStroke(1.dp,primaryColor),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(
                modifier = Modifier
                    .padding(
                        start = 5.dp
                    )
                    .size(width = 75.dp, height = 35.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                ),
                onClick = {
                    onConfirmation(
                        Waitlist(
                            waitlistID = waitlist.waitlistID,
                            userID = waitlist.userID,
                            size = waitlist.size,
                            datetime = waitlist.datetime,
                            status = "Cancelled"
                        )
                    )
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT)
                        .show()
                }
            ) {
                Text("Cancel")
            }
        }
    )
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