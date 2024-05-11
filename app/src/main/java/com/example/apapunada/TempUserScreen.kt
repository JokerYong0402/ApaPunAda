package com.example.apapunada

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.example.apapunada.viewmodel.OrderListState
import com.example.apapunada.viewmodel.OrderUiState
import com.example.apapunada.viewmodel.OrderViewModel

@Composable
fun TempUserScreen(
    orderViewModel: OrderViewModel = viewModel()
) {
    val orderUiState = orderViewModel.orderUiState.collectAsState(initial = OrderUiState())
    val orderListState = orderViewModel.orderListState.collectAsState(initial = OrderListState())
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "HI")
    }
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(orderListState.value.orderList.size) {
//            Text(text = "Hi")
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun TempUserScreenPreview() {
    TempUserScreen()
}