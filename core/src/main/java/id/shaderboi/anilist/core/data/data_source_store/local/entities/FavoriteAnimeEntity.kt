package id.shaderboi.anilist.core.data.data_source_store.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "anime_favorite"
)
data class FavoriteAnimeEntity(
    val animeId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)