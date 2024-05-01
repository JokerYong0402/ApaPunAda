package com.example.apapunada.ui.users

import android.os.CountDownTimer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun WaitlistPager(initialPage: Int = 0) {
    WaitlistScreen()
}

@Composable
fun WaitlistScreen(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = {}
){
    var size by remember { mutableStateOf(1) }

    val openAlertDialog = remember { mutableStateOf(false) }

    var check by remember { mutableStateOf(false) }

    var check1 by remember { mutableStateOf(false) }

    //for countdown timer
    var numsInMinute :Long by remember{ mutableStateOf(10) }
    var numsInSecond :Long by remember{ mutableStateOf(10) }
    var setView :String by remember{ mutableStateOf("5 mins 0 secs") }
    val cuntNum = object :CountDownTimer(300000, 1000){
       override fun onTick(millisUntilFinished: Long){
           numsInSecond = millisUntilFinished/1000
           numsInMinute = numsInSecond/60
           numsInSecond = numsInSecond%60
           setView = "$numsInMinute mins $numsInSecond secs"
       }

        override fun onFinish(){
            check1 = true
            setView = "Finish"
        }
    }

    if (check) {
        PopupWindowDialog(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
                println("Confirmation registered")
            },
            dialogTitle = stringResource(id = R.string.waitlist_8),
        )
    }

    if (check1) {
        PopupWindowAlert(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
                println("Confirmation registered")
            },
            dialogTitle = stringResource(id = R.string.waitlist_11),
            dialogText = stringResource(id = R.string.waitlist_12)
        )
    }

    val primaryColor = colorResource(R.color.primary)

    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.waitlist), onBackButtonClicked) }
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
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.waitlist_13),
                        fontSize = 65.sp,
                        color = colorResource(R.color.primary)
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 25.dp)
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
                        shape = RoundedCornerShape(12.dp),
                        modifier = modifier
                            .width(56.dp)
                            .height(45.dp),
                        onClick = {
//                            if (size < 10) {
//                                size++
//                            }
                            cuntNum.start()
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
                ) {
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
                                Text(
                                    text = "$setView", // Replace with your formatting function
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontSize = 85.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    }
                }
                Row(
                    modifier = Modifier
                        .padding(
                            start = 30.dp,
                            top = 100.dp
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
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = modifier
                            .widthIn(min = 300.dp)
                            .align(Alignment.CenterHorizontally),
                        //update "check" variable to call the function
                        onClick = { check = true }
                    ) {
                        Text(text = stringResource(id = R.string.waitlist_10))
                    }
                }
            }
        }
    }
}

