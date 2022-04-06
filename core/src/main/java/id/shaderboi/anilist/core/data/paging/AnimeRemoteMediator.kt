package id.shaderboi.anilist.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.shaderboi.anilist.core.data.data_source_store.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeRemoteKeyEntity
import id.shaderboi.anilist.core.data.data_source_store.remote.network.JIKAN_STARTING_PAGE
import id.shaderboi.anilist.core.data.data_source_store.remote.network.JikanAPIService
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val jikanAPIService: JikanAPIService,
    private val database: AnilistDatabase
) : RemoteMediator<Int, AnimeEntity>() {

    private val animeRemoteKeyDao = database.animeRemoteKeyDao()
    private val animeDao = database.animeDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: JIKAN_STARTING_PAGE
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = jikanAPIService.getAnimeList(currentPage).data

            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == JIKAN_STARTING_PAGE) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                animeRemoteKeyDao.addRemoteKeys(
                    response.map { animeData ->
                        AnimeRemoteKeyEntity(animeData.malId, prevPage, nextPage)
                    }
                )
                animeDao.addAllAnime(
                    response.map { animeData ->
                        AnimeEntity(animeData, animeData.malId)
                    }
                )
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ex: Exception) {
            MediatorResult.Error(ex)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, AnimeEntity>
    ): AnimeRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                animeRemoteKeyDao.getRemoteKey(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, AnimeEntity>
    ): AnimeRemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                animeRemoteKeyDao.getRemoteKey(unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, AnimeEntity>
    ): AnimeRemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                animeRemoteKeyDao.getRemoteKey(unsplashImage.id)
            }
    }
}