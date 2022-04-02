package id.shaderboi.anilist.core.data.data_source_store.local

import id.shaderboi.anilist.core.data.data_source_store.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeEntity
import id.shaderboi.anilist.core.domain.data_source.AnimeDataStore
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import javax.inject.Inject

class AnimeLocalDataStore @Inject constructor(
    anilistDatabase: AnilistDatabase
) : AnimeDataStore {
    private val animeDao = anilistDatabase.animeDao()

    override suspend fun insertAnime(animeData: AnimeData) =
        animeDao.insertAnime(AnimeEntity(animeData, animeData.malId))

    override suspend fun insertAnimes(animeDatas: List<AnimeData>) =
        animeDao.insertAnimes(animeDatas.map { AnimeEntity(it) })
}