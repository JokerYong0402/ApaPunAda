package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoucherRedeem(
    onBackButtonClicked: () -> Unit,
    onDetails: (Int, String) -> Unit,
    image: Painter,
    voucherRM: String,
    userId: Int,
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        var tabIndex by remember { mutableStateOf(0) }
        val tabs = listOf("Redeem Rewards", "Available Rewards")
        val primaryColor = colorResource(R.color.primary)
        var isPopUpVisible by remember { mutableStateOf(false) }
        var able by remember { mutableStateOf(true) }
        val userState = viewModel.userState.collectAsState(initial = UserState())
        viewModel.loadUserByUserId(userId)
        val user = userState.value.user
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
                                    text = "4. This Voucher is not exchangeable or replaceable for cash.",
                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.padding(25.dp, 10.dp, 25.dp, 100.dp),
                                    text = "5. APAPUNADA shall be entitled to withhold any benefits under the services rendered, with or without notice to the users, if the user is found to have breached APAPUNADA Users Terms and Conditions.",
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
                else {
                    if (user.point in 100..299) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.voucher_rm1_redeemed),
                                contentDescription = "Voucher RM1 Available",
                                modifier = Modifier
                                    .padding(30.dp, 10.dp)
                                    .width(400.dp)
                                    .clickable { onDetails(R.drawable.voucher_rm1, "RM1") }
                            )
                        }
                    }
                    else if (user.point in 300..999) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.voucher_rm1_redeemed),
                                contentDescription = "Voucher RM1 Available",
                                modifier = Modifier
                                    .padding(30.dp, 10.dp)
                                    .width(400.dp)
                                    .clickable { onDetails(R.drawable.voucher_rm1, "RM1") }
                            )
                            Image(
                                painter = painterResource(R.drawable.voucher_rm3_redeemed),
                                contentDescription = "Voucher RM3 Available",
                                modifier = Modifier
                                    .padding(30.dp, 10.dp)
                                    .width(400.dp)
                                    .clickable { onDetails(R.drawable.voucher_rm3, "RM3") }
                            )
                        }
                    }
                    else if (user.point >= 1000) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.voucher_rm1_redeemed),
                                contentDescription = "Voucher RM1 Available",
                                modifier = Modifier
                                    .padding(30.dp, 10.dp)
                                    .width(400.dp)
                                    .clickable { onDetails(R.drawable.voucher_rm1, "RM1") }
                            )
                            Image(
                                painter = painterResource(R.drawable.voucher_rm3_redeemed),
                                contentDescription = "Voucher RM3 Available",
                                modifier = Modifier
                                    .padding(30.dp, 10.dp)
                                    .width(400.dp)
                                    .clickable { onDetails(R.drawable.voucher_rm3, "RM3") }
                            )
                            Image(
                                painter = painterResource(R.drawable.voucher_rm10_redeemed),
                                contentDescription = "Voucher RM10 Redeemed",
                                modifier = Modifier
                                    .padding(30.dp, 10.dp)
                                    .width(400.dp)
                                    .clickable { onDetails(R.drawable.voucher_rm10, "RM10") }
                            )
                        }
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


@Preview(showBackground = true)
@Composable
fun VoucherRedeemPreview() {
    VoucherRedeem({},{drawableId: Int, voucherRM: String -> }, painterResource(R.drawable.voucher_rm1), "RM1", 6)
}