package id.shaderboi.anilist.core.domain.data_store

interface FavoriteAnimeDataStore {
    suspend fun addFavoriteAnime(animeId: Int)
}