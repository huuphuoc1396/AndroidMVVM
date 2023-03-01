package com.example.androidmvvm.domain.util

import java.util.*

fun String?.defaultEmpty(): String {
    return this.default("")
}

fun Int?.defaultZero(): Int {
    return this.default(0)
}

fun Long?.defaultZero(): Long {
    return this.default(0)
}

fun Double?.defaultZero(): Double {
    return this.default(0.0)
}

fun Float?.defaultZero(): Float {
    return this.default(0.0f)
}

fun <T> List<T>?.defaultEmpty(): List<T> {
    return this ?: listOf()
}

fun <K, V> Map<K, V>?.defaultEmpty(): Map<K, V> {
    return this ?: mapOf()
}

fun <T> Set<T>?.defaultEmpty(): Set<T> {
    return this ?: setOf()
}

fun Date?.defaultToday(): Date {
    return this.default(Date())
}

fun Boolean?.defaultFalse(): Boolean {
    return this.default(false)
}

fun Boolean?.defaultTrue(): Boolean {
    return this.default(true)
}

fun <T> T?.default(default: T): T {
    return this ?: default
}