package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuItem(
    @PrimaryKey(autoGenerate = true)
    val menuItemID: Int,
    val itemName: String = "",
    val cuisine: String, //enum
    val price: Double,
    val rating: Double,
    val image: Int,
    val description: String,
    val status: String
)
