package com.example.apapunada.ui.users

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.PrepopulateData
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.data.dataclass.Voucher
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.MyAlertDialog
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedDate
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.OrderViewModel
import com.example.apapunada.viewmodel.UserViewModel
import com.example.apapunada.viewmodel.VoucherListState
import com.example.apapunada.viewmodel.VoucherViewModel
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCheckoutScreen(
    onBackButtonClicked: () -> Unit,
    onPayButtonClicked: () -> Unit,
    orderViewModel: OrderViewModel,
    authViewModel: AuthViewModel,
    menuItemViewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
    voucherViewModel: VoucherViewModel = viewModel(factory = AppViewModelProvider.Factory),
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    val currentOrder = orderViewModel.orderState.value.order

    orderViewModel.loadOrderDetailsByOrderId(currentOrder.orderID)
    val orderDetails = orderViewModel.orderDetailsListState.value.orderDetails

    val user = PrepopulateData.users[2] // TODO
    val voucherListState = voucherViewModel.voucherListState.collectAsState(
        initial = VoucherListState()
    )
    var vouchers: List<Voucher> = listOf()
    var promoCodeList: List<Voucher> = listOf()

    voucherViewModel.loadAllVouchers()

    if (voucherListState.value.isLoading) {
        IndeterminateCircularIndicator("Loading...")
    } else {
        if (voucherListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading orders: ${voucherListState.value.errorMessage}")
            Log.i("Order", "StaffOrderScreen: ${voucherListState.value.errorMessage}")
        } else {
            vouchers = voucherListState.value.voucherList
        }
    }

    if (vouchers.size > 1) {
        promoCodeList = voucherViewModel.getAvailableVoucher(
            vouchers, user.point, currentOrder.amount
        )
    }

    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    var selectedPromo by remember { mutableStateOf("") }
    var voucherCode by remember { mutableStateOf("") }

    val subtotal = currentOrder.amount

    val fees = when (currentOrder.method) {
        "Dine-in" -> {
            Pair("Service Charges (10%)", currentOrder.amount * 0.1)
        }
        "Takeaway" -> {
            Pair("Takeaway Charges (2%)", currentOrder.amount * 0.02)
        }
        "Delivery" -> {
            Pair("Delivery Charges (5%)", currentOrder.amount * 0.05)
        }
        else -> {
            Pair("Error", currentOrder.amount)
        }
    }

    val discount = when (voucherCode) {
        "VOUCHERRM1" -> {
            1.0
        }
        "VOUCHERRM3" -> {
            3.0
        }
        "VOUCHERRM10" -> {
            10.0
        }
        else -> {
            0.0
        }
    }

    var total by remember { mutableStateOf(0.0) }

    total = subtotal + fees.second - discount

    var points by remember { mutableStateOf(floor(total).toInt()) }
    points = floor(total).toInt()

    val datetime = System.currentTimeMillis()

    var isLoading by remember { mutableStateOf(true) }
    if (isLoading) {
        IndeterminateCircularIndicator("Loading...")
    }

    var checkout by remember { mutableStateOf(false) }
    if (checkout) {
        MyAlertDialog(
            onDismissRequest = { checkout = false },
            onConfirmation = {
                val latestOrder = Order(
                    orderID = currentOrder.orderID,
                    userID = currentOrder.userID,
                    method = currentOrder.method,
                    amount = total,
                    dateTime = System.currentTimeMillis(),
                    orderStatus = currentOrder.orderStatus
                )
                orderViewModel.updateOrderState(latestOrder)
                orderViewModel.updateOrder()

                val usedPoints = when (voucherCode) {
                    "VOUCHERRM1" -> {
                        100
                    }

                    "VOUCHERRM3" -> {
                        300
                    }

                    "VOUCHERRM10" -> {
                        1000
                    }

                    else -> {
                        0
                    }
                }

                val currentUser = authViewModel.userState.value.user
                userViewModel.loadUserByUserId(currentUser.userID)
                val latestPoints = currentUser.point - usedPoints + points
                userViewModel.updateUserState(
                    User(
                        userID = currentUser.userID,
                        username = currentUser.username,
                        email = currentUser.email,
                        password = currentUser.password,
                        phoneNo = currentUser.phoneNo,
                        gender = currentUser.gender,
                        dob = currentUser.dob,
                        image = currentUser.image,
                        role = currentUser.role,
                        point = latestPoints,
                        status = currentUser.status,
                    )
                )
                userViewModel.updateUser()

                onPayButtonClicked()
            },
            dialogTitle = "Confirm order?",
            dialogText = "You can't edit your order again after you proceed to payment",
            icon = Icons.Default.Warning
        )
    }

    val headerList = listOf(
        Pair("No.", 45.dp),
        Pair("Title", 160.dp),
        Pair("Qty", 50.dp),
        Pair("Amount (RM)", 105.dp),
    )

    Scaffold(
        topBar = {
            MyTopTitleBar(
                title = "Checkout",
                onBackButtonClicked = onBackButtonClicked
            )
        },
        bottomBar = {
            MyBottomButton(
                content = "Proceed to Payment",
                onClick = { checkout = true }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_small)
                    )
                    .verticalScroll(rememberScrollState())
            ) {

                // PromoCode
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
                            .fillMaxWidth()
                    ) {

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            if (promoCodeList.isNotEmpty()) {
                                OutlinedTextField(
                                    value = selectedPromo,
                                    onValueChange = { selectedPromo = it },
                                    label = { Text(text = "Promo Code") },
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxHeight()
                                        .width(250.dp)
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { /* nothing */ }
                                ) {
                                    promoCodeList.forEach { promo ->
                                        DropdownMenuItem(
                                            text = { Text(text = promo.code) },
                                            onClick = {
                                                selectedPromo = promo.code
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            } else {
                                OutlinedTextField(
                                    value = "Not enough points",
                                    onValueChange = { selectedPromo = it },
                                    label = { Text(text = "Promo Code") },
                                    enabled = false,
                                    readOnly = true,
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxHeight()
                                        .width(250.dp)
                                )
                            }
                        }

                        if (promoCodeList.isNotEmpty()) {
                            Button(
                                onClick = {
                                    voucherCode = selectedPromo
                                    Toast.makeText(
                                        context,
                                        "Voucher Applied",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                },
                                shape = RoundedCornerShape(5.dp),
                                modifier = Modifier
                                    .height(55.dp)
                                    .width(85.dp)
                                    .padding(top = 8.dp)
                            ) {
                                Text(
                                    text = "Apply",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        } else {
                            Button(
                                onClick = { },
                                enabled = false,
                                shape = RoundedCornerShape(5.dp),
                                modifier = Modifier
                                    .height(55.dp)
                                    .width(85.dp)
                                    .padding(top = 8.dp)
                            ) {
                                Text(
                                    text = "Apply",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Date: " + formattedDate(datetime, "date"),
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Time: " + formattedDate(datetime, "time"),
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Whole Receipt
                Column {
                    // Order Details
                    Column {
                        //header
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
                            headerList.forEach { header ->
                                Text(
                                    text = header.first,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .width(header.second)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // table body
                        orderDetails.forEachIndexed() { i, detail ->
                            menuItemViewModel.loadMenuItemByMenuItemId(detail.menuItemID)
                            var menuItem: MenuItem

                            do {
                                menuItem = menuItemViewModel.menuItemState.value.menuItem
                            } while (menuItem.menuItemID != detail.menuItemID)

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(
                                    text = (i + 1).toString() + ".",
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .width(headerList[0].second)
                                )

                                Text(
                                    text = menuItem.itemName,
                                    fontSize = 15.sp,
                                    overflow = TextOverflow.Visible,
                                    modifier = Modifier
                                        .width(headerList[1].second)
                                )

                                Text(
                                    text = detail.quantity.toString(),
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .width(headerList[2].second)
                                )

                                Text(
                                    text = formattedString(detail.total),
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .width(headerList[3].second)
                                        .padding(end = 10.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    // price info
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = "Subtotal",
                                fontSize = 15.sp,
                            )
                            Text(
                                text = formattedString(subtotal),
                                fontSize = 15.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.width(headerList[3].second)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = fees.first,
                                fontSize = 15.sp,
                            )
                            Text(
                                text = formattedString(fees.second),
                                fontSize = 15.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.width(headerList[3].second)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = "Discount",
                                fontSize = 15.sp,
                            )
                            Text(
                                text = "- " + formattedString(discount),
                                fontSize = 15.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.width(headerList[3].second)
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Column(
                        modifier = Modifier.height(25.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Total  ",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "(${points} Points Earned)",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 13.sp
                                )
                            }
                            Text(
                                text = "RM " + formattedString(total),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
            isLoading = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderCheckoutScreenPreview() {
//    OrderCheckoutScreen()
}