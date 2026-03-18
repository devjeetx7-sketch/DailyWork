package com.crovian.ai.viewmodel

import androidx.lifecycle.ViewModel
import com.crovian.ai.data.repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val authRepo = AuthRepository()

    fun isUserLoggedIn(): Boolean {
        return authRepo.getCurrentUser() != null
    }

    fun signOut() {
        authRepo.signOut()
    }
}
