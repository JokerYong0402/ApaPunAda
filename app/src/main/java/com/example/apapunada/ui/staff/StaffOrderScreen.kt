package com.example.apapunada.ui.staff

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.apapunada.R
import com.example.apapunada.data.OrderSample.Orders
import com.example.apapunada.data.OrderStatusSample
import com.example.apapunada.model.Order
import com.example.apapunada.model.OrderStatus
import com.example.apapunada.ui.components.formattedString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffOrderScreen(
    orders: List<Order> = Orders
) {
    var editButton by remember { mutableStateOf(false) }
    var detailButton by remember { mutableStateOf(true) }

    val headerList = listOf(
        // (Header name, Column width)
        Pair("No.", 70.dp),
        Pair("Order ID", 165.dp),
        Pair("Username", 250.dp),
        Pair("Amount", 160.dp),
        Pair("Date", 170.dp),
        Pair("Time", 150.dp),
        Pair("Status", 160.dp),
        Pair("Action", 100.dp),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
    ) {
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
                    headerList.forEach() { header ->
                        Text(
                            text = header.first,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .width(header.second)
                        )
                    }
                }
            }

            items(orders.size) { i ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Text(
                        text = orders[i].id.toString(),
                        fontSize = 28.sp,
                        modifier = Modifier
                            .width(headerList[0].second)
                    )

                    Text(
                        text = orders[i].orderId.toString(),
                        fontSize = 28.sp,
                        modifier = Modifier
                            .width(headerList[1].second)
                    )

                    Row {
//                        Icon(painter = painterResource(R.id))
                        Text(
                            text = orders[i].username,
                            fontSize = 28.sp,
                            modifier = Modifier
                                .width(headerList[2].second)
                        )
                    }

                    Text(
                        text = "RM " + formattedString(orders[i].amount),
                        fontSize = 28.sp,
                        modifier = Modifier
                            .width(headerList[3].second)
                    )

                    Text(
                        text = orders[i].date.toString(),
                        fontSize = 28.sp,
                        modifier = Modifier
                            .width(headerList[4].second)
                    )

                    Text(
                        text = orders[i].time.toString(),
                        fontSize = 28.sp,
                        modifier = Modifier
                            .width(headerList[5].second)
                    )

                    Text(
                        text = orders[i].statusId.status,
                        fontSize = 28.sp,
                        modifier = Modifier
                            .width(headerList[6].second)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(headerList[7].second)
                    ) {
                        IconButton(onClick = { editButton = true }) {
                            Icon(
                                painter = painterResource(R.drawable.editicon),
                                contentDescription = "Edit button",
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        if (editButton) {
                            DialogOfEditOrder(
                                onDismissRequest = { editButton = false },
                                onConfirmation = {
                                    editButton = false
                                    // save data
                                },
                                order = orders[i]
                            )
                        }

                        IconButton(onClick = { detailButton = true }) {
                            Icon(
                                painter = painterResource(R.drawable.emailicon),
                                contentDescription = "Detail button",
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        if (detailButton) {
                            DialogOfOrderDetail(
                                onDismissRequest = { detailButton = false },
                                order = orders[0], // TODO i
                                labelList = headerList
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
    onConfirmation: () -> Unit,
    order: Order,
    orderStatus: List<OrderStatus> = OrderStatusSample.OrderStatus
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(order.statusId) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
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
                        value = selectedStatus.status,
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
                        orderStatus.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(text = status.status) },
                                onClick = {
                                    selectedStatus = status
                                    expanded = false
                                    Toast.makeText(context, status.status, Toast.LENGTH_SHORT)
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

                    TextButton(onClick = { onConfirmation() }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DialogOfOrderDetail(
    onDismissRequest: () -> Unit = {},
    order: Order,
    labelList: List<Pair<String, Dp>>
) {
    val details = order.orderDetails

    val orderHeader = listOf(
        // (Header name, Column width)
        Pair("No.", 50.dp),
        Pair("Food Name", 165.dp),
        Pair("Qty", 60.dp),
        Pair("Price", 100.dp),
        Pair("Remark", 200.dp),
    )

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
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
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(50.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                text = labelList[1].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = order.orderId.toString(),
                                fontSize = 20.sp
                            )

                            Text(
                                text = labelList[2].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = order.username.toString(),
                                fontSize = 20.sp
                            )

                            Text(
                                text = labelList[3].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = order.orderId.toString(),
                                fontSize = 20.sp
                            )
                        }

                        Column(
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text(
                                text = labelList[4].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = order.date.toString(),
                                fontSize = 20.sp
                            )

                            Text(
                                text = labelList[5].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = order.time,
                                fontSize = 20.sp
                            )

                            Text(
                                text = labelList[6].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = order.statusId.status,
                                fontSize = 20.sp
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.height(300.dp)
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
                                        .height(60.dp)
                                        .background(
                                            colorResource(R.color.primary_200),
                                            RoundedCornerShape(10.dp)
                                        )
                                ) {
                                    orderHeader.forEach() { header ->
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

                            items(details.size) { i ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                ) {
                                    Text(
                                        text = details[i].id.toString(),
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .width(orderHeader[0].second)
                                    )

                                    Text(
                                        text = details[i].menuId.name,
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .width(orderHeader[1].second)
                                    )

                                    Text(
                                        text = details[i].qty.toString(),
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .width(orderHeader[2].second)
                                    )

                                    Text(
                                        text = "RM" + formattedString(details[i].total),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .width(orderHeader[3].second)
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