package id.shaderboi.anilist.core.data.data_source.local.database.converter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import java.lang.reflect.Type

class MoshiJSONParser(
    private val moshi: Moshi
): JSONParser {
    override fun <T> fromJson(json: String, type: Type): T? {
        val jsonAdapter = moshi.adapter<T>(type)
        return jsonAdapter.fromJson(json)
    }

    override fun <T> toJson(obj: T, type: Type): String {
        val jsonAdapter = moshi.adapter<T>(type)
        return jsonAdapter.toJson(obj)
    }
}