package com.example.apapunada.data

import com.example.apapunada.R
import com.example.apapunada.model.User

object UserSample {
    val Users = listOf(
        User(
            1,
            1,
            "User One",
            "user@gmail.com",
            "user123",
            "012-2365269",
            "Male",
            "01-01-2001",
            R.drawable.profile_image,
            1,
            "Active"
        ),
        User(
            2,
            2,
            "User Two",
            "user2@gmail.com",
            "user321",
            "019-8765432",
            "Female",
            "21-04-2003",
            R.drawable.feedback1,
            123,
            "Disabled"
        )
    )
}