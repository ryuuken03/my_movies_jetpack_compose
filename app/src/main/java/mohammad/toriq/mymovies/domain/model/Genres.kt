package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Genres (
    var genres : List<Genre> = listOf(),
)
fun Genres.toGenresDto(): GenresDto {
    return GenresDto(
        genres = genres.map { it.toGenreDto() }.toList(),
    )
}