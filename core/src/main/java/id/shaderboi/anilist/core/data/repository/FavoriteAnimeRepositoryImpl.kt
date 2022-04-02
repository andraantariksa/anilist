package id.shaderboi.anilist.core.data.repository

import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import id.shaderboi.anilist.core.domain.data_source.FavoriteAnimeDataSource
import id.shaderboi.anilist.core.domain.data_store.FavoriteAnimeDataStore
import id.shaderboi.anilist.core.domain.repository.FavoriteAnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteAnimeRepositoryImpl @Inject constructor(
    private val dataSource: FavoriteAnimeDataSource,
    private val dataStore: FavoriteAnimeDataStore,
) : FavoriteAnimeRepository {
    override suspend fun addFavoriteAnime(animeId: Int) =
        dataStore.addFavoriteAnime(animeId)

    override suspend fun getFavoriteAnime(): Flow<List<FavoriteAnimeJoinedEntity>> =
        dataSource.getFavoriteAnime()

    override suspend fun getFavoriteAnimeDetail(id: Int): Flow<FavoriteAnimeEntity?> =
        dataSource.getFavoriteAnimeDetail(id)

    override suspend fun deleteFavoriteAnime(id: Int) {
        dataStore.deleteFavoriteAnime(id)
    }
}