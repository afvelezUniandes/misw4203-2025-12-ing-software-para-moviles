package com.example.vinilos.network.cache

import androidx.collection.ArrayMap
import com.example.vinilos.models.Album
import com.example.vinilos.models.Band
import com.example.vinilos.models.Collector
import com.example.vinilos.models.Musician

class CacheManager private constructor() {
    companion object {
        @Volatile
        private var instance: CacheManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CacheManager().also { instance = it }
            }

        private const val CACHE_VALIDITY_MS = 5 * 60 * 1000
    }

    private val albumsCache = ArrayMap<String, CacheEntry<List<Album>>>()
    private val albumByIdCache = ArrayMap<Int, CacheEntry<Album>>()

    private val musiciansCache = ArrayMap<String, CacheEntry<List<Musician>>>()
    private val bandsCache = ArrayMap<String, CacheEntry<List<Band>>>()

    private val collectorsCache = ArrayMap<String, CacheEntry<List<Collector>>>()
    private val collectorByIdCache = ArrayMap<Int, CacheEntry<Collector>>()

    private data class CacheEntry<T>(
        val data: T,
        val timestamp: Long = System.currentTimeMillis()
    ) {
        fun isValid(): Boolean {
            return System.currentTimeMillis() - timestamp < CACHE_VALIDITY_MS
        }
    }

    fun getAlbums(): List<Album>? {
        val cacheKey = "all_albums"
        val entry = albumsCache[cacheKey]
        return if (entry != null && entry.isValid()) entry.data else null
    }

    fun addAlbums(albums: List<Album>) {
        val cacheKey = "all_albums"
        albumsCache[cacheKey] = CacheEntry(albums)
    }

    fun getAlbumById(id: Int): Album? {
        val entry = albumByIdCache[id]
        return if (entry != null && entry.isValid()) entry.data else null
    }

    fun addAlbum(id: Int, album: Album) {
        albumByIdCache[id] = CacheEntry(album)
    }

    fun getMusicians(): List<Musician>? {
        val cacheKey = "all_musicians"
        val entry = musiciansCache[cacheKey]
        return if (entry != null && entry.isValid()) entry.data else null
    }

    fun addMusicians(musicians: List<Musician>) {
        val cacheKey = "all_musicians"
        musiciansCache[cacheKey] = CacheEntry(musicians)
    }

    fun getBands(): List<Band>? {
        val cacheKey = "all_bands"
        val entry = bandsCache[cacheKey]
        return if (entry != null && entry.isValid()) entry.data else null
    }

    fun addBands(bands: List<Band>) {
        val cacheKey = "all_bands"
        bandsCache[cacheKey] = CacheEntry(bands)
    }

    fun getCollectors(): List<Collector>? {
        val cacheKey = "all_collectors"
        val entry = collectorsCache[cacheKey]
        return if (entry != null && entry.isValid()) entry.data else null
    }

    fun getCollectorById(id: Int): Collector? {
        val entry = collectorByIdCache[id]
        return if (entry != null && entry.isValid()) entry.data else null
    }

    fun addCollector(id: Int, collector: Collector) {
        collectorByIdCache[id] = CacheEntry(collector)
    }

    fun addCollectors(collectors: List<Collector>) {
        val cacheKey = "all_collectors"
        collectorsCache[cacheKey] = CacheEntry(collectors)
    }
}
