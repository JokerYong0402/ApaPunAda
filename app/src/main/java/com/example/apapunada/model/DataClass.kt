package com.example.apapunada.model

data class MenuItem(
    val name: String,
    val rating: Double,
    val image: Int
) //temp

data class User(
    val id: Int,
    val userId: Int,
    val username: String,
    val email: String,
    val password: String,
    val phoneNo: String,
    val gender: String,
    val dob: String,
    val image: Int,
    val point: Int,
    val status: String
)

data class Menu(
    val id: Int,
    val name: String,
    val cuisine: Int, //fk
    val price: Double,
    val rating: Double,
    val image: Int,
    val description: String
)
//serving?

data class Cuisine(
    val id: Int,
    val cuisineName: String
)

data class FoodDetails(
    val id: Int,
    val menuId: Int, //fk
    val ingredient: String,
    val nutritionFact: String
)

data class WaitList(
    val id: Int,
    val username: String, // fk
    val size: Int,
    val dateTime: String,
    val waitTime: Int,
    val status: String
)

data class Order(
    val id: Int,
    val orderId: Int,
    val username: String, //fk
    val orderDetails: String, //JSON {menuId, qty}
    val amount: Double,
    val dateTime: String,
    val paymentStatus: Boolean,
    val statusId: Int //fk
)

data class OrderStatus(
    val id: Int,
    val statusId: Int,
    val status: String
)
