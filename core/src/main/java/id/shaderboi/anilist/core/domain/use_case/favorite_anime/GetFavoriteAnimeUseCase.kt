package id.shaderboi.anilist.core.domain.use_case.favorite_anime

import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeEntity
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import id.shaderboi.anilist.core.domain.repository.FavoriteAnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteAnimeUseCase @Inject constructor(
    private val favoriteAnimeRepository: FavoriteAnimeRepository
) {
    suspend operator fun invoke(id: Int): Flow<FavoriteAnimeEntity?> =
        favoriteAnimeRepository.getFavoriteAnimeDetail(id)
}