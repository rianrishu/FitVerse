package com.rianrishu.fitverse.utils

import com.rianrishu.fitverse.data.model.SelectedProportion

fun <E> List<E>.extractProportions(selector: (E) -> SelectedProportion): List<SelectedProportion> {
    val total = this.sumOf { selector(it).proportion.toDouble() }
    return this.map {
        val selected = selector(it)
        SelectedProportion((selected.proportion / total).toFloat(), selected.dataType)
    }
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