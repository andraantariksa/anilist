package id.shaderboi.anilist.core.domain.repository

import id.shaderboi.anilist.core.domain.model.anime.AnimeSingle
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData

interface AnimeRepository {
    suspend fun getAnimeDetail(id: Int): Result<AnimeData>
    suspend fun getAnimeList(): Result<List<AnimeData>>
    suspend fun searchAnime(query: String): Result<List<AnimeData>>
}