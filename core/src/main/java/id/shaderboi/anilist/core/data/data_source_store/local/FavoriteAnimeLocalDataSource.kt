package id.shaderboi.anilist.core.data.data_source_store.local

import id.shaderboi.anilist.core.data.data_source_store.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import id.shaderboi.anilist.core.domain.data_source.FavoriteAnimeDataSource
import id.shaderboi.anilist.core.domain.data_store.FavoriteAnimeDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteAnimeLocalDataSource @Inject constructor(
    anilistDatabase: AnilistDatabase
) : FavoriteAnimeDataSource {
    val favoriteDao = anilistDatabase.favoriteDao()

    override suspend fun getFavoriteAnime(): Flow<List<FavoriteAnimeJoinedEntity>> {
        return favoriteDao.getFavoriteAnime()
    }

    override suspend fun getFavoriteAnimeDetail(id: Int): Flow<FavoriteAnimeEntity?> {
        return favoriteDao.getFavoriteAnimeDetail(id)
    }
}