package id.shaderboi.anilist.core.data.data_source_store.local.database.dao

import androidx.room.*
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteAnime(anime: FavoriteAnimeEntity)

    @Transaction
    @Query("SELECT * FROM anime_favorite")
    fun getFavoriteAnime(): Flow<List<FavoriteAnimeJoinedEntity>>
}