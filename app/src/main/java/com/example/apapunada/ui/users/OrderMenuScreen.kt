package com.example.apapunada.ui.users

import android.util.Log
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.MyAlertDialog
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.Cuisine
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.MenuListState
import com.example.apapunada.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun OrderMenuScreen(
    onBackButtonClicked: () -> Unit,
    onAddButtonClicked: (Int) -> Unit,
    onCheckoutButtonClicked: () -> Unit,
    orderViewModel: OrderViewModel,
    menuViewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val primaryColor = R.color.primary
    val tabs = getEnumList(Cuisine::class.java)

    orderViewModel.getLatestOrderID()
    val orderID = orderViewModel.orderIdState.value.orderID

    // load current order
    orderViewModel.loadOrderByOrderId(orderID)
    val currentOrder = orderViewModel.orderState.value.order

    // load orderDetails to get number and calculate subtotal
    orderViewModel.loadOrderDetailsByOrderId(orderID)
    val orderDetailsList = orderViewModel.orderDetailsListState.value.orderDetails
    var detailsNumber = orderViewModel.calculateDetailsNumber(orderDetailsList)
    var detailsAmount = orderViewModel.calculateOrderSubtotal(orderDetailsList)

    // load menu to list
    val menuListState = menuViewModel.menuListState.collectAsState(initial = MenuListState())
    var orderMenu: List<MenuItem> = listOf()

    menuViewModel.loadAllMenuItem() // TODO load only active

    if (menuListState.value.isLoading) {
        IndeterminateCircularIndicator("Loading menu...")
    } else {
        if (menuListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading menus: ${menuListState.value.errorMessage}")
            Log.i("Menu", "StaffMenuScreen: ${menuListState.value.errorMessage}")
        } else {
            orderMenu = menuListState.value.menuItemList
        }
    }

    var back by remember { mutableStateOf(false) }
    if (back) {
        MyAlertDialog(
            onDismissRequest = { back = false },
            onConfirmation = { onBackButtonClicked() },
            dialogTitle = "Confirm Discard?",
            dialogText = "You will lost your orders if you leave.",
            icon = Icons.Default.Warning
        )
    }

    var checkout by remember { mutableStateOf(false) }
    if (checkout) {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = "Warning Icon"
                )
            },
            title = {
                Text(text = "Oops!")
            },
            text = {
                Text(text = "You can't checkout because your cart is empty.")
            },
            onDismissRequest = { },
            confirmButton = {
                TextButton(
                    onClick = { checkout = false }
                ) {
                    Text("Dismiss")
                }
            },
        )
    }

    Scaffold(
        topBar = {
            MyTopTitleBar(
                title = "Order",
                onBackButtonClicked = { back = true }
            )
        },
        bottomBar = {
            MyBottomButton(
                content = "Checkout",
                order = currentOrder,
                detailCount = detailsNumber,
                amount = detailsAmount,
                onClick = {
                    detailsNumber = orderViewModel.calculateDetailsNumber(orderDetailsList)
                    if (detailsNumber > 0) {
                        val latestOrder = Order(
                            orderID = currentOrder.orderID,
                            userID = currentOrder.userID,
                            method = currentOrder.method,
                            amount = orderViewModel.calculateOrderSubtotal(orderDetailsList),
                            dateTime = System.currentTimeMillis(),
                            orderStatus = currentOrder.orderStatus
                        )
                        orderViewModel.updateOrderState(latestOrder)
                        orderViewModel.updateOrder()
                        onCheckoutButtonClicked()
                    } else {
                        checkout = true
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row {
                    ScrollableTabRow(
                        selectedTabIndex = 0,
                        contentColor = Color.Black,
                        edgePadding = 0.dp,
                        modifier = Modifier.weight(1f)
                    ) {
                        tabs.forEach { tab ->
                            Tab(
                                selected = true,
                                onClick = {
                                    coroutineScope.launch {
                                              scrollState.animateScrollTo(300)
                                              /*TODO*/
                                          }
                                },
                                text = { Text(text = tab) }
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .verticalScroll(scrollState)
                ) {

                    tabs.forEach { tab ->
                        Text(
                            text = tab,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        orderMenu.forEach { menu ->
                            if (menu.cuisine == tab
                                && menu.status == "Active") {
                                Card(
                                    colors = CardDefaults.cardColors(Color.White),
                                    modifier = Modifier
                                        .height(100.dp)
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                        .shadow(
                                            elevation = 15.dp,
                                            spotColor = colorResource(primaryColor)
                                        )
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
                                                contentDescription = menu.itemName,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(90.dp)
                                            )
                                        }

                                        Column(
                                            verticalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .size(200.dp, 55.dp)
                                        ) {
                                            Text(
                                                text = menu.itemName,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
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
                                                    text = "RM " + formattedString(menu.price),
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.Medium,
                                                )
                                            }
                                        }

                                        Column{
                                            IconButton(
                                                onClick = {
                                                    val menuItemID = menu.menuItemID
                                                    onAddButtonClicked(menuItemID)
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.AddCircle,
                                                    contentDescription = "Add button",
                                                    tint = colorResource(primaryColor),
                                                    modifier = Modifier.size(35.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderMenuScreenPreview() {
//    OrderMenuScreen()
}