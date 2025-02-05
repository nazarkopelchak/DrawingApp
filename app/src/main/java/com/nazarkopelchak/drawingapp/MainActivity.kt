package com.nazarkopelchak.drawingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nazarkopelchak.drawingapp.ui.theme.DrawingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = viewModel<DrawingViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DrawingCanvas(
                            currentPath = state.currentPath,
                            paths = state.paths,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        CanvasControls(
                            selectedColor = state.selectedColor,
                            colors = allColors,
                            onSelectedColor = { color ->
                                viewModel.onEvent(DrawingAction.OnSelectedColor(color))
                            },
                            onClearCanvas = {
                                viewModel.onEvent(DrawingAction.OnClearCanvasClick)
                            }
                        )
                        // Causes unnecessarily recompositions. Moved to CanvasControls.kt
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp),
//                            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
//                        ) {
//                            allColors.fastForEach { color ->
//                                val isColorSelected = state.selectedColor == color
//                                Box(
//                                    modifier = Modifier
//                                        .graphicsLayer {    // this modifier has to be before the size modifier
//                                            val scale = if (isColorSelected) 1.2f else 1f
//                                            scaleX = scale
//                                            scaleY = scale
//                                        }
//                                        .size(40.dp)
//                                        .clip(CircleShape)
//                                        .background(color)
//                                        .border(
//                                            width = 2.dp,
//                                            color = if (isColorSelected) {
//                                                Color.Black
//                                            } else {
//                                                Color.Transparent
//                                            },
//                                            shape = CircleShape
//                                        )
//                                        .clickable {
//                                            viewModel.onEvent(DrawingAction.OnSelectedColor(color))
//                                        }
//                                )
//                            }
//                        }
//                        Button(
//                            onClick = {
//                                viewModel.onEvent(DrawingAction.OnClearCanvasClick)
//                            }
//                        ) {
//                            Text(text = "Clear Canvas")
//                        }
                    }
                }
            }
        }
    }
}