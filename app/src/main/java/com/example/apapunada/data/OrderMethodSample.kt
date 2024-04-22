package com.example.apapunada.data

import com.example.apapunada.R
import com.example.apapunada.model.OrderOption

object OrderMethodSample {
    val OrderMethod = listOf(
        OrderOption(
            1,
            "Dine-in",
            R.drawable.ordermethod1,
            0.0
        ),
        OrderOption(
            2,
            "Pick up",
            R.drawable.ordermethod2,
            1.0
        ),
        OrderOption(
            3,
            "Delivery",
            R.drawable.ordermethod3,
            2.5
        ),
    )
}