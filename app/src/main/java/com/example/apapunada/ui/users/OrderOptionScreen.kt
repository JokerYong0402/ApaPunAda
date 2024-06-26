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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.OrderStatus
import com.example.apapunada.viewmodel.OrderViewModel

@Composable
fun OrderOptionScreen(
    onBackButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    viewModel: OrderViewModel,
    authViewModel: AuthViewModel,
) {

    val currentUserID = authViewModel.userState.value.user.userID

    val orderOptions = listOf(
        Pair("Dine-in", R.drawable.ordermethod1),
        Pair("Takeaway", R.drawable.ordermethod2),
        Pair("Delivery", R.drawable.ordermethod3),
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(orderOptions[0]) }

    Scaffold(
        topBar = { MyTopTitleBar(title = "Order", onBackButtonClicked) },
        bottomBar = {
            MyBottomButton(
                content = "Next",
                onClick = {
                    val latestOrder = Order(
                        userID = currentUserID,
                        method = selectedOption.first,
                        orderStatus = getEnumList(OrderStatus::class.java)[0]
                    )
                    viewModel.updateOrderState(latestOrder)
                    viewModel.saveOrder()
                    onNextButtonClicked()
                }
            )
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
                    text = "Select method",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                // TODO if dine-in or delivery, need pop up
                orderOptions.forEach { method ->
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
                                if (method == selectedOption) {
                                    BorderStroke(2.dp, colorResource(R.color.primary))
                                } else {
                                    BorderStroke(1.dp, Color.LightGray)
                                },
                                shape = RoundedCornerShape(5.dp)
                            )
                            .selectable(
                                selected = (method == selectedOption),
                                onClick = { onOptionSelected(method) }
                            )
                    ) {
                        Image(
                            painter = painterResource(method.second),
                            contentDescription = method.first,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(start = 10.dp)
                        )

                        Text(
                            text = method.first,
                            fontSize = 16.sp,
                        )

                        RadioButton(
                            selected = (method == selectedOption),
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
fun OrderOptionScreenPreview() {
//    OrderOptionScreen()
}