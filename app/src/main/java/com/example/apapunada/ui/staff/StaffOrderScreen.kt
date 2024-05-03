package com.example.apapunada.ui.staff

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.data.OrderSample.Orders
import com.example.apapunada.model.Order

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffOrderScreen(
    order: List<Order> = Orders
) {

    val headerList = listOf(
        "No.",
        "Order Number",
        "Name",
        "Amount",
        "Date",
        "Time",
        "Status",
        "Status",
        "Status",
        "Status",
        "Status",
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // header row
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    headerList.forEach() { header ->
                        Text(
                            text = header,
                            fontSize = 30.sp,
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = TABLET)
@Composable
fun StaffOrderScreenPreview() {
    StaffOrderScreen()
}