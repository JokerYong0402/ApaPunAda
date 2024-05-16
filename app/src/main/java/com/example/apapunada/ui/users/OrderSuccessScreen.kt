package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.viewmodel.OrderViewModel

@Composable
fun OrderSuccessScreen(
    onBackToHomeClicked: () -> Unit = {},
    onOrderDetailsClicked: () -> Unit = {},
    paymentMethod: String,
    orderViewModel: OrderViewModel
){

    val currentOrder = orderViewModel.orderState.value.order

    orderViewModel.loadOrderDetailsByOrderId(currentOrder.orderID)
    val orderDetails = orderViewModel.orderDetailsListState.value.orderDetails

    val total = currentOrder.amount

    val subtotal = orderViewModel.calculateOrderSubtotal(orderDetails)

    val fees = when (currentOrder.method) {
        "Dine-in" -> {
            Pair("Service Charges (10%)", subtotal * 0.1)
        }
        "Takeaway" -> {
            Pair("Takeaway Charges (2%)", subtotal * 0.02)
        }
        "Delivery" -> {
            Pair("Delivery Charges (5%)", subtotal * 0.05)
        }
        else -> {
            Pair("Error", subtotal)
        }
    }

    val discount = (subtotal + fees.second) - total

    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.feedback4),
                contentDescription = "feedback4",
                modifier = Modifier
                    .size(100.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "Order placed successfully",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.primary),
                modifier = Modifier
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "Thank you!\n Your order will be served soon.",
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier.padding(10.dp, 50.dp)
        ) {
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
                        text = formattedString(subtotal), // calculate subtotal
                        fontSize = 15.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(105.dp)
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
                    // get order method if delivery = deliveryFee
                    // else if takeaway = takeawayFee else ServiceTax
                    Text(
                        text = formattedString(fees.second),
                        fontSize = 15.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(105.dp)
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
                        text = formattedString(discount), // calculate subtotal
                        fontSize = 15.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(105.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Method",
                        fontSize = 15.sp,
                    )

                    Text(
                        text = paymentMethod,
                        fontSize = 15.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(105.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

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
                    Text(
                        text = "Total  ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Success",
                            fontWeight = FontWeight.Light,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF0e642a),
                            modifier = Modifier
                                .background(Color(0xFFeefdf3))
                                .width(55.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "RM " + formattedString(total), // TODO
                            // calculate total in viewmodel
                            // assign to point also
                            fontSize = 20.sp,
                            color = Color(0xFF0e642a),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth()
                ,
                onClick = onOrderDetailsClicked
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Order Details button"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Order Details",
                    fontSize = 18.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth()
                ,
                onClick = onBackToHomeClicked
            ) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Back to home button"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Back to home",
                    fontSize = 18.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderSuccessPreview () {
//    OrderSuccessScreen()
}