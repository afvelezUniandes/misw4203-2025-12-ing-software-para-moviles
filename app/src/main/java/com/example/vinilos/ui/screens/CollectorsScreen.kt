package com.example.vinilos.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.vinilos.models.Collector
import com.example.vinilos.ui.components.collectors.CollectorsList
import com.example.vinilos.ui.components.common.AppScaffold
import com.example.vinilos.ui.components.common.ErrorScreen
import com.example.vinilos.ui.components.common.LoadingScreen
import com.example.vinilos.ui.components.common.NavigationTab
import com.example.vinilos.viewmodels.CollectorViewModel

@Composable
fun CollectorsScreen(
    viewModel: CollectorViewModel,
    onNavigateToTab: (NavigationTab) -> Unit,
    onCollectorClick: (Collector) -> Unit = {}
) {
    val collectors by viewModel.collectors.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadAllCollectors()
    }

    AppScaffold(
        selectedTab = NavigationTab.COLLECTORS,
        onTabSelected = onNavigateToTab,
        onAddClick = {}
    ) {
        when {
            isLoading -> LoadingScreen()
            error != null -> ErrorScreen(message = error ?: "Error desconocido") {
                viewModel.loadAllCollectors()
            }
            else -> CollectorsList(collectors = collectors, onCollectorClick = onCollectorClick)
        }
    }
}
