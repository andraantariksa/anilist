package id.shaderboi.anilist.core.data.data_source.remote.network

import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import retrofit2.Response
import retrofit2.http.GET

interface JikanAPIService {
    @GET("anime/:id")
    suspend fun getAnimeDetail(id: Int): Response<Anime>

    @GET("anime")
    suspend fun getAnimeList(): Response<AnimeSearch>

    @GET("anime")
    suspend fun searchAnime(query: String): Response<Anime>

}