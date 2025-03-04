package com.example.zafrair.ui.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Coords(val x: Float, val y: Float)

val sampleCoordsList = listOf(
    Coords(x = 10f, y = 10f),
    Coords(x = 26f, y = 23f),
    Coords(x = 30f, y = 24f),
    Coords(x = 40f, y = 25f),
    Coords(x = 50f, y = 26f),
    Coords(x = 60f, y = 27f),
    Coords(x = 70f, y = 28f),
    Coords(x = 80f, y = 29f),
    Coords(x = 90f, y = 30f),
    Coords(x = 100f, y = 31f),
    Coords(x = 110f, y = 32f),
    Coords(x = 120f, y = 33f),
    Coords(x = 130f, y = 34f),
    Coords(x = 140f, y = 35f),
    Coords(x = 150f, y = 36f),
    Coords(x = 160f, y = 37f),
    Coords(x = 170f, y = 38f),
    Coords(x = 180f, y = 39f),
    Coords(x = 190f, y = 40f),
    Coords(x = 200f, y = 41f),
    Coords(x = 210f, y = 42f),
    Coords(x = 220f, y = 10f),

)

@Composable
fun ACScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DrawChart(
            coordsList = sampleCoordsList,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp),
            lineColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DrawChart(
    coordsList: List<Coords>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Red
) {
    if (coordsList.size < 2) return

    Canvas(modifier = modifier) {
        val totalWidth = size.width
        val totalHeight = size.height

        val maxX = coordsList.maxOf { it.x }
        val maxY = coordsList.maxOf { it.y }

        val xScale = totalWidth / maxX
        val yScale = totalHeight / maxY

        val path = Path()

        // Draw X-axis
        drawLine(
            start = Offset(0f, totalHeight),
            end = Offset(totalWidth, totalHeight),
            color = Color.Gray,
            strokeWidth = 2f
        )

        // Draw Y-axis
        drawLine(
            start = Offset(0f, 0f),
            end = Offset(0f, totalHeight),
            color = Color.Gray,
            strokeWidth = 2f
        )

        coordsList.forEachIndexed { index, coords ->
            val x = coords.x * xScale
            val y = totalHeight - coords.y * yScale

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3f, cap = StrokeCap.Round)
        )
    }
}