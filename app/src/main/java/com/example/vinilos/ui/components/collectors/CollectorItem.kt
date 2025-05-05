package com.example.vinilos.ui.components.collectors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.vinilos.models.Collector
import com.example.vinilos.ui.theme.CoveDarkBlue
import com.example.vinilos.ui.theme.CoveLightBlue
import com.example.vinilos.ui.theme.CoveOrange

@Composable
fun CollectorItem(
    collector: Collector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
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
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(getCollectorColor(collector.id)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getCollectorInitials(collector.name),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = collector.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CoveDarkBlue
                )

                Spacer(modifier = Modifier.height(2.dp))

                val albumCount = collector.collectorAlbums.size
                val albumText = if (albumCount == 1) "1 álbum" else "$albumCount álbumes"

                val artistCount = collector.favoritePerformers.size
                val artistText = if (artistCount == 1) "1 artista favorito" else "$artistCount artistas favoritos"

                Text(
                    text = "$albumText · $artistText",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CoveDarkBlue
                )
            }
        }
    }
}

fun getCollectorInitials(name: String): String {
    return name.split(" ")
        .take(2)
        .joinToString("") { it.first().toString() }
        .uppercase()
}

fun getCollectorColor(id: Int): Color {
    return when (id % 2) {
        0 -> CoveLightBlue
        else -> CoveOrange
    }
}
