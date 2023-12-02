package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Movie (
    var id: Long = 0,
    var title: String? = null,
    var desc: String? = null,
    var originalTitle: String? = null,
    var popularity: Double = 0.0,
    var imageUrlBackDrop: String? = null,
    var imageUrl: String? = null,
    var voteAverage: Float = 0f,
    var voteCount: Long = 0,
    var releaseDate: String? = null,
    var runtime: Int = 0,
    var budget: Long = 0,
    var genres: List<Genre> = listOf(),
    var homepage: String? = null,
    var status: String? = null,
)

fun Movie.toMovieDto(): MovieDto {
    return MovieDto(
        id = id,
        title = title,
        desc = desc,
        originalTitle = originalTitle,
        popularity = popularity,
        imageUrlBackDrop = imageUrlBackDrop,
        imageUrl = imageUrl,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate,
        runtime = runtime,
        budget = budget,
        genres = genres.map { it.toGenreDto() }.toList(),
        homepage = homepage,
        status = status,
    )
}