//@Composable
//fun WaitlistScreen(
//    modifier: Modifier = Modifier
//){
//    var size by remember { mutableStateOf(1) }
//
//    val openAlertDialog = remember { mutableStateOf(false) }
//
//    var check by remember { mutableStateOf(false) }
//
//    if (check) {
//        PopupWindowDialog(
//            onDismissRequest = { openAlertDialog.value = false },
//            onConfirmation = {
//                openAlertDialog.value = false
//                println("Confirmation registered")
//            },
//            dialogTitle = stringResource(id = R.string.waitlist_8),
//        )
//    }
//
//    var check1 by remember { mutableStateOf(false) }
//
//    if (check1) {
//        PopupWindowAlert(
//            onDismissRequest = { openAlertDialog.value = false },
//            onConfirmation = {
//                openAlertDialog.value = false
//                println("Confirmation registered")
//            },
//            dialogTitle = stringResource(id = R.string.waitlist_11),
//            dialogText = stringResource(id = R.string.waitlist_12)
//        )
//    }
//
//    val primaryColor = colorResource(R.color.primary)
//
//    Scaffold(
//        topBar = { MyTopTitleBar(title = stringResource(R.string.waitlist)) }
//    ) { innerPadding ->
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//        ) {
//            Column(
//                modifier = modifier
//                    .padding(innerPadding)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.waitlist_9),
//                        fontSize = 65.sp,
//                        color = colorResource(R.color.primary)
//                    )
//                }
//                Divider(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 30.dp, bottom = 25.dp)
//                )
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 15.dp)
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.waitlist_2),
//                        fontSize = 20.sp,
//                        modifier = modifier
//                            .padding(end = 10.dp)
//                            .align(Alignment.CenterVertically)
//                    )
//                    Button(
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = colorResource(id = R.color.primary)
//                        ),
//                        shape = RoundedCornerShape(12.dp),
//                        modifier = modifier
//                            .width(56.dp)
//                            .height(45.dp),
//                        onClick = {
//                            if (size > 1){
//                                size--
//                            }
//                        }
//                    ) {
//                        Text(
//                            text = "-",
//                            fontSize = 20.sp
//                        )
//                    }
//                    Box(
//                        modifier = modifier
//                            .height(45.dp)
//                            .width(56.dp)
//                            .padding(
//                                horizontal = 5.dp
//                            )
//                            .clip(
//                                shape = RoundedCornerShape(
//                                    size = 8.dp,
//                                ),
//                            )
//                            .border(
//                                BorderStroke(width = 1.dp, colorResource(id = R.color.primary)),
//                                shape = RoundedCornerShape(
//                                    size = 12.dp,
//                                )
//                            )
//
//                    ) {
//                        Text(
//                            fontSize = 20.sp,
//                            text = "$size",
//                            modifier = modifier
//                                .align(Alignment.Center)
//                        )
//                    }
//                    Button(
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = colorResource(id = R.color.primary)
//                        ),
//                        shape = RoundedCornerShape(12.dp),
//                        modifier = modifier
//                            .width(56.dp)
//                            .height(45.dp),
//                        onClick = {
//                            if (size < 10) {
//                                size++
//                            }
//                        }
//                    ) {
//                        Text(
//                            text = "+",
//                            fontSize = 20.sp
//                        )
//                    }
//                }
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 15.dp, top = 10.dp, bottom = 15.dp)
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.waitlist_3),
//                        fontSize = 20.sp,
//                        color = Color.White,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                            .clip(
//                                shape = RoundedCornerShape(
//                                    size = 8.dp,
//                                ),
//                            )
//                            .background(
//                                Color.Red
//                            )
//                            .padding(
//                                vertical = 2.dp,
//                                horizontal = 16.dp
//                            )
//                            .height(27.dp)
//                    )
//                }
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 20.dp, horizontal = 35.dp),
//                    shape = RoundedCornerShape(8.dp),
//                    elevation = CardDefaults.cardElevation(
//                        defaultElevation = 4.dp
//                    ),
//                    colors = CardDefaults.cardColors(
//                        containerColor = primaryColor,
//                        contentColor = Color.White
//                    )
//                ) {
//                    Box(
//                        modifier = Modifier.padding(10.dp)
//                    ) {
//                        Column {
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(
//                                        vertical = 15.dp,
//                                        horizontal = 15.dp
//                                    ),
//                                shape = RoundedCornerShape(8.dp),
//                                elevation = CardDefaults.cardElevation(
//                                    defaultElevation = 4.dp
//                                ),
//                                colors = CardDefaults.cardColors(
//                                    containerColor = Color.White,
//                                    contentColor = Color.Black
//                                )
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .padding(horizontal = 16.dp, vertical = 10.dp)
//                                ) { // Add inner padding
//                                    Row(
//                                        modifier = Modifier
//                                            .padding(
//                                                start = 10.dp
//                                            )
//                                    ) {
//                                        Image(
//                                            painter = painterResource(id = R.drawable.waitlist1),
//                                            contentDescription = "waitlist1",
//                                            modifier = Modifier
//                                                .size(
//                                                    width = 23.dp,
//                                                    height = 23.dp
//                                                )
//
//                                        )
//                                        Text(
//                                            text = stringResource(id = R.string.waitlist_4),
//                                            fontSize = 17.sp,
//                                            modifier = modifier
//                                                .padding(start = 16.dp)
//                                        )
//                                    }
//                                }
//                            }
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                modifier = Modifier
//                                    .padding(start = 90.dp, top = 15.dp, bottom = 35.dp)
//                            ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.waitlist2),
//                                    contentDescription = "waitlist2",
//                                    modifier = Modifier
//                                        .size(
//                                            width = 50.dp,
//                                            height = 50.dp
//                                        )
//
//                                )
//                                Text(
//                                    text = "6",
//                                    fontSize = 60.sp,
//                                    modifier = modifier
//                                        .padding(start = 10.dp)
//                                )
//                            }
//                        }
//
//                    }
//                }
//                Row(
//                    modifier = Modifier
//                        .padding(
//                            start = 30.dp,
//                            top = 100.dp
//                        )
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.waitlist3),
//                        contentDescription = "waitlist3",
//                        modifier = Modifier
//                            .size(
//                                width = 20.dp,
//                                height = 20.dp
//                            )
//                    )
//                    Text(
//                        text = stringResource(id = R.string.waitlist_5),
//                        fontSize = 17.sp,
//                        color = Color.Red,
//                        modifier = modifier
//                            .padding(
//                                start = 5.dp
//                            )
//                    )
//                }
//                Column {
//                    Text(
//                        text = stringResource(id = R.string.waitlist_6),
//                        fontSize = 17.sp,
//                        modifier = modifier
//                            .padding(
//                                horizontal = 30.dp
//                            )
//                    )
//                }
//                Divider(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 20.dp, bottom = 15.dp)
//                )
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    Button(
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Red
//                        ),
//                        shape = RoundedCornerShape(12.dp),
//                        modifier = modifier
//                            .widthIn(min = 300.dp)
//                            .align(Alignment.CenterHorizontally),
//                        //update "check" variable to call the function
//                        onClick = { check = true }
//                    ) {
//                        Text(text = stringResource(id = R.string.waitlist_10))
//                    }
//                }
//            }
//        }
//    }
//}

//@Composable
//fun WaitlistScreen(
//    modifier: Modifier = Modifier
//){
//    var size by remember { mutableStateOf(1) }
//
//    val openAlertDialog = remember { mutableStateOf(false) }
//
//    var check by remember { mutableStateOf(false) }
//
//    if (check) {
//        PopupWindowDialog(
//            onDismissRequest = { openAlertDialog.value = false },
//            onConfirmation = {
//                openAlertDialog.value = false
//                println("Confirmation registered")
//            },
//            dialogTitle = stringResource(id = R.string.waitlist_8),
//        )
//    }
//
//    val primaryColor = colorResource(R.color.primary)
//
//    Scaffold(
//        topBar = { MyTopTitleBar(title = stringResource(R.string.waitlist)) }
//    ) { innerPadding ->
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//        ) {
//                Column(
//                    modifier = modifier
//                        .padding(innerPadding)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.waitlist_1),
//                            fontSize = 65.sp,
//                            color = colorResource(R.color.primary)
//                        )
//                    }
//                    Divider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 30.dp, bottom = 25.dp)
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 15.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.waitlist_2),
//                            fontSize = 20.sp,
//                            modifier = modifier
//                                .padding(end = 10.dp)
//                                .align(Alignment.CenterVertically)
//                        )
//                        Button(
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = colorResource(id = R.color.primary)
//                            ),
//                            shape = RoundedCornerShape(12.dp),
//                            modifier = modifier
//                                .width(56.dp)
//                                .height(45.dp),
//                            onClick = {
//                                 if (size > 1){
//                                size--
//                            }
//                            }
//                        ) {
//                            Text(
//                                text = "-",
//                                fontSize = 20.sp
//                            )
//                        }
//                        Box(
//                            modifier = modifier
//                                .height(45.dp)
//                                .width(56.dp)
//                                .padding(
//                                    horizontal = 5.dp
//                                )
//                                .clip(
//                                    shape = RoundedCornerShape(
//                                        size = 8.dp,
//                                    ),
//                                )
//                                .border(
//                                    BorderStroke(width = 1.dp, colorResource(id = R.color.primary)),
//                                    shape = RoundedCornerShape(
//                                        size = 12.dp,
//                                    )
//                                )
//
//                        ) {
//                            Text(
//                                fontSize = 20.sp,
//                                text = "$size",
//                                modifier = modifier
//                                    .align(Alignment.Center)
//                            )
//                        }
//                        Button(
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = colorResource(id = R.color.primary)
//                            ),
//                            shape = RoundedCornerShape(12.dp),
//                            modifier = modifier
//                                .width(56.dp)
//                                .height(45.dp),
//                            onClick = {
//                            if (size < 10) {
//                                size++
//                            }
//                            }
//                        ) {
//                            Text(
//                                text = "+",
//                                fontSize = 20.sp
//                            )
//                        }
//                    }
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 15.dp, top = 10.dp, bottom = 15.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.waitlist_3),
//                            fontSize = 20.sp,
//                            color = Color.White,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .clip(
//                                    shape = RoundedCornerShape(
//                                        size = 8.dp,
//                                    ),
//                                )
//                                .background(
//                                    Color.Red
//                                )
//                                .padding(
//                                    vertical = 2.dp,
//                                    horizontal = 16.dp
//                                )
//                                .height(27.dp)
//                        )
//                    }
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 20.dp, horizontal = 35.dp),
//                        shape = RoundedCornerShape(8.dp),
//                        elevation = CardDefaults.cardElevation(
//                            defaultElevation = 4.dp
//                        ),
//                        colors = CardDefaults.cardColors(
//                            containerColor = primaryColor,
//                            contentColor = Color.White
//                        )
//                    ) {
//                        Box(
//                            modifier = Modifier.padding(10.dp)
//                        ) {
//                            Column {
//                                Card(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(
//                                            vertical = 15.dp,
//                                            horizontal = 15.dp
//                                        ),
//                                    shape = RoundedCornerShape(8.dp),
//                                    elevation = CardDefaults.cardElevation(
//                                        defaultElevation = 4.dp
//                                    ),
//                                    colors = CardDefaults.cardColors(
//                                        containerColor = Color.White,
//                                        contentColor = Color.Black
//                                    )
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .padding(horizontal = 16.dp, vertical = 10.dp)
//                                    ) { // Add inner padding
//                                        Row(
//                                            modifier = Modifier
//                                                .padding(
//                                                    start = 10.dp
//                                                )
//                                        ) {
//                                            Image(
//                                                painter = painterResource(id = R.drawable.waitlist1),
//                                                contentDescription = "waitlist1",
//                                                modifier = Modifier
//                                                    .size(
//                                                        width = 23.dp,
//                                                        height = 23.dp
//                                                    )
//
//                                            )
//                                            Text(
//                                                text = stringResource(id = R.string.waitlist_4),
//                                                fontSize = 17.sp,
//                                                modifier = modifier
//                                                    .padding(start = 16.dp)
//                                            )
//                                        }
//                                    }
//                                }
//                                Row(
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    modifier = Modifier
//                                        .padding(start = 90.dp, top = 15.dp, bottom = 35.dp)
//                                ) {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.waitlist2),
//                                        contentDescription = "waitlist2",
//                                        modifier = Modifier
//                                            .size(
//                                                width = 50.dp,
//                                                height = 50.dp
//                                            )
//
//                                    )
//                                    Text(
//                                        text = "6",
//                                        fontSize = 60.sp,
//                                        modifier = modifier
//                                            .padding(start = 10.dp)
//                                    )
//                                }
//                            }
//
//                        }
//                    }
//                    Row(
//                        modifier = Modifier
//                            .padding(
//                                start = 30.dp,
//                                top = 100.dp
//                            )
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.waitlist3),
//                            contentDescription = "waitlist3",
//                            modifier = Modifier
//                                .size(
//                                    width = 20.dp,
//                                    height = 20.dp
//                                )
//                        )
//                        Text(
//                            text = stringResource(id = R.string.waitlist_5),
//                            fontSize = 17.sp,
//                            color = Color.Red,
//                            modifier = modifier
//                                .padding(
//                                    start = 5.dp
//                                )
//                        )
//                    }
//                    Column {
//                        Text(
//                            text = stringResource(id = R.string.waitlist_6),
//                            fontSize = 17.sp,
//                            modifier = modifier
//                                .padding(
//                                    horizontal = 30.dp
//                                )
//                        )
//                    }
//                    Divider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 20.dp, bottom = 15.dp)
//                    )
//
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        Button(
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = primaryColor
//                            ),
//                            shape = RoundedCornerShape(12.dp),
//                            modifier = modifier
//                                .widthIn(min = 300.dp)
//                                .align(Alignment.CenterHorizontally),
//                            //update "check" variable to call the function
//                            onClick = { check = true }
//                        ) {
//                            Text(text = stringResource(id = R.string.waitlist_7))
//                        }
//                    }
//                }
//            }
//        }
//    }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupWindowDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
){
    val primaryColor = colorResource(R.color.primary)

    AlertDialog(
        containerColor = Color.White,
        shape = RoundedCornerShape(5.dp),
        title = {
            Text(
                modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 10.dp,
                    bottom = 30.dp
                ),
                fontWeight = FontWeight.Bold,
                text = dialogTitle
            )
        },
        onDismissRequest = {
            onDismissRequest()
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
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Quit")
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier
                    .padding(
                        start = 70.dp
                    )
                    .size(width = 75.dp, height = 35.dp),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(width = 1.dp, primaryColor),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = primaryColor
                ),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun PopupWindowAlert(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
){
    AlertDialog(
        containerColor = Color.White,
        shape = RoundedCornerShape(5.dp),
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
                        text = dialogTitle
                    )
                }
            }

        },
        text = {
            Text(
                text = dialogText
            )
        },
        onDismissRequest = {
            onDismissRequest()
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
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Quit")
            }
        }
//        dismissButton = {
//            TextButton(
//                modifier = Modifier
//                    .padding(
//                        start = 70.dp
//                    )
//                    .size(width = 75.dp, height = 35.dp),
//                shape = RoundedCornerShape(5.dp),
//                border = BorderStroke(width = 1.dp, primaryColor),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.White,
//                    contentColor = primaryColor
//                ),
//                onClick = {
//                    onDismissRequest()
//                }
//            ) {
//                Text("Cancel")
//            }
//        }
    )
}

@Preview(showBackground = true)
@Composable
fun WaitlistScreenPreview() {
    WaitlistScreen(
        modifier = Modifier
    )
}