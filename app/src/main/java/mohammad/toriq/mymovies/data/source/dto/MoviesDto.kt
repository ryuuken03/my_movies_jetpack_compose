package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class MoviesDto (
    @SerializedName("page")
    var page: Long = 0,
    @SerializedName("results")
    var movies: List<MovieDto> = listOf(),
    @SerializedName("total_pages")
    var totalPages: Long = 0,
    @SerializedName("total_results")
    var totalResults: Long = 0,
)

fun MoviesDto.toMovies(): Movies {
    return Movies(
        page = page,
        movies = movies.map { it.toMovie() }.toList(),
        totalPages = totalPages,
        totalResults = totalResults,
    )
}