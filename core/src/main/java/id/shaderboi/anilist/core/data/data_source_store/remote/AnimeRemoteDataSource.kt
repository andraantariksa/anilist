package id.shaderboi.anilist.core.data.data_source_store.remote

import id.shaderboi.anilist.core.data.data_source_store.remote.network.JikanAPIService
import id.shaderboi.anilist.core.data.exception.NetworkErrorException
import id.shaderboi.anilist.core.domain.data_source.AnimeDataSource
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import javax.inject.Inject

class AnimeRemoteDataSource @Inject constructor(
    private val jikanAPIService: JikanAPIService
) : AnimeDataSource {
    override suspend fun getAnimeDetail(id: Int): AnimeData {
        val response = jikanAPIService.getAnimeDetail(id)
        if (response.isSuccessful) {
            return response.body()!!.animeData
        } else {
            throw NetworkErrorException(response.code(), response.errorBody().toString())
        }
    }

    override suspend fun getAnimeList(): List<AnimeData> {
        val response = jikanAPIService.getAnimeList()
        if (response.isSuccessful) {
            return response.body()!!.data
        } else {
            throw NetworkErrorException(response.code(), response.errorBody().toString())
        }
    }

    suspend fun searchAnime(query: String): List<AnimeData> {
        val response = jikanAPIService.searchAnime(query)
        if (response.isSuccessful) {
            return response.body()!!.data
        } else {
            throw NetworkErrorException(response.code(), response.errorBody().toString())
        }
    }
}
