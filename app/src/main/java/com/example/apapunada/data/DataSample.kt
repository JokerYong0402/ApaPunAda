package com.example.apapunada.data

import com.example.apapunada.R
import com.example.apapunada.model.Cuisine
import com.example.apapunada.model.Menu
import com.example.apapunada.model.MenuItem

object DataSample {
    val FoodMenu = listOf(
        MenuItem("Beef Burger", 4.9, R.drawable.feedback1),
        MenuItem("Low Rated 1", 1.4, R.drawable.intro3),
        MenuItem("Nigga Steak", 4.8, R.drawable.profile_image),
        MenuItem("Prawn Noodle", 4.6, R.drawable.intro3),
        MenuItem("Chicken Chop", 5.0, R.drawable.feedback2),
        MenuItem("Low Rated 2", 2.6, R.drawable.intro3),
        MenuItem("Carbonara Pasta", 4.7, R.drawable.emailicon),
    )

    val FoodCuisines = listOf(
        Cuisine(1, "Western"),
        Cuisine(2, "Japanese"),
        Cuisine(3, "Korean"),
        Cuisine(4, "Malaysian"),
        Cuisine(5, "Thai"),
        Cuisine(6, "Beverages"),
    )

    val OrderMenu = listOf(
        Menu(1, "Beef Burger", FoodCuisines[1], 10.00, 4.9, R.drawable.feedback1, "Burger with beef meat"),
        Menu(2, "Beef Burger", FoodCuisines[1], 15.00, 4.9, R.drawable.feedback2, "Burger with beef meat"),
        Menu(3, "Beef Burger", FoodCuisines[3], 17.00, 4.9, R.drawable.feedback1, "Burger with beef meat"),
        Menu(4, "Beef Burger", FoodCuisines[5], 4.00, 4.9, R.drawable.feedback2, "Burger with beef meat"),
        Menu(5, "Beef Burger", FoodCuisines[6], 2.00, 4.9, R.drawable.feedback1, "Burger with beef meat"),
    )
}