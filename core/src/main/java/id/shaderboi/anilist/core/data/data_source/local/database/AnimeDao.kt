package id.shaderboi.anilist.core.data.data_source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.shaderboi.anilist.core.data.data_source.local.entities.AnimeEntity
import id.shaderboi.anilist.core.data.data_source.local.entities.AnimeListEntity
import id.shaderboi.anilist.core.domain.model.anime.Anime

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(anime: AnimeEntity)

    @Query("SELECT * FROM anime WHERE id = :id")
    fun get(id: Int): AnimeEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimeList(anime: AnimeListEntity)

    @Query("SELECT * FROM anime_list WHERE id = 1")
    fun getAnimeList(): AnimeListEntity?
}