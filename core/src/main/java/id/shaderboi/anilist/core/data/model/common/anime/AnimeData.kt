package id.shaderboi.anilist.core.data.model.common.anime

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime.Date
import id.shaderboi.anilist.core.domain.model.anime.Image
import id.shaderboi.anilist.core.domain.model.anime.Prop

@JsonClass(generateAdapter = true)
data class AnimeData(
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
    val rank: Int?,
    @Json(name = "rating")
    val rating: String,
    @Json(name = "score")
    val score: Float?,
    @Json(name = "scored_by")
    val scoredBy: Int?,
    @Json(name = "season")
    val season: String?,
    @Json(name = "source")
    val source: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "studios")
    val studios: List<Studio>,
    @Json(name = "synopsis")
    val synopsis: String?,
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
    val type: String?,
    @Json(name = "url")
    val url: String,
    @Json(name = "year")
    val year: Int?
) {
    fun toAnime(): Anime {
        return Anime(
            aired = id.shaderboi.anilist.core.domain.model.anime.Aired(
                aired.from,
                prop = aired.prop?.let { prop ->
                    Prop(
                        to = Date(
                            prop.to?.day,
                            prop.to?.month,
                            prop.to?.year
                        ),
                        from = Date(
                            prop.from?.day,
                            prop.from?.month,
                            prop.from?.year
                        ),
                        string = prop.string
                    )
                },
                aired.to,
                aired.string,
            ),
            airing = airing,
            background = background,
            year = year,
            url = url,
            title = title,
            titleEnglish = titleEnglish,
            titleJapanese = titleJapanese,
            titleSynonyms = titleSynonyms,
            score = score,
            scoredBy = scoredBy,
            season = season,
            rating = rating,
            type = type,
            malId = malId,
            members = members,
            popularity = popularity,
            duration = duration,
            status = status,
            source = source,
            rank = rank,
            episodes = episodes,
            trailer = trailer?.let { trailer ->
                id.shaderboi.anilist.core.domain.model.anime.Trailer(
                    embedUrl = trailer.embedUrl,
                    url = trailer.url,
                    youtubeId = trailer.youtubeId,
                )
            },
            favorites = favorites,
            themes = themes.map { theme ->
                id.shaderboi.anilist.core.domain.model.anime.Theme(
                    malId = theme.malId,
                    name = theme.name,
                    type = theme.type,
                    url = theme.url,
                )
            },
            synopsis = synopsis,
            genres = genres.map { genre ->
                id.shaderboi.anilist.core.domain.model.anime.Genre(
                    malId = genre.malId,
                    name = genre.name,
                    type = genre.type,
                    url = genre.url
                )
            },
            broadcast = broadcast?.let { broadcast ->
                id.shaderboi.anilist.core.domain.model.anime.Broadcast(
                    day = broadcast.day,
                    string = broadcast.string,
                    time = broadcast.time,
                    timezone = broadcast.timezone,
                )
            },
            studios = studios.map { studio ->
                id.shaderboi.anilist.core.domain.model.anime.Studio(
                    malId = studio.malId,
                    name = studio.name,
                    type = studio.type,
                    url = studio.url
                )
            },
            demographics = demographics.map { demographic ->
                id.shaderboi.anilist.core.domain.model.anime.Demographic(
                    malId = demographic.malId,
                    name = demographic.name,
                    type = demographic.type,
                    url = demographic.url
                )
            },
            producers = producers.map { producer ->
                id.shaderboi.anilist.core.domain.model.anime.Producer(
                    malId = producer.malId,
                    name = producer.name,
                    type = producer.type,
                    url = producer.url
                )
            },
            images = id.shaderboi.anilist.core.domain.model.anime.Images(
                jpg = Image(
                    imageUrl = images.jpg.imageUrl,
                    largeImageUrl = images.jpg.largeImageUrl,
                    smallImageUrl = images.jpg.smallImageUrl,
                ),
                webp = Image(
                    imageUrl = images.webp.imageUrl,
                    largeImageUrl = images.webp.largeImageUrl,
                    smallImageUrl = images.webp.smallImageUrl,
                )
            ),
            licensors = licensors.map { licensor ->
                id.shaderboi.anilist.core.domain.model.anime.Licensor(
                    malId = licensor.malId,
                    name = licensor.name,
                    type = licensor.type,
                    url = licensor.url
                )
            },
            explicitGenres = explicitGenres.map { genre ->
                id.shaderboi.anilist.core.domain.model.anime.ExplicitGenre(
                    malId = genre.malId,
                    name = genre.name,
                    type = genre.type,
                    url = genre.url
                )
            }
        )
    }
}