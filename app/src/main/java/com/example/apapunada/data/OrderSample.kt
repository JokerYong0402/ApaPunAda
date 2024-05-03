package com.example.apapunada.data

import com.example.apapunada.data.OrderDetailsSample.OrderDetails
import com.example.apapunada.data.OrderMethodSample.OrderMethod
import com.example.apapunada.data.OrderStatusSample.OrderStatus
import com.example.apapunada.model.Order

object OrderSample {
    val Orders = listOf(
        Order(
            1,
            1,
            "user1",
            OrderMethod[0],
            listOf(
                OrderDetails[0]
            ),
            0.0,
            "10/04/202 12:00:00",
            false,
            OrderStatus[0]
        ),
        Order(
            2,
            2,
            "user1",
            OrderMethod[0],
            listOf(
                OrderDetails[0]
            ),
            0.0,
            "10/04/202 12:00:00",
            false,
            OrderStatus[0]
        )
    )
}