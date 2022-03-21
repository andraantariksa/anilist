package id.shaderboi.anilist.core.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.shaderboi.anilist.core.data.data_source.local.AnimeLocalDataSource
import id.shaderboi.anilist.core.data.data_source.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.data_source.local.database.converter.AnimeConverter
import id.shaderboi.anilist.core.data.data_source.local.database.converter.MoshiJSONParser
import id.shaderboi.anilist.core.data.data_source.remote.AnimeRemoteDataSource
import id.shaderboi.anilist.core.data.data_source.remote.network.JikanAPIService
import id.shaderboi.anilist.core.data.data_source.remote.network.NetworkInterceptor
import id.shaderboi.anilist.core.data.repository.AnimeRepositoryImpl
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Singleton
    @Provides
    fun provideJikanAPIService(@ApplicationContext context: Context): JikanAPIService {
        val client = OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.jikan.moe/v4/")
            .build()
        return retrofit.create(JikanAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AnilistDatabase {
        val moshi = Moshi.Builder().build()
        return Room.databaseBuilder(context, AnilistDatabase::class.java, "anilist")
            .addTypeConverter(AnimeConverter(MoshiJSONParser(moshi)))
            .build()
    }

    @Singleton
    @Provides
    fun provideAnimeRepository(
        remoteDataSource: AnimeRemoteDataSource,
        localDataSource: AnimeLocalDataSource
    ): AnimeRepository {
        return AnimeRepositoryImpl(
            remoteDataSource,
            localDataSource
        )
    }
}

