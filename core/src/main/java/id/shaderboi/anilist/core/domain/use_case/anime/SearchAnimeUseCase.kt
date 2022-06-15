package id.shaderboi.anilist.core.domain.use_case.anime

import androidx.paging.PagingData
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    val animeRepository: AnimeRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Anime>> =
        animeRepository.searchAnime(query)
}