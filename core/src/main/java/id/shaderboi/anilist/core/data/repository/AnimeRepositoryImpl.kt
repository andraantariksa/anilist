package id.shaderboi.anilist.core.data.repository

import androidx.paging.*
import id.shaderboi.anilist.core.data.data_source_store.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.remote.network.JikanAPIService
import id.shaderboi.anilist.core.data.paging.AnimeRemoteMediator
import id.shaderboi.anilist.core.data.paging.SearchAnimePagingSource
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val jikanAPIService: JikanAPIService,
    private val anilistDatabase: AnilistDatabase
) : AnimeRepository {
    private val animeDao = anilistDatabase.animeDao()
    private val animeRemoteKeyDao = anilistDatabase.animeRemoteKeyDao()

    override suspend fun getAnimeDetail(id: Int): Result<Anime> {
        val result = runCatching {
            val anime = jikanAPIService.getAnime(id).animeData
            animeDao.addAnime(AnimeEntity(anime, anime.malId))
            anime
        }

        if (result.isSuccess) {
            return Result.success(result.getOrNull()!!.toAnime())
        }

        return runCatching {
            animeDao.getAnime(id)!!.anime.toAnime()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimeList(): Flow<PagingData<Anime>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = AnimeRemoteMediator(
                jikanAPIService,
                anilistDatabase
            ),
            pagingSourceFactory = {
                animeDao.getAnimeList()
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { animeEntity ->
                    animeEntity.anime.toAnime()
                }
            }
    }

    override fun searchAnime(query: String): Flow<PagingData<Anime>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchAnimePagingSource(jikanAPIService, query)
            }
        ).flow
    }
}