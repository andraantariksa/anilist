package id.shaderboi.anilist.core.domain.repository

import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimeRepository {
    suspend fun addFavoriteAnime(animeId: Int)
    suspend fun getFavoriteAnime() : Flow<List<FavoriteAnimeJoinedEntity>>
}