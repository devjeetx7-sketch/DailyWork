package com.crovian.ai.data.model

/**
 * Represents a single daily attendance record for a user.
 *
 * @property id Unique identifier, typically a combination of userId and date.
 * @property userId The Firebase UID of the employee.
 * @property date The date of attendance, formatted as "YYYY-MM-DD".
 * @property status The status of the day. Expected values: "full_day", "half_day", "absent".
 * @property overtimeHours Any extra hours worked on this specific day.
 * @property note Optional notes regarding the day's attendance.
 * @property timestamp Epoch timestamp of when the record was created/modified.
 */
data class AttendanceRecord(
    val id: String = "",
    val userId: String = "",
    val date: String = "",
    val status: String = "",
    val overtimeHours: Double = 0.0,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
