package com.example.apapunada.ui.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.OrderStatus
import com.example.apapunada.viewmodel.OrderViewModel
import com.example.apapunada.viewmodel.PaymentStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderPaymentScreen(
    onPayButtonClicked: (String) -> Unit,
    onCancelButtonClicked: (String) -> Unit,
    orderViewModel: OrderViewModel,
) {

    val currentOrder = orderViewModel.orderState.value.order

    val total = currentOrder.amount

    val paymentMethod = listOf(
        Pair("Card", R.drawable.payment_mastercard),
        Pair("Touch 'n Go", R.drawable.payment_tng),
        Pair("PayPal", R.drawable.payment_paypal),
    )

    val (selectedMethod, onOptionSelected) = remember { mutableStateOf(paymentMethod[0]) }

    var transaction by remember { mutableStateOf(false) }
    if (transaction) {
        IndeterminateCircularIndicator("Processing Transaction...")
    }

    var pay by remember { mutableStateOf(false) }
    if (pay) {
        transaction = true
        val latestOrder = Order(
            orderID = currentOrder.orderID,
            userID = currentOrder.userID,
            method = currentOrder.method,
            amount = total,
            dateTime = System.currentTimeMillis(),
            paymentStatus = getEnumList(PaymentStatus::class.java)[0],
            orderStatus = getEnumList(OrderStatus::class.java)[1]
        )
        orderViewModel.updateOrderState(latestOrder)
        orderViewModel.updateOrder()
        LaunchedEffect(Unit) {
            launch {
                delay(5000)
                transaction = false
                onPayButtonClicked(selectedMethod.first)
            }
        }
    }

    Scaffold(
        topBar = {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.primary_200),
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        text = "Payment",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(250.dp)
                    )
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(10.dp)
            )
        },
        bottomBar = {
            Card(
                colors = CardDefaults.cardColors(colorResource(R.color.primary_200)),
                shape = RoundedCornerShape(15.dp, 15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = { pay = true },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium), 8.dp)
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text(
                        text = "Pay now",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        val latestOrder = Order(
                            orderID = currentOrder.orderID,
                            userID = currentOrder.userID,
                            method = currentOrder.method,
                            amount = total,
                            dateTime = System.currentTimeMillis(),
                            paymentStatus = getEnumList(PaymentStatus::class.java)[1],
                            orderStatus = getEnumList(OrderStatus::class.java)[4]
                        )
                        orderViewModel.updateOrderState(latestOrder)
                        orderViewModel.updateOrder()
                        onCancelButtonClicked(selectedMethod.first)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium), 8.dp)
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text(
                        text = "Cancel Order",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {

                Text(
                    text = "Total",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "RM " + formattedString(total),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Select method",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                paymentMethod.forEach { method ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(
                                dimensionResource(R.dimen.padding_medium),
                                dimensionResource(R.dimen.padding_small)
                            )
                            .border(
                                border =
                                if (method == selectedMethod) {
                                    BorderStroke(2.dp, colorResource(R.color.primary))
                                } else {
                                    BorderStroke(1.dp, Color.LightGray)
                                },
                                shape = RoundedCornerShape(5.dp)
                            )
                            .selectable(
                                selected = (method == selectedMethod),
                                onClick = { onOptionSelected(method) }
                            )
                    ) {
                        Image(
                            painter = painterResource(method.second),
                            contentDescription = method.first,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(start = 10.dp)
                        )

                        Text(
                            text = method.first,
                            fontSize = 16.sp,
                        )

                        RadioButton(
                            selected = (method == selectedMethod),
                            onClick = { onOptionSelected(method) }
                        )
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun OrderPaymentScreenPreview() {
//    OrderPaymentScreen()
}