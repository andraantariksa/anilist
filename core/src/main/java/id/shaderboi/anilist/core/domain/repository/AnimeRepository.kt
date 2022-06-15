package id.shaderboi.anilist.core.domain.repository

import androidx.paging.PagingData
import id.shaderboi.anilist.core.domain.model.anime.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getAnimeDetail(id: Int): Result<Anime>
    fun getAnimeList(): Flow<PagingData<Anime>>
    fun searchAnime(query: String): Flow<PagingData<Anime>>
}