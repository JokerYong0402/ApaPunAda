package com.example.apapunada.ui.staff

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.dataclass.OrderDetails
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DropDownMenu
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.SearchBar
import com.example.apapunada.ui.components.formattedDate
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.OrderDetailsListState
import com.example.apapunada.viewmodel.OrderListState
import com.example.apapunada.viewmodel.OrderStatus
import com.example.apapunada.viewmodel.OrderViewModel
import com.example.apapunada.viewmodel.UserViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffOrderScreen(
    orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory),
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    menuItemViewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    val orderListState = orderViewModel.orderListState.collectAsState(initial = OrderListState())
    val orderDetailsListState = orderViewModel.orderDetailsListState
        .collectAsState(initial = OrderDetailsListState())

    var orders: List<Order> = listOf()

    var searchInput by remember { mutableStateOf("") }
    var selectedField by remember { mutableStateOf("") }

    orderViewModel.loadAllOrders()

    if (orderListState.value.isLoading) {
        IndeterminateCircularIndicator("Loading Order...")
    } else {
        if (orderListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading orders: ${orderListState.value.errorMessage}")
            Log.i("Order", "StaffOrderScreen: ${orderListState.value.errorMessage}")
        } else {
            orders = orderListState.value.orderList
        }
    }

    orders = orders.filter { it.orderStatus != "Pending" }

    if (searchInput.isNotBlank()) {
        when (selectedField) {
            "OrderID" -> orders = orders.filter { it.orderID.toString() == searchInput }
            "Amount" -> orders = orders.filter { it.amount.toString().contains(searchInput) }
            "Status" -> orders = orders.filter { it.orderStatus.contains(searchInput) }
        }
    }

    var editButton by remember { mutableStateOf(false) }
    var detailButton by remember { mutableStateOf(false) }
    var currentOrder by remember { mutableStateOf(Order()) } // TODO
    var currentDetails by remember { mutableStateOf(listOf(OrderDetails())) }

    val headerList = listOf(
        // (Header name, Column width)
        Pair("  No.", 80.dp),
        Pair("Order ID", 145.dp),
        Pair("Username", 210.dp),
        Pair("Amount", 160.dp),
        Pair("Date", 170.dp),
        Pair("Time", 150.dp),
        Pair("Status", 160.dp),
        Pair("Action", 150.dp),
    )

    if (editButton) {
        DialogOfEditOrder(
            onDismissRequest = { editButton = false },
            onConfirmation = {
                val latestOrder = it
                orderViewModel.updateOrderState(latestOrder)
                orderViewModel.updateOrder()
                editButton = false
            },
            order = currentOrder
        )
    }

    if (detailButton) {
        orderViewModel.loadOrderDetailsByOrderId(currentOrder.orderID)
        currentDetails = orderDetailsListState.value.orderDetails

        DialogOfOrderDetail(
            onDismissRequest = { detailButton = false },
            order = currentOrder,
            details = currentDetails,
            labelList = headerList,
            userViewModel = userViewModel,
            menuItemViewModel = menuItemViewModel
        )
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
    ) {

        Row {
            selectedField = DropDownMenu(listOf("OrderID", "Amount", "Status"))
            SearchBar(
                value = searchInput,
                onValueChange = { searchInput = it },
                modifier = Modifier
            )
        }
        
        Spacer(modifier = Modifier.height(10.dp))

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

            items(orders.size) { i ->
                val o = orders[i]
                userViewModel.loadUserByUserId(o.userID)
                var user: User

                do {
                    user = userViewModel.userState.value.user
                } while (user.userID != o.userID)

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
                            .width(headerList[0].second)
                            .padding(start = 20.dp)
                    )

                    Text(
                        text = o.orderID.toString(),
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[1].second)
                    )

                    Row {
                        Text(
                            text = user.username,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[2].second)
                        )
                    }

                    Text(
                        text = "RM " + formattedString(o.amount),
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[3].second)
                    )

                    Text(
                        text = formattedDate(o.dateTime, "date"),
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[4].second)
                    )

                    Text(
                        text = formattedDate(o.dateTime, "time"),
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[5].second)
                    )

                    Text(
                        text = o.orderStatus,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .width(headerList[6].second)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(headerList[7].second)
                    ) {
                        IconButton(
                            onClick = {
                                currentOrder = o
                                editButton = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.editicon),
                                contentDescription = "Edit button",
                                modifier = Modifier.size(30.dp)
                            )
                            
                        }

                        IconButton(
                            onClick = {
                                currentOrder = o
                                detailButton = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.emailicon),
                                contentDescription = "Detail button",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogOfEditOrder(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (Order) -> Unit,
    order: Order
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(order.orderStatus) }

    val orderStatusList = getEnumList(OrderStatus::class.java)

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = selectedStatus,
                        onValueChange = {},
                        label = { Text(text = "Status") },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { /*TODO*/ }
                    ) {
                        orderStatusList.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(text = status) },
                                onClick = {
                                    selectedStatus = status
                                    expanded = false
                                    Toast.makeText(context, status, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        }
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Dismiss")
                    }

                    TextButton(
                        onClick = {
                            onConfirmation(
                                Order(
                                    orderID = order.orderID,
                                    userID = order.userID,
                                    method = order.method,
                                    amount = order.amount,
                                    dateTime = order.dateTime,
                                    paymentStatus = order.paymentStatus,
                                    orderStatus = selectedStatus
                                )
                            )
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DialogOfOrderDetail(
    onDismissRequest: () -> Unit = {},
    order: Order,
    details: List<OrderDetails>,
    labelList: List<Pair<String, Dp>>,
    userViewModel: UserViewModel,
    menuItemViewModel: MenuItemViewModel
) {

    val orderHeader = listOf(
        // (Header name, Column width)
        Pair("No.", 50.dp),
        Pair("Food Name", 165.dp),
        Pair("Qty", 60.dp),
        Pair("Price", 100.dp),
        Pair("Remark", 200.dp),
    )

    userViewModel.loadUserByUserId(order.userID)
    val user = userViewModel.userState.value.user

    val fDetails = details.filter { it.status != getEnumList(OrderStatus::class.java)[0] }

    //screen width
    val config  = LocalConfiguration.current
    val width by remember(config) {
        mutableStateOf(config.screenWidthDp)
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(520.dp)
                ) {
                    if (width <= 600) {
                        Column {
                            Column {
                                Text(
                                    text = labelList[1].first,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 22.sp
                                )
                                Text(
                                    text = order.orderID.toString(),
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            Column {
                                Text(
                                    text = labelList[2].first,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 22.sp
                                )
                                Text(
                                    text = user.username,
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            Column {
                                Text(
                                    text = labelList[3].first,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 22.sp
                                )
                                Text(
                                    text = "RM " + formattedString(order.amount),
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            Column {
                                Text(
                                    text = labelList[4].first,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 22.sp
                                )
                                Text(
                                    text = formattedDate(order.dateTime, "date"),
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            Column {
                                Text(
                                    text = labelList[5].first,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 22.sp
                                )
                                Text(
                                    text = formattedDate(order.dateTime, "time"),
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            Column {
                                Text(
                                    text = labelList[6].first,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 22.sp
                                )
                                Text(
                                    text = order.orderStatus,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(50.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(15.dp),
                                modifier = Modifier.width(150.dp)
                            ) {
                                Column {
                                    Text(
                                        text = labelList[1].first,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 22.sp
                                    )
                                    Text(
                                        text = order.orderID.toString(),
                                        fontSize = 20.sp
                                    )
                                }

                                Column {
                                    Text(
                                        text = labelList[2].first,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 22.sp
                                    )
                                    Text(
                                        text = user.username,
                                        fontSize = 20.sp
                                    )
                                }

                                Column {
                                    Text(
                                        text = labelList[3].first,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 22.sp
                                    )
                                    Text(
                                        text = "RM " + formattedString(order.amount),
                                        fontSize = 20.sp
                                    )
                                }
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(15.dp),
                                modifier = Modifier.width(150.dp)
                            ) {
                                Column {
                                    Text(
                                        text = labelList[4].first,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 22.sp
                                    )
                                    Text(
                                        text = formattedDate(order.dateTime, "date"),
                                        fontSize = 20.sp
                                    )
                                }

                                Column {
                                    Text(
                                        text = labelList[5].first,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 22.sp
                                    )
                                    Text(
                                        text = formattedDate(order.dateTime, "time"),
                                        fontSize = 20.sp
                                    )
                                }

                                Column {
                                    Text(
                                        text = labelList[6].first,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 22.sp
                                    )
                                    Text(
                                        text = order.orderStatus,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Text(
                            text = "Food Details",
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {

                        LazyColumn(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                        ) {
                            // header row
                            stickyHeader {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .background(
                                            colorResource(R.color.primary_200),
                                            RoundedCornerShape(10.dp)
                                        )
                                ) {
                                    orderHeader.forEach { header ->
                                        Text(
                                            text = header.first,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .width(header.second)
                                        )
                                    }
                                }
                            }

                            items(fDetails.size) { i ->
                                val detail = fDetails[i]

                                menuItemViewModel.loadMenuItemByMenuItemId(detail.menuItemID)
                                val food = menuItemViewModel.menuItemState.value.menuItem

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                ) {
                                    Text(
                                        text = (i + 1).toString(),
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .width(orderHeader[0].second)
                                    )

                                    Text(
                                        text = food.itemName,
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .width(orderHeader[1].second)
                                    )

                                    Text(
                                        text = detail.quantity.toString(),
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .width(orderHeader[2].second)
                                    )

                                    Text(
                                        text = "RM" + formattedString(detail.total),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .width(orderHeader[3].second)
                                    )

                                    Text(
                                        text = detail.remark,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .width(orderHeader[4].second)
                                    )
                                }
                            }
                        }
                    }

                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Dismiss")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = TABLET)
@Composable
fun StaffOrderScreenPreview() {
    StaffOrderScreen()
}

@Preview(showBackground = true, device = PHONE)
@Composable
fun StaffOrderScreenPhonePreview() {
    StaffOrderScreen()
}