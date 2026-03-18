package com.crovian.ai.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crovian.ai.data.model.AdvancePayment
import com.crovian.ai.data.model.AttendanceRecord
import com.crovian.ai.data.repository.AdvanceRepository
import com.crovian.ai.data.repository.AttendanceRepository
import com.crovian.ai.data.repository.AuthRepository
import com.crovian.ai.utils.DateUtils.getCurrentMonthString
import com.crovian.ai.utils.DateUtils.getTodayString
import kotlinx.coroutines.launch

/**
 * Summary of a user's attendance and financials for a specific month.
 */
data class MonthlySummary(
    val totalDaysWorked: Double = 0.0,
    val fullDays: Int = 0,
    val halfDays: Int = 0,
    val absentDays: Int = 0,
    val grossEarnings: Double = 0.0,
    val totalAdvances: Double = 0.0,
    val netPayable: Double = 0.0,
    val dailyWage: Double = 0.0
)

/**
 * ViewModel responsible for managing state and business logic on the Dashboard screen.
 */
class DashboardViewModel : ViewModel() {

    private val authRepo = AuthRepository()
    private val attendanceRepo = AttendanceRepository()
    private val advanceRepo = AdvanceRepository()

    private val _monthlySummary = MutableLiveData<MonthlySummary>()
    val monthlySummary: LiveData<MonthlySummary> = _monthlySummary

    private val _todayAttendance = MutableLiveData<AttendanceRecord?>()
    val todayAttendance: LiveData<AttendanceRecord?> = _todayAttendance

    private val _recentAdvances = MutableLiveData<List<AdvancePayment>>()
    val recentAdvances: LiveData<List<AdvancePayment>> = _recentAdvances

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Loads all necessary dashboard data for the current user, including
     * monthly summaries, today's attendance, and recent advance payments.
     */
    fun loadDashboardData() {
        val userId = authRepo.getCurrentUser()?.uid ?: return
        val currentMonth = getCurrentMonthString() // "YYYY-MM"
        val today = getTodayString()               // "YYYY-MM-DD"

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val records = attendanceRepo.getMonthlyAttendance(userId, currentMonth)
                val advances = advanceRepo.getMonthlyAdvances(userId, currentMonth)

                calculateMonthlySummary(records, advances)

                _todayAttendance.value = attendanceRepo.getTodayAttendance(userId, today)
                _recentAdvances.value = advanceRepo.getRecentAdvances(userId)

            } catch (e: Exception) {
                // handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun calculateMonthlySummary(records: List<AttendanceRecord>, advances: List<AdvancePayment>) {
        val fullDays = records.count { it.status == "full_day" }
        val halfDays = records.count { it.status == "half_day" }
        val absentDays = records.count { it.status == "absent" }
        val totalDaysWorked = fullDays + (halfDays * 0.5)

        // Let's assume daily wage is 0 for now until user profile is loaded
        val dailyWage = 0.0
        val grossEarnings = totalDaysWorked * dailyWage
        val totalAdvances = advances.sumOf { it.amount }
        val netPayable = grossEarnings - totalAdvances

        _monthlySummary.value = MonthlySummary(
            totalDaysWorked = totalDaysWorked,
            fullDays = fullDays,
            halfDays = halfDays,
            absentDays = absentDays,
            grossEarnings = grossEarnings,
            totalAdvances = totalAdvances,
            netPayable = netPayable,
            dailyWage = dailyWage
        )
    }

    /**
     * Records or updates the user's attendance for today.
     */
    fun markAttendance(status: String, overtimeHours: Double, note: String) {
        val userId = authRepo.getCurrentUser()?.uid ?: return
        val today = getTodayString()

        val record = AttendanceRecord(
            id = "${userId}_${today}",
            userId = userId,
            date = today,
            status = status,
            overtimeHours = overtimeHours,
            note = note
        )

        viewModelScope.launch {
            try {
                attendanceRepo.markAttendance(record)
                _todayAttendance.value = record
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    /**
     * Registers a new advance payment requested by the user.
     */
    fun addAdvancePayment(date: String, amount: Double, note: String) {
        val userId = authRepo.getCurrentUser()?.uid ?: return

        val advance = AdvancePayment(
            id = "${userId}_advance_${date}_${System.currentTimeMillis()}",
            userId = userId,
            date = date,
            amount = amount,
            note = note
        )

        viewModelScope.launch {
            try {
                advanceRepo.addAdvancePayment(advance)
                loadDashboardData() // Refresh
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}
