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
    val feedbackID: Int = 0,
    val userID: Int = 0,
    val star: Int = 5,
    val category: String = "",
    val images: String = "",
//    val images: List<ByteArray> = listOf(ByteArray(10)), TODO
    val comments: String = ""
)