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
    suspend fun getAnime(@Path("id") id: Int): AnimeSingle

    @GET("anime")
    suspend fun getAnimeList(
        @Query("page") page: Int,
        @Query("order_by") order_by: String = "mal_id"
    ): AnimeSearch

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("order_by") order_by: String? = null
    ): AnimeSearch

}