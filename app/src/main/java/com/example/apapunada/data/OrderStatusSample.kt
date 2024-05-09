package com.example.apapunada.data

import com.example.apapunada.data.dataclass.OrderStatus

object OrderStatusSample {
    val OrderStatus = listOf(
        OrderStatus(
            1,
            1,
            "New"
        ),
        OrderStatus(
            2,
            2,
            "Preparing"
        ),
        OrderStatus(
            3,
            3,
            "Ready"
        ),
        OrderStatus(
            4,
            4,
            "Completed"
        ),
        OrderStatus(
            5,
            5,
            "Cancelled"
        ),
    )
}