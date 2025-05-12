package com.example.vinilos.ui.components.artists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vinilos.models.Artist
import com.example.vinilos.ui.theme.CoveDarkBlue
import com.example.vinilos.ui.theme.CoveLightBlue
import com.example.vinilos.ui.theme.CoveOrange
import androidx.compose.ui.platform.testTag

@Composable
fun ArtistCard(
    artist: Artist,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("artist_item")
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(getArtistColor(artist.id)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getArtistInitials(artist.name),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = artist.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CoveDarkBlue
                )

                val genre = if (artist.albums.isNotEmpty()) {
                    artist.albums.first().genre
                } else {
                    "Género desconocido"
                }

                val albumCount = artist.albums.size
                val albumText = if (albumCount == 1) "1 álbum" else "$albumCount álbumes"

                Text(
                    text = "$genre · $albumText",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CoveDarkBlue
                )
            }
        }
    }
}

fun getArtistInitials(name: String): String {
    return name.split(" ")
        .take(2).joinToString("") { it.first().uppercase() }
}

fun getArtistColor(id: Int): Color {
    return when (id % 4) {
        0 -> CoveLightBlue
        1 -> CoveDarkBlue
        2 -> CoveOrange
        3 -> CoveLightBlue.copy(alpha = 0.7f)
        else -> CoveLightBlue
    }
}
