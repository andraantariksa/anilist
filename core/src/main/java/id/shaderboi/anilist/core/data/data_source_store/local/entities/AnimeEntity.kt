package id.shaderboi.anilist.core.data.data_source_store.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.shaderboi.anilist.core.data.model.common.anime.AnimeData

@Entity(
    tableName = "anime"
)
data class AnimeEntity(
    val anime: AnimeData,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)