package com.example.zafrair.ui.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


data class Coords(val x: Float, val y: Float)

val coordsList = listOf(
    Coords(x = 10f, y = 10f),
    Coords(x = 20f, y = 20f),
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

)



@Composable
fun ACScreen() {
    Column {
        DrawChart(
            coordsList = coordsList,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding(12.dp),
        )
    }
}

@Composable
fun DrawChart(coordsList: List<Coords>, modifier: Modifier = Modifier) {
    if (coordsList.size < 2) return
    println("coordsList: $coordsList")
    Canvas(modifier = modifier) {
        val totalWidth = size.width
        val totalHeight = size.height

        val maxX = coordsList.maxOf { it.x }
        val maxY = coordsList.maxOf { it.y }

        val xScale = totalWidth / maxX
        val yScale = totalHeight / maxY

        var currentX = coordsList.first().x * xScale
        var currentY = totalHeight - coordsList.first().y * yScale

        coordsList.forEachIndexed { index, coords ->
            if (true) {
                val nextX = coords.x * xScale
                val nextY = totalHeight - coords.y * yScale

                drawLine(
                    start = Offset(x = currentX, y = currentY),
                    end = Offset(x = nextX, y = nextY),
                    color = Color.Red,
                    strokeWidth = Stroke.DefaultMiter
                )
                currentX = nextX
                currentY = nextY
            }
        }
    }
}