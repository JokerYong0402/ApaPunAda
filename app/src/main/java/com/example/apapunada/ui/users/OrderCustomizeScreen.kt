package com.example.apapunada.ui.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.OrderDetails
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.OrderDetailStatus
import com.example.apapunada.viewmodel.OrderViewModel

@Composable
fun OrderCustomizeScreen(
    onButtonClicked: () -> Unit,
    menuItemID: Int,
    orderViewModel: OrderViewModel,
    menuViewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    val currentOrder = orderViewModel.orderState.value.order

    menuViewModel.loadMenuItemByMenuItemId(menuItemID)
    val currentMenu = menuViewModel.menuItemState.value.menuItem

    var quantity by remember { mutableStateOf(1) }
    var remark by remember { mutableStateOf("") }
    var optionSelected by remember { mutableStateOf(1) }
    var total by remember { mutableStateOf(0.0) }
    if (total == 0.0) {
        total = currentMenu.price
    }

    val detailOption = if (currentMenu.cuisine == "Beverage") {
        when (optionSelected) {
            1 -> {
                "Hot"
            }

            else -> {
                "Cold"
            }
        }
    } else {
        when (optionSelected) {
            1 -> {
                "Small"
            }

            2 -> {
                "Medium"
            }

            else -> {
                "Large"
            }
        }
    }

    val temperatureOptions = listOf(
        Pair("Hot", 0.0),
        Pair("Cold", 1.0),
    )

    val sizeOptions = listOf(
        Pair("Small", 0.0),
        Pair("Medium", 1.5),
        Pair("Large", 3.0),
    )

    Scaffold(
        bottomBar = {
            MyBottomButton(
                content = "Add to Cart",
                price = total,
                onClick = {
                    val orderDetail = OrderDetails(
                        orderID = currentOrder.orderID,
                        menuItemID = menuItemID,
                        quantity = quantity,
                        remark = remark,
                        option = detailOption,
                        total = total,
                        status = getEnumList(OrderDetailStatus::class.java)[0]
                    )
                    orderViewModel.updateOrderDetailState(orderDetail)
                    orderViewModel.saveDetails()
                    onButtonClicked()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.intro2),
                        contentDescription = currentMenu.itemName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                    )

                    IconButton(
                        onClick = onButtonClicked,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close button",
                            modifier = Modifier
                                .background(Color.LightGray, CircleShape)
                                .size(30.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp)
                        ) {
                            Text(
                                text = currentMenu.itemName,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "RM " + formattedString(currentMenu.price),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = currentMenu.description,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Column {
                        Row(
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )
                                    ) {
                                        if (currentMenu.cuisine == "Beverage") {
                                            append("Temperature")
                                        } else {
                                            append("Size")
                                        }
                                    }
                                    append(" (Choose 1)")
                                },
                            )
                        }

                        if (currentMenu.cuisine == "Beverage") {
                            temperatureOptions.forEachIndexed { i, option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            onClick = {
                                                optionSelected = (i + 1)
                                                total = orderViewModel.calculateDetailsTotal(
                                                    currentMenu, quantity, optionSelected
                                                )
                                            }
                                        )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        if (optionSelected == (i + 1)) {
                                            RadioButton(
                                                selected = true,
                                                onClick = { optionSelected = (i + 1) },
                                                modifier = Modifier
                                            )
                                        } else {
                                            RadioButton(
                                                selected = false,
                                                onClick = {
                                                    optionSelected = (i + 1)
                                                    total = orderViewModel.calculateDetailsTotal(
                                                        currentMenu, quantity, optionSelected
                                                    )
                                                },
                                                modifier = Modifier
                                            )
                                        }

                                        Text(text = option.first)
                                    }
                                    Text(text = "+ RM " + formattedString(option.second))
                                }
                            }
                        } else {
                            sizeOptions.forEachIndexed() { i, option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable(
                                            onClick = {
                                                optionSelected = (i + 1)
                                                total = orderViewModel.calculateDetailsTotal(
                                                    currentMenu, quantity, optionSelected
                                                )
                                            }
                                        )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (optionSelected == (i + 1)) {
                                            RadioButton(
                                                selected = true,
                                                onClick = { optionSelected = (i + 1) },
                                                modifier = Modifier
                                            )
                                        } else {
                                            RadioButton(
                                                selected = false,
                                                onClick = {
                                                    optionSelected = (i + 1)
                                                    total = orderViewModel.calculateDetailsTotal(
                                                        currentMenu, quantity, optionSelected
                                                    )
                                                },
                                                modifier = Modifier
                                            )
                                        }

                                        Text(text = option.first)
                                    }

                                    Row {
                                        Text(text = "+ RM " + formattedString(option.second))
                                    }
                                }
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Column {
                        Column(
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            Text(
                                text = "Quantity",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(id = R.color.primary)
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .width(56.dp)
                                        .height(45.dp),
                                    onClick = {
                                        if (quantity > 1) {
                                            quantity--
                                        }
                                        total = orderViewModel.calculateDetailsTotal(
                                            currentMenu, quantity, optionSelected
                                        )
                                    }
                                ) {
                                    Text(
                                        text = "-",
                                        fontSize = 20.sp
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .height(45.dp)
                                        .width(56.dp)
                                        .padding(
                                            horizontal = 5.dp
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(
                                                size = 8.dp,
                                            ),
                                        )
                                        .border(
                                            BorderStroke(
                                                width = 1.dp,
                                                colorResource(id = R.color.primary)
                                            ),
                                            shape = RoundedCornerShape(
                                                size = 12.dp,
                                            )
                                        )

                                ) {
                                    Text(
                                        fontSize = 20.sp,
                                        text = "$quantity",
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                    )
                                }

                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(id = R.color.primary)
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .width(56.dp)
                                        .height(45.dp),
                                    onClick = {
                                        quantity++
                                        total = orderViewModel.calculateDetailsTotal(
                                            currentMenu, quantity, optionSelected
                                        )
                                    }
                                ) {
                                    Text(
                                        text = "+",
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            Text(
                                text = "Remark",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = remark,
                                onValueChange = { remark = it },
                                placeholder = {
                                    Text(text = "Kindly enter your remark here...")
                                },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderCustomizeScreenPreview() {
//    OrderCustomizeScreen()
}