package com.crovian.ai.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crovian.ai.data.model.AttendanceRecord
import com.crovian.ai.data.repository.AttendanceRepository
import com.crovian.ai.data.repository.AuthRepository
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {
    private val authRepo = AuthRepository()
    private val attendanceRepo = AttendanceRepository()

    private val _records = MutableLiveData<List<AttendanceRecord>>()
    val records: LiveData<List<AttendanceRecord>> = _records

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadMonthlyData(month: String) {
        val userId = authRepo.getCurrentUser()?.uid ?: return

        _isLoading.value = true

        viewModelScope.launch {
            try {
                _records.value = attendanceRepo.getMonthlyAttendance(userId, month)
            } catch (e: Exception) {
                // handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
