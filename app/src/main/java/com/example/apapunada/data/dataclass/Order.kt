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
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderID: Int,
    val userID: Int, //fk
    val method: String = "",
    val amount: Double = 0.0,
    val dateTime: Long = System.currentTimeMillis(),
    val paymentStatus: String = "",
    val orderStatus: String = ""
)