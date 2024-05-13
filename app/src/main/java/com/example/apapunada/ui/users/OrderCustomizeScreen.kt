package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.apapunada.R
import com.example.apapunada.data.MenuSample.Menus
import com.example.apapunada.data.OrderSample.Orders
import com.example.apapunada.data.SizeOptionSample.sizeOptions
import com.example.apapunada.data.TemperatureOptionSample.temperatureOptions
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.formattedString

@Composable
fun OrderCustomizeScreen(
    orderId: Int,
    menuId: Int,
) {
    val currentOrder = Orders[orderId]
    val currentMenu = Menus[menuId]
    var price = remember { mutableStateOf(currentOrder.amount) }

    Scaffold(
        bottomBar = { MyBottomButton(content = "Add to Cart", price = price.value) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp)
                ) {
                    Image(
                        painter = painterResource(currentMenu.image),
                        contentDescription = currentMenu.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton(
                        onClick = { /*TODO*/ },
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
                                text = currentMenu.name,
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
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = currentMenu.description,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 5
                            )
                        }
                    }

                    Divider( modifier = Modifier.padding(vertical = 12.dp) )

                    Column {
                        Row(
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                ) {
                                    if (currentMenu.cuisine.cuisineName == "Beverages") {
                                        append("Temperature")
                                    } else {
                                        append("Size")
                                    }
                                }
                                append(" (Choose 1)")
                                },
                            )
                        }

                        if (currentMenu.cuisine.cuisineName == "Beverages") {
                            temperatureOptions.forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = false,
                                            onClick = { /*TODO*/ },
                                            modifier = Modifier
                                        )

                                        Text(text = option.temperatureName)
                                    }
                                    Text(text = "RM " + formattedString(option.addPrice))
                                }
                            }
                        } else {
                            sizeOptions.forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = false,
                                            onClick = { /*TODO*/ },
                                            modifier = Modifier
                                                .size(30.dp)
                                        )

                                        Text(text = option.sizeName)
                                    }

                                    Row {
                                        Text(text = "RM " + formattedString(option.addPrice))
                                    }
                                }
                            }
                        }
                    }

                    Divider( modifier = Modifier.padding(vertical = 12.dp) )

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
                            EditTextField(
                                value = "",
                                onValueChange = {},
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Divider( modifier = Modifier.padding(vertical = 12.dp) )

                    Column {
                        // qty
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderCustomizeScreenPreview() {
    OrderCustomizeScreen(0, 1)
}