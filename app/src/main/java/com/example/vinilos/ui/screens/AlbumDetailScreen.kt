package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.vinilos.ui.components.albums.AlbumDetailContent
import com.example.vinilos.ui.components.common.ErrorScreen
import com.example.vinilos.ui.components.common.LoadingScreen
import com.example.vinilos.viewmodels.AlbumDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    viewModel: AlbumDetailViewModel,
    albumId: Int,
    onBackClick: () -> Unit
) {
    val album by viewModel.album.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(albumId) {
        viewModel.loadAlbumDetail(albumId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Álbum", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
                    viewModel.loadAlbumDetail(albumId)
                }
                album != null -> AlbumDetailContent(
                    album = album!!,
                    onAddTrackClick = { }
                )
            }
        }
    }
}
