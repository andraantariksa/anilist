package id.shaderboi.anilist.core.domain.model.common.anime

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Broadcast(
    @Json(name = "day")
    val day: String?,
    @Json(name = "string")
    val string: String?,
    @Json(name = "time")
    val time: String?,
    @Json(name = "timezone")
    val timezone: String?
)