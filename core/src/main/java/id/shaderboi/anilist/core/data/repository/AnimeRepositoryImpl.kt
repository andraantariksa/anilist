package id.shaderboi.anilist.core.data.repository

import id.shaderboi.anilist.core.data.data_source.local.AnimeLocalDataSource
import id.shaderboi.anilist.core.data.data_source.remote.AnimeRemoteDataSource
import id.shaderboi.anilist.core.data.exception.NetworkErrorException
import id.shaderboi.anilist.core.data.exception.NoNetworkException
import id.shaderboi.anilist.core.data.exception.NoDataAvailableException
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import id.shaderboi.anilist.core.domain.repository.AnimeRepository

class AnimeRepositoryImpl(
    private val remoteDataSource: AnimeRemoteDataSource,
    private val localDataSource: AnimeLocalDataSource,
) : AnimeRepository {
    override suspend fun getAnimeDetail(id: Int): Result<Anime> {
        val result = runCatching {
            return Result.success(remoteDataSource.getAnimeDetail(id))
        }

        try {
            return Result.success(localDataSource.getAnimeDetail(id))
        } catch (ex: NoDataAvailableException) {
            // Ignore
        }
        return result
    }

    override suspend fun getAnimeList(): Result<AnimeSearch> {
        val result = runCatching {
            return Result.success(remoteDataSource.getAnimeList())
        }

        try {
            return Result.success(localDataSource.getAnimeList())
        } catch (ex: NoDataAvailableException) {
            // Ignore
        }
        return result
    }
}