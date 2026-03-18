package com.crovian.ai.utils

import android.content.Context
import com.crovian.ai.data.model.AdvancePayment
import com.crovian.ai.data.model.AttendanceRecord
import java.io.File

object CsvExporter {

    fun generateCsv(
        context: Context,
        userName: String,
        month: String,
        records: List<AttendanceRecord>,
        advances: List<AdvancePayment>
    ): File {
        val fileName = "Attendance_${userName}_${month}.csv"
        val file = File(context.getExternalFilesDir(null), fileName)

        val sb = StringBuilder()
        sb.appendLine("Date,Status,Overtime Hours,Note,Advance Amount")

        val allDates = (records.map { it.date } + advances.map { it.date }).distinct().sorted()

        allDates.forEach { date ->
            val record = records.find { it.date == date }
            val advance = advances.find { it.date == date }
            sb.appendLine(
                "${date}," +
                "${record?.status ?: "-"}," +
                "${record?.overtimeHours ?: 0}," +
                "${record?.note ?: ""}," +
                "${advance?.amount ?: 0.0}"
            )
        }

        file.writeText(sb.toString())
        return file
    }
}
