package id.shaderboi.anilist.core.domain.use_case.favorite_anime

import androidx.paging.PagingData
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.repository.FavoriteAnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListFavoriteAnimeUseCase @Inject constructor(
    private val favoriteAnimeRepository: FavoriteAnimeRepository
) {
    operator fun invoke(): Flow<PagingData<AnimeData>> =
        favoriteAnimeRepository.getFavoriteAnime()
}