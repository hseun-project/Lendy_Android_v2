package com.hseun.lendy_v2.utils

import android.util.Log
import kotlin.math.floor

fun calculateInterest(
    duringType: DuringType,
    during: Int,
    money: Int,
    interestStr: String
): Int {
    try {
        val annualRate = interestStr.toFloat() / 100
        val rate = when(duringType) {
            DuringType.DAY -> annualRate / 365
            DuringType.MONTH -> annualRate / 12
        }
        val interest = floor((money * rate * during)).toInt()
        return interest
    } catch (e: Exception) {
        Log.e("calculateInterest", e.message.toString())
        return 0
    }
}