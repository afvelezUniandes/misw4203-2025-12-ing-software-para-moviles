package com.example.vinilos.repositories

import com.example.vinilos.models.Collector
import com.example.vinilos.network.adapter.CollectorServiceAdapter
import com.example.vinilos.network.adapter.RetrofitCollectorServiceAdapter
import com.example.vinilos.network.cache.CacheManager

class CollectorRepository(
    private val serviceAdapter: CollectorServiceAdapter = RetrofitCollectorServiceAdapter()
) {
    private val cacheManager = CacheManager.getInstance()

    suspend fun getCollectors(): List<Collector> {
        cacheManager.getCollectors()?.let { collectors ->
            return collectors
        }

        val result = serviceAdapter.getCollectors()

        return result.getOrElse {
            throw it
        }.also { collectors ->
            cacheManager.addCollectors(collectors)
        }
    }
}
