package com.example.vinilos.network

import com.example.vinilos.models.Album
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): Response<List<Album>>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): Album
}
