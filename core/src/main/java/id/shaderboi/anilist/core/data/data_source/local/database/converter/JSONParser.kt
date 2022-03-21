package id.shaderboi.anilist.core.data.data_source.local.database.converter

import java.lang.reflect.Type

interface JSONParser {
    fun <T> fromJson(json: String, type: Type): T?
    fun <T> toJson(obj: T, type: Type): String
}