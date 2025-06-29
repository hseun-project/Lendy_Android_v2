package com.hseun.lendy_v2.utils

import java.util.Locale

fun formatTimer(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format(Locale.getDefault(), "%01d:%02d", minutes, remainingSeconds)
}