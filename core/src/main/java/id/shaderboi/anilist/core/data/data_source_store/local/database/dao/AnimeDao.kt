package id.shaderboi.anilist.core.data.data_source_store.local.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeEntity

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAnime(anime: AnimeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllAnime(anime: List<AnimeEntity>)

    @Query("SELECT * FROM anime WHERE id = :id")
    fun getAnime(id: Int): AnimeEntity?

    @Query("SELECT * FROM anime ORDER BY id ASC")
    fun getAnimeList(): PagingSource<Int, AnimeEntity>
}