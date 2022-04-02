package id.shaderboi.anilist.core.data.repository

import id.shaderboi.anilist.core.data.data_source_store.local.AnimeLocalDataSource
import id.shaderboi.anilist.core.data.data_source_store.local.AnimeLocalDataStore
import id.shaderboi.anilist.core.data.data_source_store.remote.AnimeRemoteDataSource
import id.shaderboi.anilist.core.data.exception.NoDataAvailableException
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.repository.AnimeRepository

class AnimeRepositoryImpl(
    private val remoteDataSource: AnimeRemoteDataSource,
    private val localDataSource: AnimeLocalDataSource,
    private val localDataStore: AnimeLocalDataStore,
) : AnimeRepository {
    override suspend fun getAnimeDetail(id: Int): Result<AnimeData> {
        val result = runCatching {
            val anime = remoteDataSource.getAnimeDetail(id)
            localDataStore.insertAnime(anime)
            anime
        }

        try {
            return Result.success(localDataSource.getAnimeDetail(id))
        } catch (ex: NoDataAvailableException) {
            // Ignore
        }
        return result
    }

    override suspend fun getAnimeList(): Result<List<AnimeData>> {
        val result = runCatching {
            val animes = remoteDataSource.getAnimeList()
            localDataStore.insertAnimes(animes)
            animes
        }

        try {
            return Result.success(localDataSource.getAnimeList())
        } catch (ex: NoDataAvailableException) {
            // Ignore
        }
        return result
    }

    override suspend fun searchAnime(query: String): Result<List<AnimeData>> {
        return runCatching {
            remoteDataSource.searchAnime(query)
        }
    }
}