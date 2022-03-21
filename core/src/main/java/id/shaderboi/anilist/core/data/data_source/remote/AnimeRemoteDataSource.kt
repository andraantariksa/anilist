package id.shaderboi.anilist.core.data.data_source.remote

import id.shaderboi.anilist.core.data.data_source.remote.network.JikanAPIService
import id.shaderboi.anilist.core.data.exception.NetworkErrorException
import id.shaderboi.anilist.core.domain.data_source.AnimeDataSource
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import javax.inject.Inject

class AnimeRemoteDataSource @Inject constructor(
    private val jikanAPIService: JikanAPIService
) : AnimeDataSource {
    override suspend fun getAnimeDetail(id: Int): Anime {
        val response = jikanAPIService.getAnimeDetail(id)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw NetworkErrorException(response.code(), response.errorBody().toString())
        }
    }

    override suspend fun getAnimeList(): AnimeSearch {
        val response = jikanAPIService.getAnimeList()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw NetworkErrorException(response.code(), response.errorBody().toString())
        }
    }
}
