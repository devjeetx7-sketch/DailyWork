package com.crovian.ai.data.model

/**
 * Represents a registered employee in the system.
 *
 * @property uid The unique Firebase authentication ID.
 * @property name The employee's display name, typically from Google Sign-In.
 * @property email The employee's email address.
 * @property photoUrl Optional URL pointing to the employee's profile picture.
 * @property dailyWage The configured daily wage for calculating gross earnings.
 * @property language Preferred language of the employee (default: "en").
 * @property createdAt Epoch timestamp representing the account creation date.
 */
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val dailyWage: Double = 0.0,
    val language: String = "en",
    val createdAt: Long = System.currentTimeMillis()
)
