package com.crovian.ai.data.repository

import com.crovian.ai.data.model.AttendanceRecord
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AttendanceRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getMonthlyAttendance(userId: String, month: String): List<AttendanceRecord> {
        val snapshot = db.collection("attendance")
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", "$month-01")
            .whereLessThanOrEqualTo("date", "$month-31")
            .get()
            .await()
        return snapshot.toObjects(AttendanceRecord::class.java)
    }

    suspend fun getTodayAttendance(userId: String, today: String): AttendanceRecord? {
        val snapshot = db.collection("attendance")
            .whereEqualTo("userId", userId)
            .whereEqualTo("date", today)
            .get()
            .await()
        return snapshot.documents.firstOrNull()?.toObject(AttendanceRecord::class.java)
    }

    suspend fun markAttendance(record: AttendanceRecord) {
        db.collection("attendance")
            .document(record.id)
            .set(record)
            .await()
    }
}
