package com.crovian.ai.data.repository

import com.crovian.ai.data.model.AdvancePayment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

/**
 * Handles database operations for [AdvancePayment] logic.
 */
class AdvanceRepository {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Fetches all advance payment records within a specific month for a given user.
     */
    suspend fun getMonthlyAdvances(userId: String, month: String): List<AdvancePayment> {
        val snapshot = db.collection("advances")
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", "$month-01")
            .whereLessThanOrEqualTo("date", "$month-31")
            .get()
            .await()
        return snapshot.toObjects(AdvancePayment::class.java)
    }

    /**
     * Obtains the 5 most recent advance payments requested by a specific user.
     */
    suspend fun getRecentAdvances(userId: String): List<AdvancePayment> {
        val snapshot = db.collection("advances")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(5)
            .get()
            .await()
        return snapshot.toObjects(AdvancePayment::class.java)
    }

    /**
     * Creates a new [AdvancePayment] record in the Firestore database.
     */
    suspend fun addAdvancePayment(advance: AdvancePayment) {
        db.collection("advances")
            .document(advance.id)
            .set(advance)
            .await()
    }
}
