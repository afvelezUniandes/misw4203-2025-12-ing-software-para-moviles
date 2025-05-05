package com.example.vinilos.models

data class Band(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val creationDate: String,
    val albums: List<Album> = emptyList(),
    val musicians: List<Musician> = emptyList(),
    val performerPrizes: List<PerformerPrize> = emptyList()
)
