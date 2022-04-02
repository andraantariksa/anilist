package id.shaderboi.anilist.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//    @Provides
//    fun provideAnimeUseCases(): AnimeUseCases {
//        return AnimeUseCases(
//            listAnimeUseCase = ListAnimeUseCase(),
//            searchAnimeUseCase = SearchAnimeUseCase()
//        )
//    }
}