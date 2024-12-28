package com.nazarkopelchak.drawingapp

import androidx.compose.ui.graphics.Color

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList()
)
