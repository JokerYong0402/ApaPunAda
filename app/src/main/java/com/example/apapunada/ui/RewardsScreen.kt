package com.example.apapunada.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun RewardsScreen(userPoint: Int) {
    var isPopUpVisible by remember { mutableStateOf(false) }
    var able by remember { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize()) {
        val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        var tabIndex by remember { mutableStateOf(0) }
        val tabs = listOf("Redeem Rewards", "My Rewards")
        val primaryColor = colorResource(R.color.primary)
        Scaffold(
            topBar = { CenterAlignedTopAppBar(
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
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                },
                actions = {
                    IconButton(
                        enabled = able,
                        onClick = {
                            isPopUpVisible = true
                        }
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
            ) }
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
                if (tabIndex == 0)
                {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(0.dp, 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(30.dp, 15.dp, 0.dp, 0.dp)
                            ) {
                                Text(
                                    text = "APA Points",
                                    color = primaryColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Row(
                                modifier = Modifier.padding(30.dp, 10.dp, 0.dp, 0.dp)
                            ) {
                                Text(
                                    text = "$userPoint pts",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier.padding(30.dp, 10.dp, 0.dp, 0.dp)
                            ) {
                                Text(
                                    text = "History",
                                    color = Color.Gray
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 10.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.apa_points),
                                contentDescription = "APA Points",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(80.dp, 0.dp, 0.dp, 0.dp)
                                    .size(100.dp)
                            )
                        }
                    }

                    Text(
                        text = "APA Rewards",
                        color = primaryColor,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(30.dp, 10.dp, 0.dp, 10.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.voucher_rm1),
                        contentDescription = "Voucher RM1",
                        modifier = Modifier
                            .padding(30.dp, 7.dp)
                            .width(400.dp)
                            .clickable(enabled = able) { /*Another Page*/ }
                    )

                    Image(
                        painter = painterResource(R.drawable.voucher_rm3),
                        contentDescription = "Voucher RM3",
                        modifier = Modifier
                            .padding(30.dp, 7.dp)
                            .width(400.dp)
                            .clickable(enabled = able) { /*Another Page*/ }
                    )

                    Image(
                        painter = painterResource(R.drawable.voucher_rm10),
                        contentDescription = "Voucher RM10",
                        modifier = Modifier
                            .padding(30.dp, 7.dp)
                            .width(400.dp)
                            .clickable(enabled = able) { /*Another Page*/ }
                    )
                }
                else
                {
                    /* TODO */
                    // If the user got the voucher then present the voucher
                    // For example
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
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
    }
}

@Composable
fun RewardPopUp(onDismiss: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black.copy(alpha = 0.5f))
        .padding(10.dp, 130.dp, 10.dp, 60.dp)
        .clip(RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier
            .background(color = Color.White)
            .padding(10.dp, 20.dp, 10.dp, 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Help",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "Frequently Ask Questions",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .clickable(
                            onClick = onDismiss
                        )
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column() {
                    Text(
                        modifier = Modifier.padding(20.dp, 10.dp),
                        text = "How do I earn APA Points?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(40.dp, 0.dp),
                        text = "You earn APA Points with every transaction of a minimum spend of RM1 via APAPUNADA app.",
                        fontSize = 12.sp
                    )
                    Text(
                        modifier = Modifier.padding(20.dp, 30.dp, 20.dp, 10.dp),
                        text = "How do I earn APA Rewards?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(40.dp, 0.dp),
                        text = "You can redeem APA Rewards with your earned APA Points.",
                        fontSize = 12.sp
                    )
                    Text(
                        modifier = Modifier.padding(20.dp, 30.dp, 20.dp, 10.dp),
                        text = "Where can I see the number of APA Points that I will earn with each transaction?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(40.dp, 0.dp),
                        text = "The number of APA Points you will earn will show on the payment page.",
                        fontSize = 12.sp
                    )
                    Text(
                        modifier = Modifier.padding(20.dp, 30.dp, 20.dp, 10.dp),
                        text = "How do I track my earned APA Points?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(40.dp, 0.dp),
                        text = "You can track your APA Points history on the Rewards page.",
                        fontSize = 12.sp
                    )
                    Text(
                        modifier = Modifier.padding(20.dp, 30.dp, 20.dp, 10.dp),
                        text = "Is there an expiration date for my APA Points and APA Rewards?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(40.dp, 0.dp),
                        text = "No! Our APA Points and APA Rewards have no expiration date which means you are able to use it whenever you want.",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

//Navigate to another screen
@Composable
fun VoucherRM1(onDismiss: () -> Unit) {
    
}

@Preview(showBackground = true)
@Composable
fun RewardsScreenPreview() {
    RewardsScreen(234)
}