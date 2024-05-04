package com.example.apapunada.data

import com.example.apapunada.model.OrderDetails

object OrderDetailsSample {
    val OrderDetails = listOf(
        OrderDetails(
            1,
            MenuSample.Menus[0],
            2,
            "No vegetables",
            20.0
        ),
        OrderDetails(
            2,
            MenuSample.Menus[3],
            1,
            "",
            4.0
        ),
        OrderDetails(
            3,
            MenuSample.Menus[5],
            5,
            "",
            10.0
        )
    )
}