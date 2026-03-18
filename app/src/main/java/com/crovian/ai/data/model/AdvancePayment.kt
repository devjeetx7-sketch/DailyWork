package com.crovian.ai.data.model

data class AdvancePayment(
    val id: String = "",
    val userId: String = "",
    val date: String = "",           // Format: "YYYY-MM-DD"
    val amount: Double = 0.0,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
