package com.example.apapunada.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apapunada.ui.components.MyBottomButton
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun OrderOptionScreen() {
    Scaffold(
        topBar = { MyTopTitleBar(title = "Order") },
        bottomBar = { MyBottomButton(content = "Next") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderOptionScreenPreview() {
    OrderOptionScreen()
}