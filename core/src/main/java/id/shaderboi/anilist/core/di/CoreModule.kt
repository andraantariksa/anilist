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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    const val JIKAN_HOSTNAME = "api.jikan.moe"
    const val DATABASE_KEY = "anilist"

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val certificatePinner = CertificatePinner.Builder()
            .add(JIKAN_HOSTNAME, "sha256/D19JhbQhM7FIf4y+0wu/jEXrlR9AwOpSTH8Bp46Fr/I=")
            .build()
        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(context))
            .certificatePinner(certificatePinner)
            .build()
    }

    @Singleton
    @Provides
    fun provideJikanAPIService(okHttpClient: OkHttpClient): JikanAPIService {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
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
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, moshi: Moshi): AnilistDatabase {
        val passphrase = SQLiteDatabase.getBytes(DATABASE_KEY.toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(context, AnilistDatabase::class.java, "anilist")
            .addTypeConverter(AnimeConverter(MoshiJSONParser(moshi)))
            .openHelperFactory(factory)
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


