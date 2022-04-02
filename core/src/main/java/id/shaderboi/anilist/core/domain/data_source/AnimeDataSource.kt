package id.shaderboi.anilist.core.domain.data_source

import id.shaderboi.anilist.core.domain.model.anime.AnimeSingle
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData

interface AnimeDataSource {
    suspend fun getAnimeDetail(id: Int): AnimeData
    suspend fun getAnimeList(): List<AnimeData>
}