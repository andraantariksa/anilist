package id.shaderboi.anilist.core.domain.repository

import androidx.paging.PagingData
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import id.shaderboi.anilist.core.domain.model.anime.Anime
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimeRepository {
    suspend fun addFavoriteAnime(animeId: Int)
    fun getFavoriteAnime(): Flow<PagingData<Anime>>
    suspend fun getFavoriteAnimeDetail(id: Int) : Flow<FavoriteAnimeEntity?>
    suspend fun deleteFavoriteAnime(id: Int)
}