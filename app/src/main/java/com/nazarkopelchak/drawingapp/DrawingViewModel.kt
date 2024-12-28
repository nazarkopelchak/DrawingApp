package com.nazarkopelchak.drawingapp

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

val allColors = listOf(
    Color.Black,
    Color.Red,
    Color.Blue,
    Color.Green,
    Color.Yellow,
    Color.Magenta,
    Color.Cyan,
)

class DrawingViewModel: ViewModel() {

    private val _state = MutableStateFlow(DrawingState())
    val state = _state.asStateFlow()

    fun onEvent(event: DrawingAction) {
        when(event){
            DrawingAction.OnClearCanvasClick -> onClearCanvasClick()
            is DrawingAction.OnDraw -> onDraw(event.offset)
            DrawingAction.OnDrawEnd -> onDrawEnd()
            DrawingAction.OnDrawStart -> onDrawStart()
            is DrawingAction.OnSelectedColor -> onSelectedColor(event.color)
        }
    }

    private fun onClearCanvasClick() {
        _state.update { it.copy(
            currentPath = null,
            paths = emptyList()
        ) }
    }

    private fun onSelectedColor(color: Color) {
        _state.update { it.copy(
            selectedColor = color
        ) }
    }

    private fun onDrawStart() {
        _state.update { it.copy(
            currentPath = PathData(
                id = UUID.randomUUID().toString(),
                color = it.selectedColor,
                path = emptyList()
            )
        ) }
    }

    private fun onDrawEnd() {
        val currentPathData = state.value.currentPath ?: return
        _state.update { it.copy(
            currentPath = null,
            paths = it.paths + currentPathData
        ) }
    }

    private fun onDraw(offset: Offset) {
        val currentPathData = state.value.currentPath ?: return
        _state.update { it.copy(
            currentPath = currentPathData.copy(
                path = currentPathData.path + offset
            )
        ) }
    }
}