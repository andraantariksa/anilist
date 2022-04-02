package id.shaderboi.anilist.core.domain.use_case.anime

import javax.inject.Inject

data class AnimeUseCases @Inject constructor(
    val listAnimeUseCase: ListAnimeUseCase,
    val searchAnimeUseCase: SearchAnimeUseCase,
    val getAnimeDetailUseCase: DetailAnimeUseCase
)