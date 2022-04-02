package id.shaderboi.anilist.core.data.data_source_store.remote.network

import id.shaderboi.anilist.core.domain.model.anime.AnimeSingle
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// order_by field enum: mal_id, title, type, rating, start_date, end_date, episodes, score, scored_by, rank, popularity, members, favorites

interface JikanAPIService {
    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): Response<AnimeSingle>

    @GET("anime")
    suspend fun getAnimeList(@Query("order_by") order_by: String = "start_date"): Response<AnimeSearch>

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("order_by") order_by: String? = null
    ): Response<AnimeSearch>

}