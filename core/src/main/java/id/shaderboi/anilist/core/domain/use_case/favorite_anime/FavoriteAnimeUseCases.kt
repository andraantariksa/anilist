package id.shaderboi.anilist.core.domain.use_case.favorite_anime

import javax.inject.Inject

data class FavoriteAnimeUseCases @Inject constructor(
    val listFavoriteAnime: ListFavoriteAnimeUseCase,
    val addFavoriteAnime: AddFavoriteAnimeUseCase,
    val getFavoriteAnime: GetFavoriteAnimeUseCase,
    val deleteFavoriteAnime: DeleteFavoriteAnimeUseCase
)