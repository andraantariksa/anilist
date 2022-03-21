package id.shaderboi.anilist.core.data.data_source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.shaderboi.anilist.core.data.data_source.local.database.converter.AnimeConverter
import id.shaderboi.anilist.core.data.data_source.local.entities.AnimeEntity
import id.shaderboi.anilist.core.data.data_source.local.entities.AnimeListEntity
import id.shaderboi.anilist.core.domain.model.anime.Anime

@Database(
    entities = [AnimeEntity::class, AnimeListEntity::class],
    version = 1
)
@TypeConverters(AnimeConverter::class)
abstract class AnilistDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}