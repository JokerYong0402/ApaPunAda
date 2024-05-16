package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["orderID"],
            childColumns = ["orderID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MenuItem::class,
            parentColumns = ["menuItemID"],
            childColumns = ["menuItemID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ]
)
data class OrderDetails(
    @PrimaryKey(autoGenerate = true)
    val orderDetailsID: Int = 0,
    val orderID: Int = 0,
    val menuItemID: Int = 0,
    val quantity: Int = 0,
    val remark: String = "",
    val option: String = "",
    val total: Double = 0.0,
//    val status: String = ""
)