package id.shaderboi.anilist.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.shaderboi.anilist.core.domain.use_case.favorite_anime.FavoriteAnimeUseCases

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteAnimeDynamicFeatureDependencies {
    fun favoriteAnimeUseCases(): FavoriteAnimeUseCases
}