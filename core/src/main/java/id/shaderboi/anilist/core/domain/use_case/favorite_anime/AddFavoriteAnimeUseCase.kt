package id.shaderboi.anilist.core.domain.use_case.favorite_anime

import id.shaderboi.anilist.core.domain.repository.FavoriteAnimeRepository
import javax.inject.Inject

class AddFavoriteAnimeUseCase @Inject constructor(
    private val favoriteAnimeRepository: FavoriteAnimeRepository
) {
    suspend operator fun invoke(id: Int) = favoriteAnimeRepository.addFavoriteAnime(id)
}