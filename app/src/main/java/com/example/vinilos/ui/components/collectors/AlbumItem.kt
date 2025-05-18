package com.example.vinilos.ui.components.collectors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vinilos.repositories.AlbumRepository

@Composable
fun AlbumItem(
    albumId: Int,
    albumRepository: AlbumRepository
) {
    var coverUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(albumId) {
        try {
            albumRepository.getAlbumById(albumId).fold(
                onSuccess = { album ->
                    coverUrl = album.cover
                    isLoading = false
                },
                onFailure = { exception ->
                    error = exception.message
                    isLoading = false
                }
            )
        } catch (e: Exception) {
            error = e.message
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier.aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            coverUrl != null -> {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(coverUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Portada del álbum",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(1f)
                )
            }
            else -> {
                Box(
                    modifier = Modifier.aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Álbum",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
