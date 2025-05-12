package com.example.vinilos.ui.components.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CircleIcon() {
    AsyncImage(
        model = "https://drive.google.com/uc?export=download&id=1BIzP6UTcoLLbiuCZHQDfEftWDvaKBwSd",
        contentDescription = "Icono",
        modifier = Modifier
            .size(50.dp)
            .padding(start = 0.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
    )
}
