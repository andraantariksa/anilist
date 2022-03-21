package id.shaderboi.anilist.core.domain.use_case

import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    val animeRepository: AnimeRepository
) {
}