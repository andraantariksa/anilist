package id.shaderboi.anilist.core.data.data_source.local.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.AnimeSearch

@ProvidedTypeConverter
class AnimeConverter(
    private val jsonParser: JSONParser
) {
    @TypeConverter
    fun animeFromString(string: String): Anime {
        return jsonParser.fromJson<Anime>(string, Types.newParameterizedType(Anime::class.java))!!
    }

    @TypeConverter
    fun animeToString(anime: Anime): String {
        return jsonParser.toJson<Anime>(anime, Types.newParameterizedType(Anime::class.java))
    }

    @TypeConverter
    fun animeSearchFromString(string: String): AnimeSearch {
        return jsonParser.fromJson<AnimeSearch>(string, Types.newParameterizedType(AnimeSearch::class.java))!!
    }

    @TypeConverter
    fun animeSearchToString(animeSearch: AnimeSearch): String {
        return jsonParser.toJson<AnimeSearch>(animeSearch, Types.newParameterizedType(AnimeSearch::class.java))
    }
}