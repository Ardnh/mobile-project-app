package com.example.mobileprojectapp.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * ========================================
 * CURRENCY FORMAT (Rupiah)
 * ========================================
 */

/**
 * Format ke "Rp 130.000.000"
 * Example: 130000000.0 -> "Rp 130.000.000"
 */
fun Double.toCurrencyFormat(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("en", "US"))
    return formatter.format(this)
}

fun Long.toCurrencyFormat(): String {
    return this.toDouble().toCurrencyFormat()
}

fun Int.toCurrencyFormat(): String {
    return this.toDouble().toCurrencyFormat()
}

/**
 * Format ke "Rp 130.000.000,50" (dengan desimal)
 * Example: 130000000.50 -> "Rp 130.000.000,50"
 */
fun Double.toCurrencyFormatWithDecimal(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("en", "US"))
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    return formatter.format(this)
}

/**
 * Format ke "130.000.000" (tanpa Rp)
 * Example: 130000000.0 -> "130.000.000"
 */
fun Double.toNumberFormat(): String {
    val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
    formatter.maximumFractionDigits = 0
    return formatter.format(this)
}

fun Long.toNumberFormat(): String {
    return this.toDouble().toNumberFormat()
}

fun Int.toNumberFormat(): String {
    return this.toDouble().toNumberFormat()
}

/**
 * Format ke "130.000.000,50" (dengan desimal, tanpa Rp)
 * Example: 130000000.50 -> "130.000.000,50"
 */
fun Double.toNumberFormatWithDecimal(): String {
    val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    return formatter.format(this)
}

/**
 * ========================================
 * COMPACT FORMAT (Singkat)
 * ========================================
 */

/**
 * Format ke format singkat Indonesia
 * Example:
 * - 500 -> "500"
 * - 1500 -> "1,5 Ribu"
 * - 1500000 -> "1,5 Juta"
 * - 1500000000 -> "1,5 Miliar"
 * - 1500000000000 -> "1,5 Triliun"
 */
fun Double.toCompactFormat(): String {
    return when {
        this >= 1_000_000_000_000 -> String.format(Locale("id", "ID"), "%.1f Triliun", this / 1_000_000_000_000)
        this >= 1_000_000_000 -> String.format(Locale("id", "ID"), "%.1f Miliar", this / 1_000_000_000)
        this >= 1_000_000 -> String.format(Locale("id", "ID"), "%.1f Juta", this / 1_000_000)
        this >= 1_000 -> String.format(Locale("id", "ID"), "%.1f Ribu", this / 1_000)
        else -> String.format(Locale("id", "ID"), "%.0f", this)
    }
}

fun Long.toCompactFormat(): String {
    return this.toDouble().toCompactFormat()
}

fun Int.toCompactFormat(): String {
    return this.toDouble().toCompactFormat()
}

/**
 * Format ke format singkat tanpa desimal
 * Example:
 * - 1500000 -> "1 Juta"
 * - 1500000000 -> "1 Miliar"
 */
fun Double.toCompactFormatNoDecimal(): String {
    return when {
        this >= 1_000_000_000_000 -> String.format(Locale("id", "ID"), "%.0f Triliun", this / 1_000_000_000_000)
        this >= 1_000_000_000 -> String.format(Locale("id", "ID"), "%.0f Miliar", this / 1_000_000_000)
        this >= 1_000_000 -> String.format(Locale("id", "ID"), "%.0f Juta", this / 1_000_000)
        this >= 1_000 -> String.format(Locale("id", "ID"), "%.0f Ribu", this / 1_000)
        else -> String.format(Locale("id", "ID"), "%.0f", this)
    }
}

fun Long.toCompactFormatNoDecimal(): String {
    return this.toDouble().toCompactFormatNoDecimal()
}

/**
 * Format ke format singkat dengan "Rp"
 * Example: 1500000 -> "Rp 1,5 Juta"
 */
fun Double.toCompactCurrencyFormat(): String {
    return "Rp ${this.toCompactFormat()}"
}

fun Long.toCompactCurrencyFormat(): String {
    return this.toDouble().toCompactCurrencyFormat()
}

/**
 * ========================================
 * PERCENTAGE FORMAT
 * ========================================
 */

/**
 * Format ke persentase
 * Example: 0.856 -> "85,6%"
 */
fun Double.toPercentageFormat(): String {
    val formatter = NumberFormat.getPercentInstance(Locale("id", "ID"))
    formatter.minimumFractionDigits = 1
    formatter.maximumFractionDigits = 1
    return formatter.format(this)
}

/**
 * Format integer ke persentase (tanpa konversi)
 * Example: 85 -> "85%"
 */
