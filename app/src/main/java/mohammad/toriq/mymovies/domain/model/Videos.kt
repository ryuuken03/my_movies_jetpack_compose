package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Videos (
    var videos: List<Video> = listOf(),
)

fun Videos.toVideosDto(): VideosDto {
    return VideosDto(
        videos = videos.map { it.toVideoDto() }.toList(),
    )
}