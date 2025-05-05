package com.example.vinilos.network

import com.example.vinilos.models.Album
import com.example.vinilos.models.Band
import com.example.vinilos.models.Musician
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): Response<List<Album>>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): Album

    @GET("musicians")
    suspend fun getMusicians(): Response<List<Musician>>

    @GET("musicians/{id}")
    suspend fun getMusicianById(@Path("id") id: Int): Musician

    @GET("bands")
    suspend fun getBands(): Response<List<Band>>

    @GET("bands/{id}")
    suspend fun getBandById(@Path("id") id: Int): Band
}
