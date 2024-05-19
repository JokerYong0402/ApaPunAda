package com.example.apapunada.ui.staff

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.viewmodel.OrderViewModel
import com.example.apapunada.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun StaffDashboardScreen() {
    val config  = LocalConfiguration.current
    val width by remember(config) { mutableStateOf(config.screenWidthDp) }

    if (width > 600) {
        StaffDashboardLandscape()
    } else {
        StaffDashboardPortrait()
    }
}

@Composable
fun StaffDashboardPortrait(
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    userViewModel.loadAllUsers()
    var users by remember { mutableStateOf(listOf(User())) }

    orderViewModel.loadAllOrders()
    var orders by remember { mutableStateOf(listOf(Order())) }

    var ordersAmount = orderViewModel.calculateOrderTotal(orders)

    var isLoading by remember { mutableStateOf(true) }
    if (isLoading) {
        IndeterminateCircularIndicator("Loading...")
    }

    LaunchedEffect(Unit) {
        launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                isLoading = false
                users = userViewModel.userListState.value.userList
                users = users.filter { it.status == "Active" }

                orders = orderViewModel.orderListState.value.orderList
                orders = orders.filter { it.orderStatus != "Pending" }

                ordersAmount = orderViewModel.calculateOrderTotal(orders)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Overview",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        dashboardCard(
            title = "User",
            value = users.size.toString(),
            icon = R.drawable.dashboard_1
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        dashboardCard(
            title = "Order",
            value = orders.size.toString(),
            icon = R.drawable.dashboard_2
        )

        Spacer(modifier = Modifier.height(20.dp))

        dashboardCard(
            title = "Sales",
            value = formattedString(0.0),
            icon = R.drawable.dashboard_3
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_large))
        ) {
            Text(
                text = "Detailed reports",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Column {
            Image(
                painter = painterResource(R.drawable.chart1),
                contentDescription = "chart1",
                modifier = Modifier.size(900.dp, 200.dp)
            )

            Image(
                painter = painterResource(R.drawable.chart2),
                contentDescription = "chart2",
                modifier = Modifier.size(800.dp, 400.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun StaffDashboardLandscape(
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    userViewModel.loadAllUsers()
    var users by remember { mutableStateOf(listOf(User())) }

    orderViewModel.loadAllOrders()
    var orders by remember { mutableStateOf(listOf(Order())) }

    var ordersAmount = orderViewModel.calculateOrderTotal(orders)

    var isLoading by remember { mutableStateOf(true) }
    if (isLoading) {
        IndeterminateCircularIndicator("Loading...")
    }

    LaunchedEffect(Unit) {
        launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                isLoading = false
                users = userViewModel.userListState.value.userList
                users = users.filter { it.status == "Active" }

                orders = orderViewModel.orderListState.value.orderList
                orders = orders.filter { it.orderStatus != "Pending" }

                ordersAmount = orderViewModel.calculateOrderTotal(orders)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Overview",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            dashboardCard(
                title = "User",
                value = users.size.toString(),
                icon = R.drawable.dashboard_1
            )

            dashboardCard(
                title = "Order",
                value = orders.size.toString(),
                icon = R.drawable.dashboard_2
            )

            dashboardCard(
                title = "Sales",
                value = formattedString(ordersAmount),
                icon = R.drawable.dashboard_3
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_large))
        ) {
            Text(
                text = "Detailed reports",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Column {
            Row {
                Image(
                    painter = painterResource(R.drawable.chart1),
                    contentDescription = "chart1",
                    modifier = Modifier.size(900.dp, 400.dp)
                )

                Image(
                    painter = painterResource(R.drawable.chart2),
                    contentDescription = "chart2",
                    modifier = Modifier.size(800.dp, 400.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun dashboardCard(
    title: String,
    value: String,
    @DrawableRes icon: Int,
) {
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = colorResource(R.color.primary),
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.LightGray,
            ),
        elevation = CardDefaults.cardElevation(15.dp),
        modifier = Modifier
            .size(350.dp, 200.dp)
    ) {
        Column(
            modifier = Modifier.padding(30.dp)
        ) {
            Row {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(150.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 40.sp
                    )

                    Text(
                        text = value,
                        fontWeight = FontWeight.Medium,
                        fontSize = 35.sp
                    )
                }

                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(icon),
                        contentDescription = title,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StaffDashboardPortraitPreview(){
    StaffDashboardScreen()
}

@Preview(showBackground = true, device = TABLET)
@Composable
fun StaffDashboardLandscapePreview(){
    StaffDashboardScreen()
}