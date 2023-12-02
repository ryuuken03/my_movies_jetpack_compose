package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Genre(
    var id: Long = 0,
    var name: String? = null,
)

fun Genre.toGenreDto(): GenreDto {
    return GenreDto(
        id = id,
        name = name,
    )
}