package com.crovian.ai.data.model

data class AttendanceRecord(
    val id: String = "",
    val userId: String = "",
    val date: String = "",           // Format: "YYYY-MM-DD"
    val status: String = "",         // "full_day", "half_day", "absent"
    val overtimeHours: Double = 0.0,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
