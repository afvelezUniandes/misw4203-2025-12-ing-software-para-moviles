package com.example.vinilos.network.adapter

import com.example.vinilos.models.Album
import com.example.vinilos.models.AlbumDTO

interface AlbumServiceAdapter {
    suspend fun getAlbums(): Result<List<Album>>
    suspend fun getAlbumById(id: Int): Result<Album>
    suspend fun createAlbum(albumDTO: AlbumDTO): Result<Album>
}