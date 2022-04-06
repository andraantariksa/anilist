package id.shaderboi.anilist.core.data.data_source_store.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_remote_key")
data class AnimeRemoteKeyEntity(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)