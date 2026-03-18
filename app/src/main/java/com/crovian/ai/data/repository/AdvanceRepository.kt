package com.crovian.ai.data.repository

import com.crovian.ai.data.model.AdvancePayment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class AdvanceRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getMonthlyAdvances(userId: String, month: String): List<AdvancePayment> {
        val snapshot = db.collection("advances")
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", "$month-01")
            .whereLessThanOrEqualTo("date", "$month-31")
            .get()
            .await()
        return snapshot.toObjects(AdvancePayment::class.java)
    }

    suspend fun getRecentAdvances(userId: String): List<AdvancePayment> {
        val snapshot = db.collection("advances")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(5)
            .get()
            .await()
        return snapshot.toObjects(AdvancePayment::class.java)
    }

    suspend fun addAdvancePayment(advance: AdvancePayment) {
        db.collection("advances")
            .document(advance.id)
            .set(advance)
            .await()
    }
}
