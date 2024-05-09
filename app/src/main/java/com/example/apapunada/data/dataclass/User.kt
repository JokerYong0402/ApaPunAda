package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userID: Int,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNo: String = "",
    val gender: String = "",
    val dob: Long = 0,
    val image: Int = 0,
    val point: Int = 0,
    val status: String = "Active" //enum
)