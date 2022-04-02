package id.shaderboi.anilist.favorite_anime.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.shaderboi.anilist.core.domain.use_case.favorite_anime.FavoriteAnimeUseCases
import id.shaderboi.anilist.favorite_anime.ui.view_models.FavoriteAnimeViewModelFactory

@Module
@InstallIn(SingletonComponent::class)
class FavoriteModule {
    @Provides
    fun provideFavoriteViewModelFactory(favoriteAnimeUseCases: FavoriteAnimeUseCases):
            FavoriteAnimeViewModelFactory {
        return FavoriteAnimeViewModelFactory(favoriteAnimeUseCases)
    }
}