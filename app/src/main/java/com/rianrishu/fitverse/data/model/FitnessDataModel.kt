package com.rianrishu.fitverse.data.model

import androidx.compose.ui.graphics.Color

data class BasicActivity(
    val dataRecord: DataRecord,
    val color: Color
)

data class DataRecord(
    val metricValue: String,
    val dataType :  DataType,
    val toDatetime: String,
    val fromDatetime: String
)

data class SelectedProportion(
    val proportion: Float,
    val dataType: DataType
)

enum class DataType{
    STEPS,MINS,DISTANCE,SLEEP
}

