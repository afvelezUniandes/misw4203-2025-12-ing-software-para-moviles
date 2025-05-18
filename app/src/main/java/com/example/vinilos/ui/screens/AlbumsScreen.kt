package com.example.vinilos.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.vinilos.models.Album
import com.example.vinilos.ui.components.albums.AlbumsGrid
import com.example.vinilos.ui.components.common.AppScaffold
import com.example.vinilos.ui.components.common.ErrorScreen
import com.example.vinilos.ui.components.common.LoadingScreen
import com.example.vinilos.ui.components.common.NavigationTab
import com.example.vinilos.viewmodels.AlbumViewModel

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

    LaunchedEffect(Unit) {
        viewModel.refreshAlbums()
    }

    AppScaffold(
        selectedTab = NavigationTab.ALBUMS,
        onTabSelected = onNavigateToTab,
        onAddClick = onAddAlbumClick
    ) {
        when {
            isLoading -> LoadingScreen()
            error != null -> ErrorScreen(message = error ?: "Error desconocido") {
                viewModel.loadAlbums()
            }
            else -> AlbumsGrid(albums = albums, onAlbumClick = onAlbumClick)
        }
    }
}
