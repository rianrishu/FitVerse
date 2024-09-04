package com.rianrishu.fitverse.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rianrishu.fitverse.data.model.DataType

/**
 * A row representing the basic information of a user Activity.
 */
@Composable
fun DetailsCard(
    modifier: Modifier = Modifier,
    metricValue: String = "5000",
    dataType: DataType = DataType.STEPS,
    toDatetime: String = "23rd September, 2024",
    fromDatetime: String = "23rd March, 2024",
    color: Color = Color.Red
) {
    BaseRow(
        modifier = modifier,
        color = color,
        title = dataType.toString(),
        metricValue = metricValue,
        fromDatetime = fromDatetime,
        toDatetime = toDatetime
    )
}

@Composable
private fun BaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    metricValue: String,
    toDatetime: String,
    fromDatetime: String
) {
    Row(
        modifier = modifier
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val typography = MaterialTheme.typography
        AccountIndicator(
            color = color,
            modifier = Modifier
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier) {
            Text(text = title, style = typography.bodyLarge)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = fromDatetime, style = typography.bodyMedium)
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = toDatetime, style = typography.bodyMedium)
            }
        }
        Spacer(Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = metricValue,
                style = typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    CardDivider()
}

/**
 * A vertical colored line that is used in a [BaseRow] to differentiate activities.
 */
@Composable
private fun AccountIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .size(4.dp, 36.dp)
            .background(color = color)
    )
}

@Composable
fun CardDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.background
    )
}