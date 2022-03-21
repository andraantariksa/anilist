package id.shaderboi.anilist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.shaderboi.anilist.core.data.data_source.local.AnimeLocalDataSource
import id.shaderboi.anilist.core.data.data_source.remote.AnimeRemoteDataSource
import id.shaderboi.anilist.core.data.repository.AnimeRepositoryImpl
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import id.shaderboi.anilist.core.domain.use_case.AnimeUseCases
import id.shaderboi.anilist.core.domain.use_case.ListAnimeUseCase
import id.shaderboi.anilist.core.domain.use_case.SearchAnimeUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

//    @Provides
//    fun provideAnimeUseCases(): AnimeUseCases {
//        return AnimeUseCases(
//            listAnimeUseCase = ListAnimeUseCase(),
//            searchAnimeUseCase = SearchAnimeUseCase()
//        )
//    }
}