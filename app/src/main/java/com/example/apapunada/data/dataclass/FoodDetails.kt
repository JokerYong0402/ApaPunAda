package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = MenuItem::class,
        parentColumns = ["menuItemID"],
        childColumns = ["menuItemID"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class FoodDetails(
    @PrimaryKey(autoGenerate = true)
    val foodDetailsID: Int = 0,
    val menuItemID: Int = 0,
    val servingSize: Double = 0.0,
    val ingredient: String = ""
)
