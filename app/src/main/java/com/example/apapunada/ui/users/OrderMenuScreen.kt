package com.example.apapunada.ui.users

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
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.apapunada.R
import com.example.apapunada.data.FoodCuisinesSample.FoodCuisines
import com.example.apapunada.data.MenuSample.Menus
import com.example.apapunada.data.OrderSample.Orders
import com.example.apapunada.model.Cuisine
import com.example.apapunada.model.Menu
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedString
import kotlinx.coroutines.launch

@Composable
fun OrderMenuScreen(
    tabs: List<Cuisine> = FoodCuisines,
    orderMenu: List<Menu> = Menus,
    orderId: Int = 0,
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val primaryColor = R.color.primary
    val currentOrder = Orders[orderId]

    Scaffold(
        topBar = { MyTopTitleBar(title = "Order") },
        bottomBar = { MyBottomButton(content = "Checkout", currentOrder) }
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
                                text = { Text(text = tab.cuisineName) }
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
                            text = tab.cuisineName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        orderMenu.forEach { menu ->
                            if (menu.cuisine.cuisineName == tab.cuisineName
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
                                                painter = painterResource(menu.image),
                                                contentDescription = menu.name,
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
                                                text = menu.name,
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
                                                onClick = { /*TODO*/
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
    OrderMenuScreen()
}