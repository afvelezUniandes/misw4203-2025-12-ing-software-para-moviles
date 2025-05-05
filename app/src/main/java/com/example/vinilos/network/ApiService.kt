package com.example.vinilos.network

import com.example.vinilos.models.*
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

    @GET("collectors")
    suspend fun getCollectors(): Response<List<Collector>>

    @GET("collectors/{id}")
    suspend fun getCollectorById(@Path("id") id: Int): Collector
}
