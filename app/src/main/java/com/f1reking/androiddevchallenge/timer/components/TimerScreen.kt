package com.f1reking.androiddevchallenge.timer.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.f1reking.androiddevchallenge.timer.ui.theme.bgCircle
import com.f1reking.androiddevchallenge.timer.ui.theme.bgColor
import com.f1reking.androiddevchallenge.timer.ui.theme.circle
import com.f1reking.androiddevchallenge.timer.ui.theme.green
import com.f1reking.androiddevchallenge.timer.ui.theme.red
import com.f1reking.androiddevchallenge.timer.ui.theme.white
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var showStart by mutableStateOf(true)
var countdowning by mutableStateOf(false)
var isPause by mutableStateOf(true)
var progress by mutableStateOf(362f)

@Composable
fun TimerScreen(coroutineScope: CoroutineScope) {

    Surface(color = bgColor, modifier = Modifier.fillMaxHeight()) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Countdown Timer",
                color = white,
                modifier = Modifier.padding(0.dp, 100.dp, 0.dp, 0.dp),
                style = MaterialTheme.typography.h4
            )
        }

        AnimatedCircle(
            Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(50.dp, 50.dp, 50.dp, 120.dp), 362F, bgCircle
        )
        AnimatedCircle(
            Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(50.dp, 50.dp, 50.dp, 120.dp), progress, circle
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var seconds = (((progress - 2) % 120) / 2).toInt()
            Text(
                text = "${((progress - 2) / 120).toInt()}:${if (seconds < 10) "0$seconds" else seconds}",
                color = Color.White,
                modifier = Modifier.padding(50.dp, 280.dp, 50.dp, 0.dp),
                style = MaterialTheme.typography.h2
            )
            if (showStart) {
                ShowStartButton(coroutineScope)
            } else {
                ShowStopButton()
            }
        }


    }


}

@Composable
fun ShowStartButton(coroutineScope: CoroutineScope) {
    Row(
        modifier = Modifier
            .padding(
                start = 48.dp,
                end = 48.dp,
                bottom = 24.dp,
                top = 180.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                showStart = false
                countdowning = true
                coroutineScope.launch {
                    setAnimation()
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = green),
            modifier = Modifier
                .clip(CircleShape)
                .size(80.dp)
        ) {
            Text(
                text = "Start",
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
fun ShowStopButton() {
    Row(
        modifier = Modifier
            .padding(
                start = 48.dp,
                end = 48.dp,
                bottom = 24.dp,
                top = 180.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                showStart = true
                countdowning = false
                isPause = true
                progress = 362f
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = red),
            modifier = Modifier
                .clip(CircleShape)
                .size(80.dp)
        ) {
            Text(
                text = "Stop",
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
        }
    }
}


private const val StrokeLength = 1.8f

@Composable
fun AnimatedCircle(modifier: Modifier = Modifier, sweep: Float, color: Color) {
    val stroke = with(LocalDensity.current) { Stroke(5.dp.toPx()) }
    Canvas(modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(halfSize.width - innerRadius, halfSize.height - innerRadius)
        val size = Size(innerRadius * 2, innerRadius * 2)
        val startAngle = -90f
        drawArc(
            color = color,
            startAngle = startAngle + StrokeLength / 2,
            sweepAngle = sweep - StrokeLength,
            topLeft = topLeft,
            size = size,
            useCenter = false,
            style = stroke
        )
    }
}

suspend fun setAnimation() {
    while (countdowning) {
        progress -= 1
        if (progress <= 2) {
            progress = 362f
            countdowning = false
            showStart = true
        }
        delay(500)
    }
}

@Preview
@Composable
fun TimerPreview() {
    val coroutineScope = rememberCoroutineScope()
    TimerScreen(coroutineScope)
}

