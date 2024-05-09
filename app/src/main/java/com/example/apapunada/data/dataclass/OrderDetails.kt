package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["orderID", "menuItemID"],
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
    val orderDetailsID: Int,
    val orderID: Int,
    val menuItemID: Int,
    val quantity: Int = 0,
    val remark: String = "",
    val option: String = "",
    val total: Double = 0.0
)