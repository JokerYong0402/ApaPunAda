package com.example.apapunada.model

data class MenuItem(
    val name: String,
    val rating: Double,
    val image: Int
)

data class PopularDishes(
    val name: String,
    val popular: Int,
    val image: Int,
    val category: String,
    val rating: Double
)