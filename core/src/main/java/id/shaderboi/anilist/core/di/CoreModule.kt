package id.shaderboi.anilist.core.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.shaderboi.anilist.core.data.data_source_store.local.database.AnilistDatabase
import id.shaderboi.anilist.core.data.data_source_store.local.database.converter.AnimeConverter
import id.shaderboi.anilist.core.data.data_source_store.local.database.converter.MoshiJSONParser
import id.shaderboi.anilist.core.data.data_source_store.remote.network.JikanAPIService
import id.shaderboi.anilist.core.data.data_source_store.remote.network.NetworkInterceptor
import id.shaderboi.anilist.core.data.repository.AnimeRepositoryImpl
import id.shaderboi.anilist.core.data.repository.FavoriteAnimeRepositoryImpl
import id.shaderboi.anilist.core.domain.repository.AnimeRepository
import id.shaderboi.anilist.core.domain.repository.FavoriteAnimeRepository
import id.shaderboi.anilist.core.util.preference.AppPreferenceStore
import id.shaderboi.anilist.core.util.preference.AppPreferenceStoreImpl
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
    fun providePreferenceStore(@ApplicationContext context: Context): AppPreferenceStore {
        return AppPreferenceStoreImpl(context)
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
        jikanAPIService: JikanAPIService,
        anilistDatabase: AnilistDatabase
    ): AnimeRepository {
        return AnimeRepositoryImpl(jikanAPIService, anilistDatabase)
    }

    @Singleton
    @Provides
    fun provideFavoriteAnimeRepository(
        anilistDatabase: AnilistDatabase
    ): FavoriteAnimeRepository {
        return FavoriteAnimeRepositoryImpl(anilistDatabase)
    }
}


