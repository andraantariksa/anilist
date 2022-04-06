package id.shaderboi.anilist.core.data.data_source_store.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.shaderboi.anilist.core.data.data_source_store.local.database.converter.AnimeConverter
import id.shaderboi.anilist.core.data.data_source_store.local.database.dao.AnimeDao
import id.shaderboi.anilist.core.data.data_source_store.local.database.dao.AnimeRemoteKeyDao
import id.shaderboi.anilist.core.data.data_source_store.local.database.dao.FavoriteDao
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeRemoteKeyEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity

@Database(
    entities = [AnimeEntity::class, FavoriteAnimeEntity::class, AnimeRemoteKeyEntity::class],
    version = 1
)
@TypeConverters(AnimeConverter::class)
abstract class AnilistDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun animeRemoteKeyDao(): AnimeRemoteKeyDao
    abstract fun favoriteAnimeDao(): FavoriteDao
}