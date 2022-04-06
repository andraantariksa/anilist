package id.shaderboi.anilist.core.data.data_source_store.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.shaderboi.anilist.core.data.data_source_store.local.entities.AnimeRemoteKeyEntity

@Dao
interface AnimeRemoteKeyDao {
    @Query("SELECT * FROM anime_remote_key WHERE id = :id")
    suspend fun getRemoteKey(id: Int): AnimeRemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(remoteKeys: List<AnimeRemoteKeyEntity>)

    @Query("DELETE FROM anime_remote_key")
    suspend fun removeRemoteKeys()
}