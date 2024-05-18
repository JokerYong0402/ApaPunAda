package com.example.apapunada.ui.users

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.PrepopulateData
import com.example.apapunada.data.dataclass.Waitlist
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.PopupWindowAlert
import com.example.apapunada.ui.components.PopupWindowDialog
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.WaitlistIDState
import com.example.apapunada.viewmodel.WaitlistListState
import com.example.apapunada.viewmodel.WaitlistState
import com.example.apapunada.viewmodel.WaitlistViewModel
import com.example.apapunada.viewmodel.WaitlistWithUsername
import com.example.apapunada.viewmodel.WaitlistWithUsernameState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WaitlistScreen(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = {},
    viewModel: WaitlistViewModel = viewModel(factory = AppViewModelProvider.Factory),
    authViewModel: AuthViewModel
){
    var waitlistListState = viewModel.waitlistWithUsernameState.collectAsState(initial = WaitlistWithUsernameState())
    var waitlistIDState = viewModel.waitlistID.collectAsState(initial = WaitlistIDState())
    var waitlists: List<WaitlistWithUsername> = listOf()

    if (waitlistListState.value.isLoading) {
        Box( modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable {}
            .zIndex(2f),
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularIndicator()
        }
    } else {
        if (waitlistListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading users: ${waitlistListState.value.errorMessage}")
            Log.i("User", "StaffUserScreen: ${waitlistListState.value.errorMessage}")
        } else {
            waitlists = waitlistListState.value.waitlistWithUsername
        }
    }

    var size by remember { mutableStateOf(1) }
    var queue = waitlists.size
    val primaryColor = colorResource(R.color.primary)

    var checkJoin by remember { mutableStateOf(false) }
    var checkQuit by remember { mutableStateOf(false) }
    var checkFinish by remember { mutableStateOf(false) }

    var numsInMinute :Long by remember{ mutableStateOf(10) }
    var numsInSecond :Long by remember{ mutableStateOf(10) }
    var cuntNumStart by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val countDownDuration = 10000L
    var displayTime by remember { mutableStateOf(countDownDuration) }
    var waitlistID by remember { mutableStateOf(0) }

    if (!checkJoin) {
        viewModel.loadWaitlistsByCurrentStatus()
    } else if (checkJoin && !checkQuit) {
        viewModel.loadQueueWaitlistID(authViewModel.userState.value.user.userID)
        waitlistID = waitlistIDState.value.waitlistID
        viewModel.loadInfrontWaitlists(waitlistID)
    }
    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.waitlist),onBackButtonClicked) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
                Column(
                    modifier = modifier
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 35.dp)
                            .fillMaxWidth()
                    ) {
                        if (waitlists.size == 0 && checkJoin){
                            Text(
                                text = stringResource(id = R.string.waitlist_13),
                                fontSize = 65.sp,
                                lineHeight = 65.sp,
                                color = colorResource(R.color.primary)
                            )
                        }
                        else if (checkJoin) {
                            Text(
                                text = stringResource(id = R.string.waitlist_9),
                                fontSize = 65.sp,
                                lineHeight = 65.sp,
                                color = colorResource(R.color.primary)
                            )
                        } else if (!checkJoin) {
                            Text(
                                text = stringResource(id = R.string.waitlist_1),
                                fontSize = 65.sp,
                                lineHeight = 65.sp,
                                color = colorResource(R.color.primary)
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 20.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.waitlist_2),
                            fontSize = 20.sp,
                            modifier = modifier
                                .padding(end = 10.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary)
                            ),
                            enabled = !checkJoin,
                            shape = RoundedCornerShape(12.dp),
                            modifier = modifier
                                .width(56.dp)
                                .height(45.dp),
                            onClick = {
                                 if (size > 1){
                                size--
                            }
                            }
                        ) {
                            Text(
                                text = "-",
                                fontSize = 20.sp
                            )
                        }
                        Box(
                            modifier = modifier
                                .height(45.dp)
                                .width(56.dp)
                                .padding(
                                    horizontal = 5.dp
                                )
                                .clip(
                                    shape = RoundedCornerShape(
                                        size = 8.dp,
                                    ),
                                )
                                .border(
                                    BorderStroke(width = 1.dp, colorResource(id = R.color.primary)),
                                    shape = RoundedCornerShape(
                                        size = 12.dp,
                                    )
                                )

                        ) {
                            Text(
                                fontSize = 20.sp,
                                text = "$size",
                                modifier = modifier
                                    .align(Alignment.Center)
                            )
                        }
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary)
                            ),
                            enabled = !checkJoin,
                            shape = RoundedCornerShape(12.dp),
                            modifier = modifier
                                .width(56.dp)
                                .height(45.dp),
                            onClick = {
                                if (size < 10) {
                                    size++
                                }
                            }
                        ) {
                            Text(
                                text = "+",
                                fontSize = 20.sp
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, top = 10.dp, bottom = 15.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.waitlist_3),
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(
                                        size = 8.dp,
                                    ),
                                )
                                .background(
                                    Color.Red
                                )
                                .padding(
                                    vertical = 2.dp,
                                    horizontal = 16.dp
                                )
                                .height(27.dp)
                        )
                    }
                    if (!checkJoin || (checkJoin && waitlists.size != 0)) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 35.dp, end = 35.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = primaryColor,
                                contentColor = Color.White
                            )
                        ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Column {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            vertical = 15.dp,
                                            horizontal = 15.dp
                                        ),
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 4.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp, vertical = 10.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    start = 10.dp
                                                )
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.waitlist1),
                                                contentDescription = "waitlist1",
                                                modifier = Modifier
                                                    .size(
                                                        width = 23.dp,
                                                        height = 23.dp
                                                    )

                                            )
                                            Text(
                                                text = stringResource(id = R.string.waitlist_4),
                                                fontSize = 17.sp,
                                                modifier = modifier
                                                    .padding(start = 16.dp)
                                            )
                                        }
                                    }
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(start = 90.dp, top = 15.dp, bottom = 35.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.waitlist2),
                                        contentDescription = "waitlist2",
                                        modifier = Modifier
                                            .size(
                                                width = 50.dp,
                                                height = 50.dp
                                            )

                                    )
                                    Text(
                                        text = queue.toString(),
                                        fontSize = 60.sp,
                                        modifier = modifier
                                            .padding(start = 10.dp)
                                    )
                                }
                            }
                        }
                    }
                    } else if (waitlists.size == 0 && checkJoin) {
                        if (cuntNumStart == false) {
                            BackgroundCountdown(
                                duration = countDownDuration,
                                onTimeUpdate = {displayTime = it},
                                onFinish = {
                                    checkFinish = true
                                }
                            )
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp, horizontal = 35.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            colors = CardDefaults.cardColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    )
                        {
                        Box(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Column {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 15.dp,
                                        horizontal = 15.dp
                                    ),
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 4.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(
                                                start = 10.dp
                                            )
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.waitlist4),
                                            contentDescription = "waitlist4",
                                            modifier = Modifier
                                                .size(
                                                    width = 23.dp,
                                                    height = 23.dp
                                                )
                                        )
                                        Text(
                                            text = stringResource(id = R.string.waitlist_4),
                                            fontSize = 17.sp,
                                            modifier = modifier
                                                .padding(start = 16.dp)
                                        )
                                    }
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                numsInSecond = displayTime/1000
                                numsInMinute = numsInSecond/60
                                numsInSecond %= 60
                                Text(
                                    text = "${numsInMinute} mins ${numsInSecond} secs",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontSize = 85.sp,
                                    lineHeight = 85.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                    }
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 30.dp,
                                top = 50.dp
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.waitlist3),
                            contentDescription = "waitlist3",
                            modifier = Modifier
                                .size(
                                    width = 20.dp,
                                    height = 20.dp
                                )
                        )
                        Text(
                            text = stringResource(id = R.string.waitlist_5),
                            fontSize = 17.sp,
                            color = Color.Red,
                            modifier = modifier
                                .padding(
                                    start = 5.dp
                                )
                        )
                    }
                    Column {
                        Text(
                            text = stringResource(id = R.string.waitlist_6),
                            fontSize = 17.sp,
                            modifier = modifier
                                .padding(
                                    horizontal = 30.dp
                                )
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 15.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        if (!checkJoin) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = primaryColor
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = modifier
                                    .widthIn(min = 300.dp)
                                ,
                                //update "check" variable to call the function
                                onClick = {
                                    checkJoin = true
                                    //add new waitlist
                                    viewModel.updateWaitlistState(
                                        Waitlist(
                                            userID = authViewModel.userState.value.user.userID,
                                            size = size,
                                            datetime = System.currentTimeMillis(),
                                            status = "Queue"
                                        )
                                    )
                                    viewModel.saveWaitlist()
                                }
                            ) {
                                Text(text = stringResource(id = R.string.waitlist_7))
                            }
                        } else if (checkJoin || waitlists.size == 0) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = modifier
                                    .widthIn(min = 300.dp),
                                //update "check" variable to call the function
                                onClick = {
                                    checkQuit = true
                                }
                            ) {
                                if (checkQuit) {
                                    PopupWindowDialog(
                                        onDismissRequest = {
                                            checkQuit = false
                                        },
                                        onConfirmation = {
                                            checkJoin = false
                                            checkQuit = false
                                            viewModel.updateWaitlistState(
                                                Waitlist(
                                                    //TODO
                                                    waitlistID = waitlistID,
                                                    userID = 2,
                                                    size = size,
                                                    datetime = System.currentTimeMillis(),
                                                    status = "Cancelled"
                                                )
                                            )
                                            viewModel.updateWaitlist()
                                        },
                                        dialogTitle = stringResource(id = R.string.waitlist_8),
                                        confirmMessage = "Quit",
                                        containerColor = Color.Red
                                    )
                                }
                                Text(text = stringResource(id = R.string.waitlist_10))
                            }
                        }
                    }
                }
            }
        }
    if (checkFinish) {
        PopupWindowAlert(
            onDismissRequest = {
                checkFinish = false
                checkQuit = false
                checkJoin = false
                size = 1
                viewModel.updateWaitlistState(
                    Waitlist(
                        waitlistID = waitlistID,
                        userID = authViewModel.userState.value.user.userID,
                        size = size,
                        datetime = System.currentTimeMillis(),
                        status = "Cancelled"
                    )
                )
                viewModel.updateWaitlist()
            },
            onConfirmation = {
                checkFinish = false
                checkQuit = false
                checkJoin = false
                size = 1
                viewModel.updateWaitlistState(
                    Waitlist(
                        waitlistID = waitlistID,
                        userID = authViewModel.userState.value.user.userID,
                        size = size,
                        datetime = System.currentTimeMillis(),
                        status = "Cancelled"
                    )
                )
                viewModel.updateWaitlist()
            },
            title = {
                Column {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.waitlist3),
                            contentDescription = "waitlist_3",
                            modifier = Modifier
                                .size(
                                    width = 25.dp,
                                    height = 25.dp
                                )
                        )
                        Text(
                            modifier = Modifier
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    bottom = 30.dp
                                ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            text = stringResource(id = R.string.waitlist_11)
                        )
                    }
                }

            },
            text = {
                Text(
                    text = stringResource(id = R.string.waitlist_12)
                )
            },
            buttonModifier = Modifier
                .padding(
                    start = 5.dp
                )
                .size(width = 75.dp, height = 35.dp),
            buttonColor = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            buttonText = "Quit"
        )
    }
}

@Composable
fun BackgroundCountdown(
    duration: Long,
    onTimeUpdate: (Long) -> Unit,
    onFinish: () -> Unit
){
    var remainingTime = duration

    LaunchedEffect(Unit) {
        launch {
            while (remainingTime > 0) {
                delay(1000)
                remainingTime -= 1000
                onTimeUpdate(remainingTime)
            }
            onFinish()
            }
        }
}