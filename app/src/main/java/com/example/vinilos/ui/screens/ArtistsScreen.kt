package com.example.vinilos.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.vinilos.models.Artist
import com.example.vinilos.ui.components.artists.ArtistsList
import com.example.vinilos.ui.components.common.AppScaffold
import com.example.vinilos.ui.components.common.ErrorScreen
import com.example.vinilos.ui.components.common.LoadingScreen
import com.example.vinilos.ui.components.common.NavigationTab
import com.example.vinilos.viewmodels.ArtistViewModel

@Composable
fun ArtistsScreen(
    viewModel: ArtistViewModel,
    onNavigateToTab: (NavigationTab) -> Unit,
    onArtistClick: (Artist) -> Unit
) {
    val artists by viewModel.artists.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadAllArtists()
    }

    AppScaffold(
        selectedTab = NavigationTab.ARTISTS,
        onTabSelected = onNavigateToTab,
        onAddClick = {}
    ) {
        when {
            isLoading -> LoadingScreen()
            error != null -> ErrorScreen(message = error ?: "Error desconocido") {
                viewModel.loadAllArtists()
            }
            else -> ArtistsList(artists = artists, onArtistClick = onArtistClick)
        }
    }
}
