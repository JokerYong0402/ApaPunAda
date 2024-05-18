package com.example.apapunada.data.dataclass

import androidx.room.ColumnInfo
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
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val images: ByteArray = ByteArray(10),
    val comments: String = ""
)