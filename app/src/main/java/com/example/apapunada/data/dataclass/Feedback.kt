package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userID"],
        childColumns = ["userID"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Feedback(
    @PrimaryKey(autoGenerate = true)
    val feedbackID: Int,
    val userID: Int,
    val star: Int = 5,
    val category: String,
    val images: String = "",
    val comments: String = ""
)