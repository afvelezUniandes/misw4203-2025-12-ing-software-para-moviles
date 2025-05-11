package com.example.vinilos.ui.components.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vinilos.R
import androidx.compose.ui.graphics.Color


@Composable
fun CircleIcon() {
    Icon(
        painter = painterResource(R.drawable.app_icon_round),
        contentDescription = "Icono",
        tint = Color.Unspecified,
        modifier = Modifier
            .size(50.dp)
            .padding(start = 0.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)

    )
}