fun Int.toPercentageFormat(): String {
    return "$this%"
}

fun Long.toPercentageFormat(): String {
    return "$this%"
}

/**
 * ========================================
 * DECIMAL FORMAT (Custom)
 * ========================================
 */

/**
 * Format dengan jumlah desimal custom
 * Example: 1234.5678.toDecimalFormat(2) -> "1.234,57"
 */
//fun Double.toDecimalFormat(decimalPlaces: Int = 2): String {
//    val pattern = if (decimalPlaces > 0) {
//        "#,##0.${"0".repeat(decimalPlaces)}"
//    } else {
//        "#,##0"
//    }
//    val formatter = DecimalFormat(pattern, DecimalFormat.getInstance(Locale("id", "ID")).decimalFormatSymbols)
//    return formatter.format(this)
//}

/**
 * ========================================
 * ORDINAL FORMAT (1st, 2nd, 3rd)
 * ========================================
 */

/**
 * Format ke ordinal number Indonesia
 * Example:
 * - 1 -> "Ke-1"
 * - 2 -> "Ke-2"
 * - 21 -> "Ke-21"
 */
fun Int.toOrdinalFormat(): String {
    return "Ke-$this"
}

/**
 * ========================================
 * FILE SIZE FORMAT
 * ========================================
 */

/**
 * Format bytes ke file size
 * Example:
 * - 1024 -> "1 KB"
 * - 1048576 -> "1 MB"
 * - 1073741824 -> "1 GB"
 */
fun Long.toFileSizeFormat(): String {
    return when {
        this >= 1_099_511_627_776 -> String.format(Locale.US, "%.2f TB", this / 1_099_511_627_776.0)
        this >= 1_073_741_824 -> String.format(Locale.US, "%.2f GB", this / 1_073_741_824.0)
        this >= 1_048_576 -> String.format(Locale.US, "%.2f MB", this / 1_048_576.0)
        this >= 1_024 -> String.format(Locale.US, "%.2f KB", this / 1_024.0)
        else -> "$this B"
    }
}

fun Int.toFileSizeFormat(): String {
    return this.toLong().toFileSizeFormat()
}

/**
 * ========================================
 * NULLABLE EXTENSIONS
 * ========================================
 */

/**
 * Format nullable number dengan default
 * Example: null.toCurrencyOrDefault() -> "Rp 0"
 */
fun Double?.toCurrencyOrDefault(default: String = "Rp 0"): String {
    return this?.toCurrencyFormat() ?: default
}

fun Long?.toCurrencyOrDefault(default: String = "Rp 0"): String {
    return this?.toCurrencyFormat() ?: default
}

fun Double?.toNumberOrDefault(default: String = "0"): String {
    return this?.toNumberFormat() ?: default
}

fun Long?.toNumberOrDefault(default: String = "0"): String {
    return this?.toNumberFormat() ?: default
}

fun Double?.toCompactOrDefault(default: String = "0"): String {
    return this?.toCompactFormat() ?: default
}

fun Long?.toCompactOrDefault(default: String = "0"): String {
    return this?.toCompactFormat() ?: default
}

/**
 * ========================================
 * SPECIAL FORMATS
 * ========================================
 */

/**
 * Format ke format Indonesia dengan satuan
 * Example: 1500000.toRupiahWithUnit() -> "1,5 Juta Rupiah"
 */
fun Double.toRupiahWithUnit(): String {
    return "${this.toCompactFormat()} Rupiah"
}

fun Long.toRupiahWithUnit(): String {
    return this.toDouble().toRupiahWithUnit()
}

/**
 * Format dengan prefix dan suffix custom
 * Example: 1000.toFormatted("Rp ", " IDR") -> "Rp 1.000 IDR"
 */
fun Double.toFormatted(prefix: String = "", suffix: String = ""): String {
    return "$prefix${this.toNumberFormat()}$suffix"
}

fun Long.toFormatted(prefix: String = "", suffix: String = ""): String {
    return this.toDouble().toFormatted(prefix, suffix)
}

/**
 * Format dengan tanda + atau -
 * Example:
 * - 1000 -> "+1.000"
 * - -1000 -> "-1.000"
 */
fun Double.toSignedFormat(): String {
    val formatted = this.toNumberFormat()
    return when {
        this > 0 -> "+$formatted"
        this < 0 -> formatted
        else -> formatted
    }
}

fun Long.toSignedFormat(): String {
    return this.toDouble().toSignedFormat()
}

fun Int.toSignedFormat(): String {
    return this.toDouble().toSignedFormat()
}