package com.example.mobileprojectapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Format LocalDate ke "12 Okt 2025"
 */
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDisplayFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("id", "ID"))
    return this.format(formatter)
}

/**
 * Format LocalDate ke "12 Oktober 2025" (nama bulan lengkap)
 */
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toFullDisplayFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
    return this.format(formatter)
}

/**
 * Format LocalDate ke "Jumat, 12 Okt 2025"
 */
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDisplayFormatWithDay(): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale("id", "ID"))
    return this.format(formatter)
}

/**
 * Format LocalDate ke "12/10/2025"
 */
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toShortFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return this.format(formatter)
}