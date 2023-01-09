package com.example.androidmvvm.util.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

const val GROUPING_SEPARATOR = ','
const val DECIMAL_SEPARATOR = '.'

val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols.getInstance().apply {
    groupingSeparator = GROUPING_SEPARATOR
    decimalSeparator = DECIMAL_SEPARATOR
})

fun Int.format(): String {
    return numberFormat.format(this)
}

fun Long.format(): String {
    return numberFormat.format(this)
}

fun Double.format(): String {
    return numberFormat.format(this)
}

fun Float.format(): String {
    return numberFormat.format(this)
}

val numberFormat2Digits = DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance().apply {
    groupingSeparator = GROUPING_SEPARATOR
    decimalSeparator = DECIMAL_SEPARATOR
})

fun Int.format2Digits(): String {
    return numberFormat2Digits.format(this)
}

fun Long.format2Digits(): String {
    return numberFormat2Digits.format(this)
}

fun Double.format2Digits(): String {
    return numberFormat2Digits.format(this)
}

fun Float.format2Digits(): String {
    return numberFormat2Digits.format(this)
}

fun BigDecimal.format2Digits(): String {
    return numberFormat2Digits.format(this)
}

val numberFormat1Digits = DecimalFormat("#,##0.0", DecimalFormatSymbols.getInstance().apply {
    groupingSeparator = GROUPING_SEPARATOR
    decimalSeparator = DECIMAL_SEPARATOR
})

fun Int.format1Digits(): String {
    return numberFormat1Digits.format(this)
}

fun Long.format1Digits(): String {
    return numberFormat1Digits.format(this)
}

fun Double.format1Digits(): String {
    return numberFormat1Digits.format(this)
}

fun Float.format1Digits(): String {
    return numberFormat1Digits.format(this)
}

fun BigDecimal.format1Digits(): String {
    return numberFormat1Digits.format(this)
}