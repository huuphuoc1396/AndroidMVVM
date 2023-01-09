package com.example.androidmvvm.util.extension

import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN = "dd/MM/yyyy"
const val TIME_PATTERN = "HH:mm"
const val DATE_SERVER_PATTERN = "yyyy-MM-dd HH:mm:ss"

fun Date.format(pattern: String = DATE_PATTERN): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

fun String.parse(pattern: String = DATE_SERVER_PATTERN): Date? {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        formatter.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.isToday(): Boolean {
    val calendar = Calendar.getInstance().apply {
        time = this@isToday
    }
    val todayCalendar = Calendar.getInstance()
    return calendar.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH) &&
            calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
            calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)
}

fun Date.isYesterday(): Boolean {
    val calendar = Calendar.getInstance().apply {
        time = this@isYesterday
    }
    val yesterdayCalendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }
    return calendar.get(Calendar.DAY_OF_MONTH) == yesterdayCalendar.get(Calendar.DAY_OF_MONTH) &&
            calendar.get(Calendar.MONTH) == yesterdayCalendar.get(Calendar.MONTH) &&
            calendar.get(Calendar.YEAR) == yesterdayCalendar.get(Calendar.YEAR)
}