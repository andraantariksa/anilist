package id.shaderboi.anilist.core.data.remote.network

import id.shaderboi.anilist.core.di.JikanResponse
import id.shaderboi.anilist.core.domain.model.anime.Anime
import retrofit2.http.GET

interface JikanAPIService {
    @GET("anime/:id")
    suspend fun getAnimeDetail(id: Int): JikanResponse<Anime>

    @GET("anime")
    suspend fun searchAnime(query: String): JikanResponse<Anime>

}