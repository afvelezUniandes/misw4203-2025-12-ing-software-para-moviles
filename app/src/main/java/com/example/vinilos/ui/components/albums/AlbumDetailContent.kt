package com.example.vinilos.ui.components.albums

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vinilos.models.Album
import com.example.vinilos.models.Track
import com.example.vinilos.ui.theme.CoveOrange
import java.util.Locale

@Composable
fun AlbumDetailContent(
    album: Album,
    onAddTrackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            AlbumHeader(album)
        }

        item {
            InformationSection(album)
        }

        item {
            TracksHeader(onAddTrackClick)
        }

        items(album.tracks.take(5)) { track ->
            TrackItem(track, album.tracks.indexOf(track) + 1)
            if (album.tracks.indexOf(track) < album.tracks.take(5).size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }
        }

        if (album.tracks.size > 5) {
            item {
                ViewAllTracksButton(album.tracks.size)
            }
        }
    }
}

@Composable
fun AlbumHeader(album: Album) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(album.cover)
                .crossfade(true)
                .build(),
            contentDescription = "Portada de ${album.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 0f,
                        endY = 900f
                    )
                )
        )

        Text(
            text = album.name,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}

@Composable
fun InformationSection(album: Album) {
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
                color = Color(0xFF0277BD),
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = album.genre,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF0277BD),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = " • ${album.tracks.size} tracks • ${calculateTotalDuration(album.tracks)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun TracksHeader(onAddTrackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Tracks",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF0277BD),
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = onAddTrackClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = CoveOrange
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar track",
                    tint = Color.White
                )
                Text(
                    text = "Agregar",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun TrackItem(track: Track, index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$index.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0277BD),
            modifier = Modifier.width(32.dp)
        )
        Text(
            text = track.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = track.duration,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF0277BD),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ViewAllTracksButton(totalTracks: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            onClick = { }
        ) {
            Text(
                text = "Ver todos ($totalTracks)",
                color = Color(0xFF0277BD),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

private fun calculateTotalDuration(tracks: List<Track>): String {
    val totalMinutes = tracks.size * 3
    return "${totalMinutes}:${String.format(Locale.getDefault(), "%02d", 23)} min"
}
