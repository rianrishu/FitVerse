package com.rianrishu.fitverse.utils

fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}

fun convertSleepDurationToMinutes(sleepDuration: String): Float {
    try {
        val parts = sleepDuration.split(":")
        val hours = parts[0].toFloat()
        val minutes = parts[1].toInt()
        return hours * 60 + minutes
    } catch (e: IndexOutOfBoundsException) {
        return 0f
    }
}

fun convertMilesToMeters(miles: String): Float {
    return miles.toFloat() * 1609.34f
}