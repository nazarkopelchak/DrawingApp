package com.nazarkopelchak.drawingapp

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

sealed interface DrawingAction {
    data class OnSelectedColor(val color: Color): DrawingAction
    data object OnClearCanvasClick: DrawingAction
    data class OnDraw(val offset: Offset): DrawingAction
    data object OnDrawStart: DrawingAction
    data object OnDrawEnd: DrawingAction
}