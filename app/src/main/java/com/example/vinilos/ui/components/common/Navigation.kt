package com.example.vinilos.ui.components.common

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.repositories.ArtistRepository
import com.example.vinilos.repositories.CollectorRepository
import com.example.vinilos.ui.screens.AlbumDetailScreen
import com.example.vinilos.ui.screens.AlbumsScreen
import com.example.vinilos.ui.screens.ArtistDetailScreen
import com.example.vinilos.ui.screens.ArtistsScreen
import com.example.vinilos.ui.screens.CollectorsScreen
import com.example.vinilos.viewmodels.AlbumDetailViewModel
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.AlbumDetailViewModelFactory
import com.example.vinilos.viewmodels.ArtistDetailViewModel
import com.example.vinilos.viewmodels.ArtistDetailViewModelFactory
import com.example.vinilos.viewmodels.ArtistViewModel
import com.example.vinilos.viewmodels.ArtistViewModelFactory
import com.example.vinilos.viewmodels.CollectorViewModel
import com.example.vinilos.viewmodels.CollectorViewModelFactory

sealed class Screen(val route: String) {
    object Albums : Screen("albums")
    object AlbumDetail : Screen("album_detail/{albumId}") {
        fun createRoute(albumId: Int) = "album_detail/$albumId"
    }
    object Artists : Screen("artists")
    object ArtistDetail : Screen("artist_detail/{artistId}") {
        fun createRoute(artistId: Int) = "artist_detail/$artistId"
    }
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
                factory = ArtistViewModelFactory(ArtistRepository())
            )
            ArtistsScreen(
                viewModel = viewModel,
                onNavigateToTab = { tab ->
                    navigateToTab(navController, tab)
                },
                onArtistClick = { artist ->
                    navController.navigate(Screen.ArtistDetail.createRoute(artist.id))
                }
            )
        }

        composable(
            route = Screen.ArtistDetail.route,
            arguments = listOf(navArgument("artistId") { type = NavType.IntType })
        ) { backStackEntry ->
            val artistId = backStackEntry.arguments?.getInt("artistId") ?: return@composable
            val artistDetailViewModel: ArtistDetailViewModel = viewModel(
                factory = ArtistDetailViewModelFactory(ArtistRepository())
            )

            ArtistDetailScreen(
                viewModel = artistDetailViewModel,
                artistId = artistId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Collectors.route) {
            val viewModel: CollectorViewModel = viewModel(
                factory = CollectorViewModelFactory(CollectorRepository())
            )

            CollectorsScreen(
                viewModel = viewModel,
                onNavigateToTab = { tab ->
                    navigateToTab(navController, tab)
                },
                onCollectorClick = { collector ->
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
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
