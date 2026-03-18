package com.crovian.ai.data.model

/**
 * Represents an advance payment requested by an employee.
 *
 * @property id Unique identifier for the transaction.
 * @property userId The Firebase UID of the employee.
 * @property date The date the advance was processed, formatted as "YYYY-MM-DD".
 * @property amount The financial value of the advance payment.
 * @property note Optional notes explaining the advance request.
 * @property timestamp Epoch timestamp of when the payment was recorded.
 */
data class AdvancePayment(
    val id: String = "",
    val userId: String = "",
    val date: String = "",
    val amount: Double = 0.0,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
