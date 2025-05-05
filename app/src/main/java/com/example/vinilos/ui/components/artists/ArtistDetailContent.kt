package com.example.vinilos.ui.components.artists

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vinilos.models.Album
import com.example.vinilos.models.Artist
import com.example.vinilos.models.PerformerPrize
import com.example.vinilos.ui.theme.CoveDarkBlue
import com.example.vinilos.ui.theme.CoveLightBlue

@Composable
fun ArtistDetailContent(artist: Artist, onAlbumClick: (Album) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            ArtistHeader(artist)
        }

        item {
            ArtistInformation(artist)
        }

        item {
            AlbumsSection(artist.albums, onAlbumClick)
        }

        item {
            PrizesSection(prizes = artist.performerPrizes)
        }
    }
}

@Composable
fun AlbumsSection(albums: List<Album>, onAlbumClick: (Album) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Álbumes",
            style = MaterialTheme.typography.titleLarge,
            color = CoveDarkBlue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                start = 4.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 4.dp
            ),
        ) {
            items(albums) { album ->
                AlbumItem(album = album, onClick = { onAlbumClick(album) })
            }
        }
    }
}

@Composable
fun AlbumItem(album: Album, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            if (album.cover.isNotEmpty()) {
                AsyncImage(
                    model = album.cover,
                    contentDescription = "Portada de ${album.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = album.name.take(2).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = album.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ArtistHeader(artist: Artist) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(CoveDarkBlue),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(CoveDarkBlue, CoveLightBlue),
                        startY = 0f,
                        endY = 900f
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                        .size(150.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = artist.name.take(2).uppercase(),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = artist.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ArtistInformation(artist: Artist) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color(0xFFF9F5E9),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Información",
                style = MaterialTheme.typography.titleLarge,
                color = CoveDarkBlue,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            val dateLabel = when (artist.dateType.lowercase()) {
                "birth" -> "Fecha de nacimiento"
                else -> "Fecha de conformación"
            }

            val formattedDate = formatDate(artist.date)

            Row {
                Text(
                    text = "$dateLabel: ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CoveDarkBlue,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = artist.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${artist.albums.size} álbumes",
                style = MaterialTheme.typography.bodyLarge,
                color = CoveDarkBlue,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PrizesSection(prizes: List<PerformerPrize>) {
    if (prizes.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFFAF7F0), shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Premios",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF2E74B5),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            prizes.forEach { prize ->
                val formattedDate = formatDate(prize.premiationDate)

                Text(
                    text = "Fecha de premiación: $formattedDate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF2E74B5),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

private fun formatDate(dateString: String): String {
    return try {
        if (dateString.contains("T")) {
            dateString.split("T")[0]
        } else {
            dateString
        }
    } catch (e: Exception) {
        dateString
    }
}
