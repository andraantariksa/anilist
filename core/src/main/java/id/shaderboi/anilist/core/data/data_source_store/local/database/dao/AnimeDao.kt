package id.shaderboi.anilist.core.data.data_source_store.local.database.dao

import androidx.room.*
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeEntity

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime: AnimeEntity)

    @Query("SELECT * FROM anime WHERE id = :id")
    fun get(id: Int): AnimeEntity?


    @Transaction
    fun insertAnimes(animes: List<AnimeEntity>) {
        animes.forEach { anime ->
            insertAnime(anime)
        }
    }

    @Query("SELECT * FROM anime")
    fun getAnimeList(): List<AnimeEntity>
}