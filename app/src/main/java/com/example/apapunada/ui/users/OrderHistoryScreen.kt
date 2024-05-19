package com.example.apapunada.ui.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedDate
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.OrderViewModel

@Composable
fun OrderHistoryScreen(
    onBackClicked: () -> Unit,
    authViewModel: AuthViewModel,
    orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val currentUserId = authViewModel.userState.value.user.userID
    orderViewModel.loadDescOrderByUserId(currentUserId)
    val orders = orderViewModel.orderListState.value.orderList

    Scaffold(
        topBar = { MyTopTitleBar(title = "Order History", onBackClicked ) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                if (orders.isEmpty()) {
                    Text(
                        text = "No records found.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    orders.forEach { order ->

                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            colors = CardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.LightGray,
                                disabledContentColor = Color.Gray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append("Order ID : ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append(order.orderID.toString())
                                        }
                                    }
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append("Method : ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append(order.method)
                                        }
                                    }
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append("Amount : ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append("RM" + formattedString(order.amount))
                                        }
                                    }
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append("Date : ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append(formattedDate(order.dateTime, "date"))
                                        }
                                    }
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append("Time : ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append(formattedDate(order.dateTime, "time"))
                                        }
                                    }
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append("Payment Status : ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append(order.paymentStatus)
                                        }
                                    }
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append("Order Status : ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            append(order.orderStatus)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderHistoryScreenPreview() {
//    OrderHistoryScreen({})
}