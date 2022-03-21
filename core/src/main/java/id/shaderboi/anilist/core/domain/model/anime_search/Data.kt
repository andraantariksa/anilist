package id.shaderboi.anilist.core.domain.model.anime_search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "aired")
    val aired: Aired,
    @Json(name = "airing")
    val airing: Boolean,
    @Json(name = "background")
    val background: String?,
    @Json(name = "broadcast")
    val broadcast: Broadcast?,
    @Json(name = "demographics")
    val demographics: List<Demographic>,
    @Json(name = "duration")
    val duration: String,
    @Json(name = "episodes")
    val episodes: Int?,
    @Json(name = "explicit_genres")
    val explicitGenres: List<ExplicitGenre>,
    @Json(name = "favorites")
    val favorites: Int,
    @Json(name = "genres")
    val genres: List<Genre>,
    @Json(name = "images")
    val images: Images,
    @Json(name = "licensors")
    val licensors: List<Licensor>,
    @Json(name = "mal_id")
    val malId: Int,
    @Json(name = "members")
    val members: Int,
    @Json(name = "popularity")
    val popularity: Int,
    @Json(name = "producers")
    val producers: List<Producer>,
    @Json(name = "rank")
    val rank: Int,
    @Json(name = "rating")
    val rating: String,
    @Json(name = "score")
    val score: Float,
    @Json(name = "scored_by")
    val scoredBy: Int,
    @Json(name = "season")
    val season: String?,
    @Json(name = "source")
    val source: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "studios")
    val studios: List<Studio>,
    @Json(name = "synopsis")
    val synopsis: String,
    @Json(name = "themes")
    val themes: List<Theme>,
    @Json(name = "title")
    val title: String,
    @Json(name = "title_english")
    val titleEnglish: String?,
    @Json(name = "title_japanese")
    val titleJapanese: String?,
    @Json(name = "title_synonyms")
    val titleSynonyms: List<String>,
    @Json(name = "trailer")
    val trailer: Trailer?,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String?,
    @Json(name = "year")
    val year: Int?
)