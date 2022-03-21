package id.shaderboi.anilist.core.domain.data_source

import id.shaderboi.anilist.core.domain.model.anime.Anime

interface AnimeDataSource {
    fun getAnimeDetail(id: Int): Anime
}