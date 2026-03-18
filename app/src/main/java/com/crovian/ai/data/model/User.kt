package com.crovian.ai.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val dailyWage: Double = 0.0,
    val language: String = "en",
    val createdAt: Long = System.currentTimeMillis()
)
