package com.example.apapunada.data

import com.example.apapunada.R
import com.example.apapunada.data.FoodCuisinesSample.FoodCuisines
import com.example.apapunada.model.Menu

object MenuSample {
    val Menus = listOf(
        Menu(
            1,
            "Beef Burger",
            FoodCuisines[0],
            12.40,
            4.9,
            R.drawable.feedback1,
            "Burger with beef meat",
            "Available",
            "100g ground beef\n1 sesame bun,split toasted & buttered\n1 sliced tomatoes\n1 lettuce leaf\n1 sliced cheddar cheese\n1 teaspoon ketchup",
            ""
        ),
        Menu(
            2,
            "Steak",
            FoodCuisines[0],
            15.00,
            4.9,
            R.drawable.ordermethod2,
            "Grilled Beef Tenderlion",
            "Available",
            "",
            ""
        ),
        Menu(
            3,
            "Kimchi Stew",
            FoodCuisines[2],
            17.00,
            4.9,
            R.drawable.ordermethod3,
            "Burger with beef meat",
            "Available",
            "",
            ""
        ),
        Menu(
            4,
            "Tom Yum Soup",
            FoodCuisines[4],
            4.00,
            4.9,
            R.drawable.profile_image,
            "Burger with beef meat",
            "Available",
            "",
            ""
        ),
        Menu(
            5,
            "Salmon Sahshimi",
            FoodCuisines[1],
            2.00,
            4.9,
            R.drawable.intro2,
            "Burger with beef meat",
            "Available",
            "",
            ""
        ),
        Menu(
            6,
            "Japanese Curry Rice",
            FoodCuisines[1],
            2.00,
            4.9,
            R.drawable.more,
            "Burger with beef meat",
            "Available",
            "",
            ""
        )
    )
}