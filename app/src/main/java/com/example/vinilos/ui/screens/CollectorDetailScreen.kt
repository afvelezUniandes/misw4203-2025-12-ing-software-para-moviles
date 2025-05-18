package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.ui.components.collectors.CollectorDetailContent
import com.example.vinilos.ui.components.common.ErrorScreen
import com.example.vinilos.ui.components.common.LoadingScreen
import com.example.vinilos.viewmodels.CollectorDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectorDetailScreen(
    viewModel: CollectorDetailViewModel,
    collectorId: Int,
    onBackClick: () -> Unit
) {
    val collector by viewModel.collector.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val albumRepository = remember { AlbumRepository() }

    LaunchedEffect(collectorId) {
        viewModel.loadCollectorDetail(collectorId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Coleccionista", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0277BD),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> LoadingScreen()
                error != null -> ErrorScreen(message = error ?: "Error desconocido") {
                    viewModel.loadCollectorDetail(collectorId)
                }

                collector != null -> {
                    CollectorDetailContent(
                        collector = collector!!,
                        albumRepository = albumRepository
                    )
                }
            }
        }
    }
}
