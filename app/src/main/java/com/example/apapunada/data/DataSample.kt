package com.example.apapunada.data

import com.example.apapunada.R
import com.example.apapunada.model.MenuItem
import com.example.apapunada.model.PopularDishes

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
}

object DataOfPopularDishes {
    val PopularMenu = listOf(
        PopularDishes("Beef Burger", 12232, R.drawable.beefburgerpic, "Western", 4.9),
        PopularDishes("Prawn Mee", 10565, R.drawable.prawnmeepic, "Malaysian", 4.5),
        PopularDishes("Fish & Chips",11264,R.drawable.fish_chipspic, "Western", 4.0),
        PopularDishes("Cabonara Pasta",9076,R.drawable.cabonarapastapic, "Western", 3.9),
        PopularDishes("Steak",15457,R.drawable.steakpic, "Western", 4.8),
        PopularDishes("Miso Ramen",21438,R.drawable.ramenpic, "Japanese", 3.8),
        PopularDishes("Salmon Sashimi",35457,R.drawable.salmonsashimipic, "Japanese", 4.7),
        PopularDishes("Japanese Curry Rice",8479,R.drawable.japanesecurrypic, "Japanese", 3.5),
        PopularDishes("Tom Yum Soup",18079,R.drawable.tomyumsouppic, "Thai", 4.4),
        PopularDishes("Pad Kra Pao",13112,R.drawable.padkrapaopic, "Thai", 4.2),
        )
}