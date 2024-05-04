package com.example.apapunada.data

import com.example.apapunada.R
import com.example.apapunada.data.FoodCuisinesSample.FoodCuisines
import com.example.apapunada.model.Menu

object MenuSample {
    val Menus = listOf(
        Menu(
            1,
            "Beef Burger",
            FoodCuisines[1],
            10.00,
            4.9,
            R.drawable.feedback1,
            "Burger with beef meat",
            "Active",
            "",
            ""
        ),
        Menu(
            2,
            "Beef Burger",
            FoodCuisines[1],
            15.00,
            4.9,
            R.drawable.ordermethod2,
            "Burger with beef meat",
            "Active",
            "",
            ""
        ),
        Menu(
            3,
            "Beef Burger",
            FoodCuisines[3],
            17.00,
            4.9,
            R.drawable.ordermethod3,
            "Burger with beef meat",
            "Active",
            "",
            ""
        ),
        Menu(
            4,
            "Beef Burger",
            FoodCuisines[5],
            4.00,
            4.9,
            R.drawable.profile_image,
            "Burger with beef meat",
            "Active",
            "",
            ""
        ),
        Menu(
            5,
            "Beef Burger",
            FoodCuisines[2],
            2.00,
            4.9,
            R.drawable.intro2,
            "Burger with beef meat",
            "Active",
            "",
            ""
        ),
        Menu(
            6,
            "Beef Burger",
            FoodCuisines[2],
            2.00,
            4.9,
            R.drawable.more,
            "Burger with beef meat",
            "Active",
            "",
            ""
        )
    )
}