package id.shaderboi.anilist.core.data.data_source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch

@Entity(
    tableName = "anime_list"
)
data class AnimeListEntity(
    val animeList: AnimeSearch,
    @PrimaryKey val id: Int = 1,
)