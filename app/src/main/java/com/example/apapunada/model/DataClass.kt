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
    val cuisine: Cuisine, //fk
    val price: Double,
    val rating: Double,
    val image: Int,
    val description: String,
    val status: String
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
    val orderMethod: OrderOption,
    val orderDetails: List<OrderDetails>,
    val amount: Double = 0.0,
    val dateTime: String,
    val paymentStatus: Boolean,
    val statusId: OrderStatus //fk
)

data class OrderOption(
    val id: Int,
    val orderMethod: String,
    val methodImg: Int,
    val addPrice: Double
)

data class OrderDetails(
    val id: Int,
    val menuId: Menu,
    val qty: Int,
//    val size: SizeOption,
    val remark: String,
    val total: Double = 0.0,
)

data class OrderStatus(
    val id: Int,
    val statusId: Int,
    val status: String
)

data class SizeOption(
    val id: Int,
    val sizeName: String,
    val addPrice: Double
)

data class TemperatureOption(
    val id: Int,
    val temperatureName: String,
    val addPrice: Double
)

data class Voucher(
    val id: Int,
    val voucherName: String,
    val value: Double
)

data class Feedback(
    val id: Int,
    val username: String,
    val star: Int,
    val category: String,
    val images: List<Int>,
    val comments: String
)