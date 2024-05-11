package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoucherRedeem(
    onBackButtonClicked: () -> Unit,
    image: Painter,
    voucherRM: String,
    userPoints: Int
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        var tabIndex by remember { mutableStateOf(0) }
        val tabs = listOf("Redeem Rewards", "My Rewards")
        val primaryColor = colorResource(R.color.primary)
        var isPopUpVisible by remember { mutableStateOf(false) }
        var able by remember { mutableStateOf(true) }
        var redeemPressed by remember { mutableStateOf(false) }
        val voucherPoints = when (voucherRM) {
            "RM1" -> 100
            "RM3" -> 300
            else -> 1000
        }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFe7e1f5),
                        titleContentColor = Color.Black,
                    ),
                    title = {
                        Text(
                            text = "Rewards",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(250.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            enabled = able,
                            onClick = onBackButtonClicked
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "Back",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            enabled = able,
                            onClick = { isPopUpVisible = true }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = "Help",
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    },
                    scrollBehavior = scrollBehaviour,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                // content of page
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TabRow(
                        selectedTabIndex = tabIndex,
                        contentColor = primaryColor
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                enabled = able,
                                text = { Text(title, fontWeight = FontWeight.SemiBold) },
                                selected = tabIndex == index,
                                onClick = { tabIndex = index }
                            )
                        }
                    }
                }
                if (tabIndex == 0) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 25.dp),
                        painter = image,
                        contentDescription = "Voucher"
                    )
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(25.dp, 10.dp),
                                text = "Terms and Conditions",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .heightIn(min = 300.dp)
                            ) {
                                Text(
                                    modifier = Modifier.padding(25.dp, 10.dp),
                                    text = "1. APAPUNADA reserves the right to amend or cancel this voucher at any time.",
                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.padding(25.dp, 10.dp),
                                    text = "2. Payment must be made via APAPUNADA  Mobile App with a minimum spend of $voucherRM.",
                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.padding(25.dp, 10.dp),
                                    text = "3. Total Order Value must be more than or equivalent to the Voucher Value.",
                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.padding(25.dp, 10.dp),
                                    text = "4. This Voucher is valid for delivery only.",
                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.padding(25.dp, 10.dp),
                                    text = "5. This Voucher is not exchangeable or replaceable for cash.",
                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.padding(25.dp, 10.dp, 25.dp, 100.dp),
                                    text = "6. APAPUNADA shall be entitled to withhold any benefits under the services rendered, with or without notice to the users, if the user is found to have breached APAPUNADA Users Terms and Conditions.",
                                    fontSize = 15.sp
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .background(color = Color.White)
                        ) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 20.dp)
                            )
                            Button(
                                enabled = able,
                                onClick =  { redeemPressed = true } ,
                                colors = ButtonDefaults.buttonColors(primaryColor),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .widthIn(min = 300.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(bottom = 16.dp)
                            ) {
                                Text(text = "Redeem Now")
                            }
                        }
                    }
                }
                else {
                    /* TODO */
                    //If user need to go back to redeem rewards page, go back to the rewardsScreen()
                    // If the user got the voucher then present the voucher
                    // For example
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            //Display all the voucher owned by the user
                            painter = painterResource(R.drawable.voucher_rm10_redeemed),
                            contentDescription = "Voucher RM10 Redeemed",
                            modifier = Modifier
                                .padding(30.dp, 10.dp)
                                .width(400.dp)
                                .clickable { /*Another Page*/ }
                        )
                    }
                }
            }
        }
        if (isPopUpVisible) {
            able = false
            RewardPopUp(onDismiss = {
                isPopUpVisible = false
                able = true
            })
        }
        if (redeemPressed) {
            able = false
            Redeem(onDone = {
                redeemPressed = false
                able = true
            }, voucherPoints, userPoints)
        }
    }
}

@Composable
fun Redeem(
    onDone: () -> Unit,
    redeemPoints: Int,
    userPoints: Int
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(10.dp, 20.dp, 10.dp, 20.dp)
        ) {
            if (userPoints >= redeemPoints) {
                Row(
                    modifier = Modifier.padding(start = 20.dp)
                ){
                    Text(
                        text = "Redeem successfully!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        //TODO when onclick add voucher into user account (my rewards) and go back to reward page
                        //TODO minus userPoints with redeemPoints
                        modifier = Modifier.clickable ( onClick = onDone )
                    )
                }
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Kindly check your rewards in \"My Rewards\".",
                    fontSize = 14.sp
                )
                Button(
                    //TODO when onclick add voucher into user account (my rewards)
                    onClick =  ( onDone ) ,
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.primary)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .widthIn(min = 300.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 15.dp)
                ) {
                    Text(text = "OK")
                }
            }
            else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    //.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = " Warning",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = "You need more APA Points",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        modifier = Modifier.clickable ( onClick = onDone )
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                    text = "You don't have enough APA Points for this reward yet. Keep earning!",
                    fontSize = 14.sp
                )
                Button(
                    onClick =  ( onDone ) ,
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.primary)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .widthIn(min = 300.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 15.dp)
                ) {
                    Text(text = "OK")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoucherRedeemPreview() {
    VoucherRedeem({},painterResource(R.drawable.voucher_rm1), "RM1", 123)
}