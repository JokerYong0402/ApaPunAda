package com.example.apapunada.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apapunada.data.DataSample.FoodCuisines
import com.example.apapunada.data.DataSample.OrderMenu
import com.example.apapunada.model.Cuisine
import com.example.apapunada.model.Menu
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun OrderMenuScreen(
    tabs: List<Cuisine> = FoodCuisines,
    orderMenu: List<Menu> = OrderMenu,
) {
    Scaffold(
        topBar = { MyTopTitleBar(title = "Order") },
        bottomBar = { MyBottomButton(content = "Checkout", order = true) }
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
                        tabs.forEach() { tab ->
                            Tab(
                                selected = true,
                                onClick = {  },
                                text = { Text(text = tab.cuisineName) }
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
//
//                    tabs.forEachIndexed { tabIndex, tab ->
//                        Text(text = tab.cuisineName)
//                    }
//                    for (i in 1..3) {
//                        Card(
//                            modifier = Modifier
//                                .height(300.dp)
//                                .fillMaxWidth()
//                                .padding(20.dp)
//                        ) {
//                            Text(text = "H")
//                        }
//                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun OrderMenuScreenPreview() {
    OrderMenuScreen()
}