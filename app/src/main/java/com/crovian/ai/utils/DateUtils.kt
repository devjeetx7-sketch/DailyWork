package com.crovian.ai.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getCurrentMonthString(): String {
        val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getTodayString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}
