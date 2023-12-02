package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class VideosDto (
    @SerializedName("results")
    var videos: List<VideoDto> = listOf(),
)

fun VideosDto.toVideos(): Videos {
    return Videos(
        videos = videos.map { it.toVideo() }.toList(),
    )
}