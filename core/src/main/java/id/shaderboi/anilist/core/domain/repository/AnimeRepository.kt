package id.shaderboi.anilist.core.domain.repository

import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch

interface AnimeRepository {
    suspend fun getAnimeDetail(id: Int): Result<Anime>
    suspend fun getAnimeList(): Result<AnimeSearch>
}