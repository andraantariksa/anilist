package id.shaderboi.anilist.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.shaderboi.anilist.core.data.data_source_store.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.repository.FavoriteAnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteAnimeRepositoryImpl @Inject constructor(
    database: AnilistDatabase
) : FavoriteAnimeRepository {
    private val favoriteAnimeDao = database.favoriteAnimeDao()

    override suspend fun addFavoriteAnime(animeId: Int) =
        favoriteAnimeDao.addFavoriteAnime(FavoriteAnimeEntity(animeId))

    override fun getFavoriteAnime(): Flow<PagingData<Anime>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                favoriteAnimeDao.getFavoriteAnime()
            }
        )
            .flow
            .map { favoriteAnimeJoinedEntityPagingData ->
                favoriteAnimeJoinedEntityPagingData.map { favoriteAnimeJoinedEntity ->
                    favoriteAnimeJoinedEntity.anime.anime.toAnime()
                }
            }
    }

    override suspend fun getFavoriteAnimeDetail(id: Int): Flow<FavoriteAnimeEntity?> =
        favoriteAnimeDao.getFavoriteAnimeDetail(id)

    override suspend fun deleteFavoriteAnime(id: Int) {
        favoriteAnimeDao.deleteFavoriteAnime(id)
    }
}