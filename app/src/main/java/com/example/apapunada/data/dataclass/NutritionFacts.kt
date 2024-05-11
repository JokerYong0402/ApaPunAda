package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = FoodDetails::class,
        parentColumns = ["foodDetailsID"],
        childColumns = ["foodDetailsID"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class NutritionFacts(
    @PrimaryKey(autoGenerate = true)
    val nutritionFactsID: Int = 0,
    val foodDetailsID: Int = 0,
    val carbohydrates: Double = 0.0,
    val proteins: Double = 0.0,
    val fats: Double = 0.0,
    val salt: Double = 0.0,
    val sugar: Double = 0.0
)
