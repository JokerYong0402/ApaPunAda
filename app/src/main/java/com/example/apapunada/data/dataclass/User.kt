package com.example.apapunada.data.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userID: Int = 0,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNo: String = "",
    val gender: String = "",
    val dob: Long = 0,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray = ByteArray(10),
    val role: String = "",
    val point: Int = 0,
    val status: String = ""
)