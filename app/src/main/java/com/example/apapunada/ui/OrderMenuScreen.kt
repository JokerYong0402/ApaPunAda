package com.example.apapunada.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun OrderMenuScreen(

) {
    val tabs = listOf("Western", "Korean", "Malaysian", "Japanese", "Thai", "Beverages")

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
                ScrollableTabRow(
                    selectedTabIndex = 1,
                ) {
                    Text(text = "HI")
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