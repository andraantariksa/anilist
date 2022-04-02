package id.shaderboi.anilist.core.data.data_source_store.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "anime_favorite",
    indices = [Index(value = ["animeId"], unique = true)]
)
data class FavoriteAnimeEntity(
    val animeId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)