package com.rianrishu.fitverse.ui.common

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.rianrishu.fitverse.data.model.SelectedProportion
import com.rianrishu.fitverse.utils.isAngleInRange
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

private const val DividerLengthInDegrees = 1.8f

@Composable
fun BaseAnimatedCircle(
    proportions: List<SelectedProportion>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    onProportionSelected: (SelectedProportion) -> Unit = {}
) {
    val currentState = remember {
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply { targetState = AnimatedCircleProgress.END }
    }
    val stroke = with(LocalDensity.current) { Stroke(10.dp.toPx()) }
    val transition = updateTransition(currentState, label = "")
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = LinearOutSlowInEasing
            )
        }, label = ""
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            360f
        }
    }
    val shift by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = CubicBezierEasing(0f, 0.75f, 0.35f, 0.85f)
            )
        }, label = ""
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            30f
        }
    }

    val currentProportions by rememberUpdatedState(newValue = proportions)

    Canvas(modifier = modifier.pointerInput(Unit) {
        detectTapGestures { offset ->

            // Center of the canvas
            val center = size / 2

            // Calculate the distance from the center of the circle to the tap
            val touchRadius =
                sqrt((offset.x - center.width).pow(2) + (offset.y - center.height).pow(2))

            // Only proceed if the tap is inside the circle
            if (touchRadius <= (min(size.width, size.height) - stroke.width) / 2) {

                // Calculate the angle of the touch (in degrees)
                val touchAngle = (atan2(
                    offset.y - center.height,
                    offset.x - center.width
                ) * 180 / Math.PI + 360) % 360  // Normalize to 0-360 range

                // Initialize the start angle (shift takes care of animation offset)
                var startAngle = (shift - 90f + 360) % 360 // Normalize startAngle to 0-360

                currentProportions.forEachIndexed { _, proportion ->

                    // Calculate the sweep angle for the current proportion
                    val sweep = proportion.proportion * angleOffset

                    // Calculate the end angle
                    val endAngle = (startAngle + sweep) % 360

                    // Check if the touch angle lies within the start and end angles
                    if (isAngleInRange(touchAngle, startAngle, endAngle)) {
                        onProportionSelected(proportion)
                        return@detectTapGestures
                    }

                    // Move to the next segment
                    startAngle = endAngle
                }
            }
        }
    }) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        var startAngle = shift - 90f
        proportions.forEachIndexed { index, proportion ->
            val sweep = proportion.proportion * angleOffset
            drawArc(
                color = colors[index],
                startAngle = startAngle + DividerLengthInDegrees / 2,
                sweepAngle = sweep - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = stroke
            )
            startAngle += sweep
        }
    }
}

private enum class AnimatedCircleProgress { START, END }
