package id.shaderboi.anilist.core.di

import com.squareup.moshi.JsonClass
import id.shaderboi.anilist.core.data.remote.network.JikanAPIService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import id.shaderboi.anilist.core.domain.model.error.Error as JikanError

object CoreModule {
    fun provideJikanAPIService(): JikanAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(JikanAPIService::class.java)
    }
}


sealed class JikanResponse<T> {
    @JsonClass(generateAdapter = true)
    class Success<T>(val data: T): JikanResponse<T>()

    @JsonClass(generateAdapter = true)
    class Error<T>(val error: JikanError): JikanResponse<T>()
}

