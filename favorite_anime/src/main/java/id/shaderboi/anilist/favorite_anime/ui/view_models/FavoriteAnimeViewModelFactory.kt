package id.shaderboi.anilist.favorite_anime.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.shaderboi.anilist.core.domain.use_case.favorite_anime.FavoriteAnimeUseCases
import javax.inject.Inject

class FavoriteAnimeViewModelFactory @Inject constructor(
    private val favoriteAnimeUseCases: FavoriteAnimeUseCases,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != FavoriteAnimeViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return FavoriteAnimeViewModel(
            favoriteAnimeUseCases,
        ) as T
    }
}