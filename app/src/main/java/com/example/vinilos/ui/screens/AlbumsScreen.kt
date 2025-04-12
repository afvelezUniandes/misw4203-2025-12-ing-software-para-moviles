package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.vinilos.ui.components.albums.AlbumsGrid
import com.example.vinilos.ui.components.common.AppScaffold
import com.example.vinilos.ui.components.common.ErrorScreen
import com.example.vinilos.ui.components.common.LoadingScreen
import com.example.vinilos.ui.components.common.NavigationTab
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.models.Album

@Composable
fun AlbumsScreen(
    viewModel: AlbumViewModel,
    onNavigateToTab: (NavigationTab) -> Unit,
    onAlbumClick: (Album) -> Unit,
    onAddAlbumClick: () -> Unit
) {
    val albums by viewModel.albums.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    AppScaffold(
        selectedTab = NavigationTab.ALBUMS,
        onTabSelected = onNavigateToTab,
        onAddClick = onAddAlbumClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                isLoading -> LoadingScreen()
                error != null -> ErrorScreen(message = error ?: "Error desconocido") {
                    viewModel.loadAlbums()
                }
                else -> AlbumsGrid(
                    albums = albums,
                    onAlbumClick = { album -> onAlbumClick(album) }
                )
            }
        }
    }
}
