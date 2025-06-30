package com.hseun.lendy_v2.utils

import android.util.Log
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

fun calculateEndDate(startDate: Date, duringType: DuringType, during: Int): LocalDate {
    return try {
        val date = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        return when(duringType) {
            DuringType.DAY -> date.plusDays(during.toLong())
            DuringType.MONTH -> date.plusMonths(during.toLong())
        }
    } catch (e: Exception) {
        Log.e("calculateEndDate", e.message.toString())
        LocalDate.now()
    }
}

fun calculateDueDate(startDate: Date, duringType: DuringType, during: Int): String {
    return try {
        val endDate = calculateEndDate(startDate, duringType, during)
        val today = LocalDate.now()
        val dueDate = ChronoUnit.DAYS.between(today, endDate)
        if (dueDate > 0) "D-${dueDate}" else if (dueDate < 0) "D+${-dueDate}" else "D-DAY"
    } catch (e: Exception) {
        Log.e("calculateDueDate", e.message.toString())
        "D-0"
    }
}