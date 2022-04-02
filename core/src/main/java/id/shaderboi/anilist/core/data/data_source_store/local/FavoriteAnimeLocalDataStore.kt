package id.shaderboi.anilist.core.data.data_source_store.local

import id.shaderboi.anilist.core.data.data_source_store.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import id.shaderboi.anilist.core.domain.data_source.FavoriteAnimeDataSource
import id.shaderboi.anilist.core.domain.data_store.FavoriteAnimeDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteAnimeLocalDataStore @Inject constructor(
    anilistDatabase: AnilistDatabase
) : FavoriteAnimeDataStore {
    val favoriteDao = anilistDatabase.favoriteDao()

    override suspend fun addFavoriteAnime(animeId: Int) {
        return favoriteDao.insertFavoriteAnime(
            FavoriteAnimeEntity(animeId)
        )
    }
}