package com.example.apapunada.data.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Voucher(
    @PrimaryKey(autoGenerate = true)
    val voucherID: Int = 0,
    val image: String = "",
    val description: String = "",
    val code: String = "",
    val value: Double = 0.0,
    val expiryDateTime: Long = 0,
    val status: String = ""
)
