package id.shaderboi.anilist.core.data.data_source_store.local

import id.shaderboi.anilist.core.data.data_source_store.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.exception.NoDataAvailableException
import id.shaderboi.anilist.core.domain.data_source.AnimeDataSource
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import javax.inject.Inject

class AnimeLocalDataSource @Inject constructor(
    anilistDatabase: AnilistDatabase
) : AnimeDataSource {
    private val animeDao = anilistDatabase.animeDao()

    override suspend fun getAnimeDetail(id: Int): AnimeData {
        return animeDao.get(id)?.anime ?: throw NoDataAvailableException()
    }

    override suspend fun getAnimeList(): List<AnimeData> {
        return animeDao.getAnimeList().map { it.anime }
    }
}