package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuItem(
    @PrimaryKey(autoGenerate = true)
    val menuItemID: Int = 0,
    val itemName: String = "",
    val cuisine: String = "", //enum
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val image: String = "",
    val description: String = "",
    val status: String = "",
)
