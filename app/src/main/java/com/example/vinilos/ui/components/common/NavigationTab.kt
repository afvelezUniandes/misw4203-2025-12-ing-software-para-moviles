package com.example.vinilos.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vinilos.ui.theme.CoveCream
import com.example.vinilos.ui.theme.CoveDarkBlue
import com.example.vinilos.ui.theme.CoveOrange
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vinilos.R

enum class NavigationTab {
    ALBUMS, ARTISTS, COLLECTORS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    selectedTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    onAddClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "VinylWave",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        CircleIcon()
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CoveDarkBlue,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )

        },
        bottomBar = {
            val selectedColor = CoveOrange
            val unselectedColor = CoveDarkBlue
            val indicatorColor = CoveCream

            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.ALBUMS,
                    onClick = { onTabSelected(NavigationTab.ALBUMS) },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.album_icon),
                            contentDescription = "Álbumes",
                            tint = if (selectedTab == NavigationTab.ALBUMS) selectedColor else unselectedColor,
                            modifier = Modifier.size(35.dp)
                        )
                    },
                    label = {
                        Text(
                            "Álbumes",
                            color = if (selectedTab == NavigationTab.ALBUMS) selectedColor else unselectedColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        selectedTextColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        unselectedTextColor = unselectedColor,
                        indicatorColor = indicatorColor
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == NavigationTab.ARTISTS,
                    onClick = { onTabSelected(NavigationTab.ARTISTS) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Artistas",
                            tint = if (selectedTab == NavigationTab.ARTISTS) selectedColor else unselectedColor,
                            modifier = Modifier.size(35.dp)
                        )
                    },
                    label = {
                        Text(
                            "Artistas",
                            color = if (selectedTab == NavigationTab.ARTISTS) selectedColor else unselectedColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        selectedTextColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        unselectedTextColor = unselectedColor,
                        indicatorColor = indicatorColor
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == NavigationTab.COLLECTORS,
                    onClick = { onTabSelected(NavigationTab.COLLECTORS) },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.people_icon),
                            contentDescription = "Coleccionistas",
                            tint = if (selectedTab == NavigationTab.COLLECTORS) selectedColor else unselectedColor,
                            modifier = Modifier.size(35.dp)
                        )
                    },
                    label = {
                        Text(
                            "Coleccionistas",
                            color = if (selectedTab == NavigationTab.COLLECTORS) selectedColor else unselectedColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        selectedTextColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        unselectedTextColor = unselectedColor,
                        indicatorColor = indicatorColor
                    )
                )
            }
        },
        floatingActionButton = {
            if (selectedTab == NavigationTab.ALBUMS) {
                FloatingActionButton(
                    onClick = onAddClick,
                    containerColor = CoveOrange,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar álbum"
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            content()
        }
    }
}
