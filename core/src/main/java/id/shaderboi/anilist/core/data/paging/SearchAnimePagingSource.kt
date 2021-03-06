package id.shaderboi.anilist.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.shaderboi.anilist.core.data.data_source_store.remote.network.JIKAN_STARTING_PAGE
import id.shaderboi.anilist.core.data.data_source_store.remote.network.JikanAPIService
import id.shaderboi.anilist.core.domain.model.anime.Anime
import javax.inject.Inject

class SearchAnimePagingSource @Inject constructor(
    private val jikanAPIService: JikanAPIService,
    private val query: String
) : PagingSource<Int, Anime>() {
    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        val currentPage = params.key ?: JIKAN_STARTING_PAGE
        return try {
            val anime = jikanAPIService.searchAnime(
                query,
                currentPage
            ).data.map { animeData -> animeData.toAnime() }
            val endOfPagination = anime.isEmpty()
            if (endOfPagination) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                LoadResult.Page(
                    data = anime,
                    prevKey = if (currentPage == JIKAN_STARTING_PAGE) null else currentPage - 1,
                    nextKey = if (endOfPagination) null else currentPage + 1
                )
            }
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}