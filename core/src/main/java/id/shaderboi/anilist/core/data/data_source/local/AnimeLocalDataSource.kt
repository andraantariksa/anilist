package id.shaderboi.anilist.core.data.data_source.local

import id.shaderboi.anilist.core.data.data_source.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.exception.NoDataAvailableException
import id.shaderboi.anilist.core.domain.data_source.AnimeDataSource
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import javax.inject.Inject

class AnimeLocalDataSource @Inject constructor(
    private val anilistDatabase: AnilistDatabase
) : AnimeDataSource {
    private val animeDao = anilistDatabase.animeDao()

    override suspend fun getAnimeDetail(id: Int): Anime {
        return (animeDao.get(id) ?: throw NoDataAvailableException()).anime
    }

    override suspend fun getAnimeList(): AnimeSearch {
        return (animeDao.getAnimeList() ?: throw NoDataAvailableException()).animeList
    }
}