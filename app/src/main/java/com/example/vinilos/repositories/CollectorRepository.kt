package com.example.vinilos.repositories

import com.example.vinilos.models.Collector
import com.example.vinilos.network.adapter.CollectorServiceAdapter
import com.example.vinilos.network.adapter.RetrofitCollectorServiceAdapter

class CollectorRepository(
    private val serviceAdapter: CollectorServiceAdapter = RetrofitCollectorServiceAdapter()
) {
    suspend fun getCollectors(): List<Collector> {
        val result = serviceAdapter.getCollectors()
        return result.getOrElse { throw it }
    }
}
