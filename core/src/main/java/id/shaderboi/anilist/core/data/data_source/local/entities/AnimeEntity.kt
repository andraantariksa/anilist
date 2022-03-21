package id.shaderboi.anilist.core.data.data_source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.shaderboi.anilist.core.domain.model.anime.Anime

@Entity(
    tableName = "anime"
)
data class AnimeEntity(
    val anime: Anime,
    @PrimaryKey val id: Int = 0,
)