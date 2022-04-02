package id.shaderboi.anilist.core.domain.use_case.favorite_anime

import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import id.shaderboi.anilist.core.domain.repository.FavoriteAnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavoriteAnimeUseCase @Inject constructor(
    private val favoriteAnimeRepository: FavoriteAnimeRepository
) {
    suspend operator fun invoke(id: Int) = favoriteAnimeRepository.deleteFavoriteAnime(id)
}