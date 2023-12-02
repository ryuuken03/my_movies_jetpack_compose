package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Video (
    var iso_639_1: String? = null,
    var iso_3166_1: String? = null,
    var name: String? = null,
    var key: String? = null,
    var site: String? = null,
    var size: Int = 0,
    var type: String? = null,
    var official: Boolean? = null,
    var publishedAt: String? = null,
    var id: String? = null,
)

fun Video.toVideoDto(): VideoDto {
    return VideoDto(
        iso_639_1 = iso_639_1,
        iso_3166_1 = iso_3166_1,
        name = name,
        key = key,
        site = site,
        size = size,
        type = type,
        official = official,
        publishedAt = publishedAt,
        id = id,
    )
}