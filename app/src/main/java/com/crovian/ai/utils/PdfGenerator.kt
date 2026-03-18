package com.crovian.ai.utils

import android.content.Context
import com.crovian.ai.data.model.AdvancePayment
import com.crovian.ai.data.model.AttendanceRecord
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.io.font.constants.StandardFonts
import java.io.File
import java.io.FileOutputStream

object PdfGenerator {

    fun generateMonthlyReport(
        context: Context,
        userName: String,
        month: String,
        records: List<AttendanceRecord>,
        advances: List<AdvancePayment>,
        dailyWage: Double
    ): File {
        val fullDays = records.count { it.status == "full_day" }
        val halfDays = records.count { it.status == "half_day" }
        val totalDaysWorked = fullDays + (halfDays * 0.5)
        val grossEarnings = totalDaysWorked * dailyWage
        val totalAdvances = advances.sumOf { it.amount }
        val netPayable = grossEarnings - totalAdvances

        val fileName = "Payroll_${userName}_${month}.pdf"
        val file = File(context.getExternalFilesDir(null), fileName)

        val writer = PdfWriter(FileOutputStream(file))
        val pdf = PdfDocument(writer)
        val document = Document(pdf)

        // Title
        val titleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)
        document.add(Paragraph("Monthly Payroll Report").setFont(titleFont).setFontSize(18f))
        document.add(Paragraph("Employee: $userName"))
        document.add(Paragraph("Month: $month"))
        document.add(Paragraph(" "))

        // Summary Table
        val table = Table(floatArrayOf(1f, 1f))
        table.addCell("Total Days Worked")
        table.addCell("$totalDaysWorked")
        table.addCell("Daily Wage")
        table.addCell("Rs. $dailyWage")
        table.addCell("Gross Earnings")
        table.addCell("Rs. $grossEarnings")
        table.addCell("Total Advances")
        table.addCell("Rs. $totalAdvances")
        table.addCell("Net Payable")
        table.addCell("Rs. $netPayable")
        document.add(table)

        document.add(Paragraph(" "))
        document.add(Paragraph("Day-wise Attendance:").setFont(titleFont).setFontSize(12f))

        // Daily records
        val dailyTable = Table(floatArrayOf(1f, 1f, 1f, 1f))
        dailyTable.addCell("Date")
        dailyTable.addCell("Status")
        dailyTable.addCell("Overtime")
        dailyTable.addCell("Advance")

        records.sortedBy { it.date }.forEach { record ->
            val advance = advances.find { it.date == record.date }
            dailyTable.addCell(record.date)
            dailyTable.addCell(record.status.replace("_", " ").capitalize())
            dailyTable.addCell(if (record.overtimeHours > 0) "${record.overtimeHours}h" else "-")
            dailyTable.addCell(if (advance != null) "Rs. ${advance.amount}" else "-")
        }

        document.add(dailyTable)
        document.close()

        return file
    }
}
