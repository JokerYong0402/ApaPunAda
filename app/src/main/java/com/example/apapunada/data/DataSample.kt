package com.example.apapunada.data

import com.example.apapunada.R
import com.example.apapunada.model.Cuisine
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
}