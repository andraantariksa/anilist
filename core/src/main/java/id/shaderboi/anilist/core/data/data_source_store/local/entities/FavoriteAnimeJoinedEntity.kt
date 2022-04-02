package id.shaderboi.anilist.core.data.data_source_store.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteAnimeJoinedEntity(
    @Embedded
    val favorite: FavoriteAnimeEntity,
    @Relation(parentColumn = "animeId", entityColumn = "id")
    val anime: AnimeEntity,
)