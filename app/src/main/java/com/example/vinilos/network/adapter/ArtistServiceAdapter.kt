package com.example.vinilos.network.adapter

import com.example.vinilos.models.*

interface ArtistServiceAdapter {
    suspend fun getMusicians(): Result<List<Musician>>
    suspend fun getBands(): Result<List<Band>>
    suspend fun getMusicianById(id: Int): Result<Musician>
    suspend fun getBandById(id: Int): Result<Band>
}
