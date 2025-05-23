package com.example.vinilos.models

data class Musician(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: List<Album> = emptyList(),
    val performerPrizes: List<PerformerPrize> = emptyList()
)
