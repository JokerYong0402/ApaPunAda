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
data class Waitlist(
    @PrimaryKey(autoGenerate = true)
    val waitlistID: Int = 0,
    val userID: Int = 0,
    val size: Int = 1,
    val datetime: Long = System.currentTimeMillis(),
    val status: String = "" // enum
)
