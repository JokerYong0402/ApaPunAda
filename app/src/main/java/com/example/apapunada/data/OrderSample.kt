package com.example.apapunada.data

import com.example.apapunada.data.OrderDetailsSample.OrderDetails
import com.example.apapunada.data.OrderMethodSample.OrderMethod
import com.example.apapunada.data.OrderStatusSample.OrderStatus
import com.example.apapunada.data.UserSample.Users
import com.example.apapunada.data.dataclass.Order

object OrderSample {
    val Orders = listOf(
        Order(
            1,
            "S00001",
            Users[0],
            OrderMethod[0],
            listOf(
                OrderDetails[0],
                OrderDetails[1],
                OrderDetails[1],
                OrderDetails[1],
                OrderDetails[1],
                OrderDetails[1],
                OrderDetails[2],
            ),
            0.0,
            "Apr 01, 2024",
            "3:52 PM",
            false,
            OrderStatus[3]
        ),
        Order(
            2,
            "S00002",
            Users[0],
            OrderMethod[0],
            listOf(
                OrderDetails[2]
            ),
            0.0,
            "Apr 01, 2024",
            "3:52 PM",
            false,
            OrderStatus[0]
        ),
        Order(
            3,
            "S00003",
            Users[0],
            OrderMethod[0],
            listOf(
                OrderDetails[2]
            ),
            0.0,
            "Apr 01, 2024",
            "3:52 PM",
            false,
            OrderStatus[0]
        )
    )
}