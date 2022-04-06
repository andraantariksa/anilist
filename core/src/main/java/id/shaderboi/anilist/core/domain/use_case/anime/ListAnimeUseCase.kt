package id.shaderboi.anilist.core.domain.use_case.anime

import androidx.paging.PagingData
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListAnimeUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    operator fun invoke(): Flow<PagingData<AnimeData>> = animeRepository.getAnimeList()
}