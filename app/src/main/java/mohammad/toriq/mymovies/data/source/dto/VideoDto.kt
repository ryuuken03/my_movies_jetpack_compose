package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class VideoDto (
    @SerializedName("iso_639_1")
    var iso_639_1: String? = null,
    @SerializedName("iso_3166_1")
    var iso_3166_1: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("key")
    var key: String? = null,
    @SerializedName("site")
    var site: String? = null,
    @SerializedName("size")
    var size: Int = 0,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("official")
    var official: Boolean? = null,
    @SerializedName("published_at")
    var publishedAt: String? = null,
    @SerializedName("id")
    var id: String? = null,
)
fun VideoDto.toVideo(): Video {
    return Video(
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