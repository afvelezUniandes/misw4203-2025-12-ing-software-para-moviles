package com.example.vinilos.ui.components.collectors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vinilos.models.Collector
import com.example.vinilos.models.Performer
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.ui.theme.CoveDarkBlue
import com.example.vinilos.ui.theme.CoveLightBlue
import com.example.vinilos.ui.theme.CoveOrange

@Composable
fun CollectorDetailContent(collector: Collector, albumRepository: AlbumRepository) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        CollectorHeader(collector)

        InfoSection(collector)

        FavoriteArtistsSection(collector.favoritePerformers)

        AlbumsSection(collector, albumRepository)

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun CollectorHeader(collector: Collector) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0277BD))
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getCollectorInitials(collector.name),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0277BD)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = collector.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun InfoSection(collector: Collector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .offset(y = (-16).dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F2)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Información",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = CoveDarkBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            val albumCount = collector.collectorAlbums.size
            val artistCount = collector.favoritePerformers.size

            val albumText = if (albumCount == 1) "álbum" else "álbumes"
            val artistText = if (artistCount == 1) "artista favorito" else "artistas favoritos"

            Text(
                text = "$albumCount $albumText • $artistCount $artistText",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            if (collector.email.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Email: ${collector.email}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }

            if (collector.telephone.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Teléfono: ${collector.telephone}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun FavoriteArtistsSection(artists: List<Performer>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Artistas favoritos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = CoveDarkBlue,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (artists.isEmpty()) {
            Text(
                text = "No hay artistas favoritos",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(artists) { artist ->
                    ArtistCircle(artist)
                }

                if (artists.size > 5) {
                    item {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(CoveLightBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+${artists.size - 5}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistCircle(artist: Performer) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(60.dp)
    ) {
        if (artist.image.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artist.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Foto de ${artist.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(getArtistColor(artist.id)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getInitials(artist.name),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun AlbumsSection(collector: Collector, albumRepository: AlbumRepository) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Álbumes en colección",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = CoveDarkBlue,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (collector.collectorAlbums.isEmpty()) {
            Text(
                text = "No hay álbumes en la colección",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(120.dp)
            ) {
                items(collector.collectorAlbums.take(3)) { collectorAlbum ->
                    AlbumItem(collectorAlbum.id, albumRepository)
                }
            }

            if (collector.collectorAlbums.size > 3) {
                TextButton(
                    onClick = { /* Navegar a la lista completa */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Ver todos (${collector.collectorAlbums.size})",
                        color = CoveDarkBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

fun getInitials(name: String): String {
    return name.split(" ")
        .take(2)
        .joinToString("") { it.first().toString() }
        .uppercase()
}

fun getArtistColor(id: Int): Color {
    return when (id % 3) {
        0 -> CoveLightBlue
        1 -> CoveOrange
        else -> Color(0xFFE57373)
    }
}
