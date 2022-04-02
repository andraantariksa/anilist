package id.shaderboi.anilist.core.domain.use_case.anime

import id.shaderboi.anilist.core.domain.model.anime.AnimeSingle
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import javax.inject.Inject

class DetailAnimeUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(id: Int): Result<AnimeData> = animeRepository.getAnimeDetail(id)
}