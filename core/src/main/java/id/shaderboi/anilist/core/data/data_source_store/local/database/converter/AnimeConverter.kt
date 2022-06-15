package id.shaderboi.anilist.core.data.data_source_store.local.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import id.shaderboi.anilist.core.data.model.common.anime.AnimeData

@ProvidedTypeConverter
class AnimeConverter(
    private val jsonParser: JSONParser
) {
    @TypeConverter
    fun animeFromString(string: String): AnimeData {
        return jsonParser.fromJson<AnimeData>(string, AnimeData::class.java)!!
    }

    @TypeConverter
    fun animeToString(animeData: AnimeData): String {
        return jsonParser.toJson(animeData, AnimeData::class.java)
    }
}