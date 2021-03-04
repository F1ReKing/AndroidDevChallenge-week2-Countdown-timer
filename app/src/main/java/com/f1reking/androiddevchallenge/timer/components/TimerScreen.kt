package com.f1reking.androiddevchallenge.timer.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import com.f1reking.androiddevchallenge.timer.ui.theme.purple500

@Composable
fun TimerScreen() {

    AnimatedCircle(
        Modifier
            .height(190.dp)
            .fillMaxWidth()
            .padding(10.dp)
        , 362F)

}

private const val DividerLengthInDegrees = 1.8f

@Composable
fun AnimatedCircle(modifier: Modifier = Modifier, sweep: Float) {
    val stroke = with(LocalDensity.current) { Stroke(5.dp.toPx()) }
    Canvas(modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(halfSize.width - innerRadius, halfSize.height - innerRadius)
        val size = Size(innerRadius * 2, innerRadius * 2)
        val startAngle = -90f
        drawArc(
            color = purple500,
            startAngle = startAngle + DividerLengthInDegrees / 2,
            sweepAngle = sweep - DividerLengthInDegrees,
            topLeft = topLeft,
            size = size,
            useCenter = false,
            style = stroke
        )

    }
}

@Preview
@Composable
fun TimerPreview() {
    TimerScreen()
}