package id.shaderboi.anilist.core.domain.use_case

import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import javax.inject.Inject

class ListAnimeUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(): Result<AnimeSearch> = animeRepository.getAnimeList()
}