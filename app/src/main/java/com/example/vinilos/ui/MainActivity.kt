package com.example.vinilos.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vinilos.ui.screens.AlbumsScreen
import com.example.vinilos.ui.theme.VinylWaveTheme
import com.example.vinilos.viewmodels.AlbumViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VinylWaveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val albumViewModel: AlbumViewModel = viewModel()
                    AlbumsScreen(albumViewModel)
                }
            }
        }
    }
}
