package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.dataclass.OrderDetails
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.OrderDetailStatus
import com.example.apapunada.viewmodel.OrderDetailsListState
import com.example.apapunada.viewmodel.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OrderCartScreen(
    onBackButtonClicked: () -> Unit,
    onCheckoutClicked: () -> Unit,
    orderViewModel: OrderViewModel,
    menuViewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    val currentOrder = orderViewModel.orderState.value.order

    val orderDetailsListState = orderViewModel.orderDetailsListState.collectAsState(
        initial = OrderDetailsListState()
    )
    orderViewModel.loadOrderDetailsByOrderId(currentOrder.orderID)
    val orderDetails = orderDetailsListState.value.orderDetails

    var subtotal by remember { mutableStateOf(currentOrder.amount) }
    subtotal = orderViewModel.calculateOrderSubtotal(orderDetails)

    var isLoading by remember { mutableStateOf(true) }
    if (isLoading) {
        IndeterminateCircularIndicator("Loading cart...")
    }

    var isUpdating by remember { mutableStateOf(false) }
    if (isUpdating) {
        LaunchedEffect(Unit) {
            launch {
                delay(500)

                val orderDetail = orderViewModel.orderDetailState.value.orderDetails

                val latestOrderDetail = OrderDetails(
                    orderDetailsID = orderDetail.orderDetailsID,
                    orderID = orderDetail.orderID,
                    menuItemID = orderDetail.menuItemID,
                    quantity = orderDetail.quantity,
                    remark = orderDetail.remark,
                    option = orderDetail.option,
                    total = orderDetail.total,
                    status = getEnumList(OrderDetailStatus::class.java)[1]
                )

                orderViewModel.updateOrderDetailState(latestOrderDetail)
                orderViewModel.updateDetails()
                isUpdating = false
            }
        }
    }

    val primaryColor = R.color.primary

    Scaffold(
        topBar = { MyTopTitleBar(
            title = "Cart",
            onBackButtonClicked = { onBackButtonClicked() }
        ) },
        bottomBar = {
            Card(
                colors = CardDefaults.cardColors(colorResource(R.color.primary_200)),
                shape = RoundedCornerShape(15.dp, 15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxSize()
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = "Subtotal",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = "RM " + formattedString(subtotal),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Column {
                        Button(
                            onClick = {
                                val latestOrder = Order(
                                    orderID = currentOrder.orderID,
                                    userID = currentOrder.userID,
                                    method = currentOrder.method,
                                    amount = subtotal,
                                    dateTime = System.currentTimeMillis(),
                                    orderStatus = currentOrder.orderStatus
                                )
                                orderViewModel.updateOrderState(latestOrder)
                                orderViewModel.updateOrder()
                                onCheckoutClicked()
                            },
                            enabled = subtotal != 0.0,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = "Checkout",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_small)
                    )
                    .verticalScroll(rememberScrollState())

            ) {
                orderDetails.forEach { detail ->
                    menuViewModel.loadMenuItemByMenuItemId(detail.menuItemID)
                    var menu: MenuItem

                    do {
                        menu = menuViewModel.menuItemState.value.menuItem
                    } while (menu.menuItemID != detail.menuItemID)

                    Card(
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.elevatedCardElevation(5.dp),
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxSize()
                        ) {

                            Column {
                                Image(
                                    painter = painterResource(
                                        R.drawable.cabonarapastapic
                                    ),
                                    contentDescription = menu.image.toString(), // TODO
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(90.dp)
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .size(200.dp, 70.dp)
                            ) {
                                Text(
                                    text = menu.itemName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Text(
                                    text = detail.option
                                )

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .size(85.dp, 25.dp)
                                        .background(
                                            Color.LightGray,
                                            RoundedCornerShape(10.dp)
                                        )
                                ) {
                                    Text(
                                        text = "RM " + formattedString(detail.total),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                }
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                IconButton(
                                    onClick = {
                                        orderViewModel.loadOrderDetailsByOrderDetailsId(
                                            detail.orderDetailsID
                                        )
                                        isUpdating = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = "Add button",
                                        tint = colorResource(primaryColor),
                                        modifier = Modifier.size(35.dp)
                                    )
                                }

                                Text(
                                    text = "x ${detail.quantity}",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
            isLoading = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderCartScreenPreview() {
//    OrderCartScreen()
}