package com.example.vinilos.models

import java.util.Date

data class AlbumDTO(
    val id: Int? = null,
    val name: String,
    val cover: String,
    val releaseDate: Date,
    val description: String,
    val genre: String,
    val recordLabel: String
)
