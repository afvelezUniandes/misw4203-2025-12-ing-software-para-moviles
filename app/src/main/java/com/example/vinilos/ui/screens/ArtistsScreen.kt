package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vinilos.ui.components.common.AppScaffold
import com.example.vinilos.ui.components.common.NavigationTab

@Composable
fun ArtistsScreen(
    onNavigateToTab: (NavigationTab) -> Unit
) {
    AppScaffold(
        selectedTab = NavigationTab.ARTISTS,
        onTabSelected = onNavigateToTab,
        onAddClick = { }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Pantalla de Artistas - Próximamente")
        }
    }
}
