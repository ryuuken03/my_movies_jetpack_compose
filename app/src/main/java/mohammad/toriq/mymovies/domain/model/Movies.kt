package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Movies (
    var page: Long = 0,
    var movies: List<Movie> = listOf(),
    var totalPages: Long = 0,
    var totalResults: Long = 0,
)

fun Movies.toMoviesDto(): MoviesDto {
    return MoviesDto(
        page = page,
        movies = movies.map { it.toMovieDto() }.toList(),
        totalPages = totalPages,
        totalResults = totalResults,
    )
}