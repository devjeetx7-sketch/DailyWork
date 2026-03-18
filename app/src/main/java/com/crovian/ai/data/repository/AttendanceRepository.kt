package com.crovian.ai.data.repository

import com.crovian.ai.data.model.AttendanceRecord
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Handles all database operations related to [AttendanceRecord] via Firebase Firestore.
 */
class AttendanceRepository {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Retrieves all attendance records for a given user within a specified month.
     *
     * @param userId The ID of the user.
     * @param month The month to filter by (Format: "YYYY-MM").
     * @return List of matching [AttendanceRecord]s.
     */
    suspend fun getMonthlyAttendance(userId: String, month: String): List<AttendanceRecord> {
        val snapshot = db.collection("attendance")
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", "$month-01")
            .whereLessThanOrEqualTo("date", "$month-31")
            .get()
            .await()
        return snapshot.toObjects(AttendanceRecord::class.java)
    }

    /**
     * Retrieves the attendance record for the current day if it exists.
     *
     * @param userId The ID of the user.
     * @param today The current date (Format: "YYYY-MM-DD").
     */
    suspend fun getTodayAttendance(userId: String, today: String): AttendanceRecord? {
        val snapshot = db.collection("attendance")
            .whereEqualTo("userId", userId)
            .whereEqualTo("date", today)
            .get()
            .await()
        return snapshot.documents.firstOrNull()?.toObject(AttendanceRecord::class.java)
    }

    /**
     * Saves or updates an [AttendanceRecord] in the database.
     */
    suspend fun markAttendance(record: AttendanceRecord) {
        db.collection("attendance")
            .document(record.id)
            .set(record)
            .await()
    }
}
