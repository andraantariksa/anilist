package id.shaderboi.anilist.core.domain.repository

import androidx.paging.PagingData
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeEntity
import id.shaderboi.anilist.core.domain.model.anime.AnimeSingle
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getAnimeDetail(id: Int): Result<AnimeData>
    fun getAnimeList(): Flow<PagingData<AnimeData>>
    fun searchAnime(query: String): Flow<PagingData<AnimeData>>
}