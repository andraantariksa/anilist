package id.shaderboi.anilist.core.domain.data_source

import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch

interface AnimeDataSource {
    suspend fun getAnimeDetail(id: Int): Anime
    suspend fun getAnimeList(): AnimeSearch
}