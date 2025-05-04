package com.example.vinilos.ui.components.common

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vinilos.network.RetrofitInstance
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.repositories.ArtistRepository
import com.example.vinilos.ui.screens.AlbumDetailScreen
import com.example.vinilos.ui.screens.AlbumsScreen
import com.example.vinilos.ui.screens.ArtistsScreen
import com.example.vinilos.ui.screens.CollectorsScreen
import com.example.vinilos.viewmodels.AlbumDetailViewModel
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.AlbumDetailViewModelFactory
import com.example.vinilos.viewmodels.ArtistViewModel
import com.example.vinilos.viewmodels.ArtistViewModelFactory

sealed class Screen(val route: String) {
    object Albums : Screen("albums")
    object AlbumDetail : Screen("album_detail/{albumId}") {
        fun createRoute(albumId: Int) = "album_detail/$albumId"
    }
    object Artists : Screen("artists")
    object Collectors : Screen("collectors")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Albums.route
    ) {
        composable(Screen.Albums.route) {
            val viewModel: AlbumViewModel = viewModel()
            AlbumsScreen(
                viewModel = viewModel,
                onNavigateToTab = { tab ->
                    navigateToTab(navController, tab)
                },
                onAlbumClick = { album ->
                    navController.navigate(Screen.AlbumDetail.createRoute(album.id))
                },
                onAddAlbumClick = {
                    // Implementar navegación a la pantalla de agregar álbum
                }
            )
        }

        composable(
            route = Screen.AlbumDetail.route,
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: return@composable
            val albumDetailViewModel: AlbumDetailViewModel = viewModel(
                factory = AlbumDetailViewModelFactory(AlbumRepository())
            )

            AlbumDetailScreen(
                viewModel = albumDetailViewModel,
                albumId = albumId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Artists.route) {
            val viewModel: ArtistViewModel = viewModel(
                factory = ArtistViewModelFactory(ArtistRepository(RetrofitInstance.apiService))
            )
            ArtistsScreen(
                viewModel = viewModel,
                onNavigateToTab = { tab ->
                    navigateToTab(navController, tab)
                },
                onArtistClick = { artist ->
                }
            )
        }

        composable(Screen.Collectors.route) {
            CollectorsScreen(
                onNavigateToTab = { tab ->
                    navigateToTab(navController, tab)
                }
            )
        }
    }
}

private fun navigateToTab(navController: NavHostController, tab: NavigationTab) {
    val route = when (tab) {
        NavigationTab.ALBUMS -> Screen.Albums.route
        NavigationTab.ARTISTS -> Screen.Artists.route
        NavigationTab.COLLECTORS -> Screen.Collectors.route
    }

    navController.navigate(route) {
        // Pop hasta la ruta de inicio y evita múltiples copias de la misma destinación en la pila
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        // Evita múltiples copias de la misma destinación
        launchSingleTop = true
        // Restaura el estado cuando se vuelve a la destinación
        restoreState = true
    }
}
