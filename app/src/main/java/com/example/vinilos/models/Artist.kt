package com.example.vinilos.models

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val date: String,
    val dateType: String,
    val type: String,
    val albums: List<Album> = emptyList()
)