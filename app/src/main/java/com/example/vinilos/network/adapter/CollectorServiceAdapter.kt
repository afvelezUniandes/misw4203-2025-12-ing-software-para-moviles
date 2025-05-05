package com.example.vinilos.network.adapter

import com.example.vinilos.models.Collector

interface CollectorServiceAdapter {
    suspend fun getCollectors(): Result<List<Collector>>
    suspend fun getCollectorById(id: Int): Result<Collector>
}