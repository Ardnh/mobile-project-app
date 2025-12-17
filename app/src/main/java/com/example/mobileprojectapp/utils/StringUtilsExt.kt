package com.example.mobileprojectapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateOrNull(): LocalDate? {
    if (this.isEmpty() || this.isBlank()) return null

    return try {
        val dateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        dateTime.toLocalDate()
    } catch (e: Exception) {
        null
    }
}

fun String?.toLong(default: Long = 0L): Long {
    return this?.toLongOrNull() ?: default
}
