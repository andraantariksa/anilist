package id.shaderboi.anilist.core.domain.data_source

import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimeDataSource {
    suspend fun getFavoriteAnime(): Flow<List<FavoriteAnimeJoinedEntity>>
    suspend fun getFavoriteAnimeDetail(id: Int): Flow<FavoriteAnimeEntity?>
}