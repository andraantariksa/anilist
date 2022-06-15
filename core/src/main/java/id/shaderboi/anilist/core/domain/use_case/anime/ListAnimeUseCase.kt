package id.shaderboi.anilist.core.domain.use_case.anime

import androidx.paging.PagingData
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListAnimeUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    operator fun invoke(): Flow<PagingData<Anime>> = animeRepository.getAnimeList()
}