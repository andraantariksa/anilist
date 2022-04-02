package id.shaderboi.anilist.core.domain.use_case.anime

import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import javax.inject.Inject

class ListAnimeUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(): Result<List<AnimeData>> = animeRepository.getAnimeList()
}