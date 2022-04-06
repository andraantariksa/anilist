package id.shaderboi.anilist.core.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimeRepository {
    suspend fun addFavoriteAnime(animeId: Int)
    fun getFavoriteAnime(): Flow<PagingData<AnimeData>>
    suspend fun getFavoriteAnimeDetail(id: Int) : Flow<FavoriteAnimeEntity?>
    suspend fun deleteFavoriteAnime(id: Int)
}