package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vinilos.ui.components.albums.AlbumsGrid
import com.example.vinilos.ui.components.common.ErrorScreen
import com.example.vinilos.ui.components.common.LoadingScreen
import com.example.vinilos.ui.theme.CoveDarkBlue
import com.example.vinilos.ui.theme.CoveOrange
import com.example.vinilos.ui.theme.CoveCream
import com.example.vinilos.viewmodels.AlbumViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(viewModel: AlbumViewModel) {
    val albums by viewModel.albums.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val selectedTab = 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "VinylWave",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CoveDarkBlue,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            // Usando los colores definidos en tu tema
            val selectedColor = CoveOrange    // Color naranja/ámbar para seleccionado
            val unselectedColor = CoveDarkBlue
            var indicadorColor = CoveCream

            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface // Fondo de la barra de navegación
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { /* Acción al hacer clic en Álbumes */ },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Álbumes",
                            tint = if (selectedTab == 0) selectedColor else unselectedColor
                        )
                    },
                    label = {
                        Text(
                            "Álbumes",
                            color = if (selectedTab == 0) selectedColor else unselectedColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        selectedTextColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        unselectedTextColor = unselectedColor,
                        indicatorColor = indicadorColor
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { /* Acción al hacer clic en Artistas */ },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Artistas",
                            tint = if (selectedTab == 1) selectedColor else unselectedColor
                        )
                    },
                    label = {
                        Text(
                            "Artistas",
                            color = if (selectedTab == 1) selectedColor else unselectedColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        selectedTextColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        unselectedTextColor = unselectedColor,
                        indicatorColor = indicadorColor
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { /* Acción al hacer clic en Coleccionistas */ },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Coleccionistas",
                            tint = if (selectedTab == 2) selectedColor else unselectedColor
                        )
                    },
                    label = {
                        Text(
                            "Coleccionistas",
                            color = if (selectedTab == 2) selectedColor else unselectedColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        selectedTextColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        unselectedTextColor = unselectedColor,
                        indicatorColor = indicadorColor
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Acción para agregar nuevo álbum */ },
                containerColor = CoveOrange
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar álbum"
                )
            }
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
                    viewModel.loadAlbums()
                }
                else -> AlbumsGrid(
                    albums = albums,
                    onAlbumClick = { albumId ->
                        // Navegación al detalle del álbum
                    }
                )
            }
        }
    }
}

