package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class GenresDto (
    @SerializedName("genres")
    var genres : List<GenreDto> = listOf(),
)

fun GenresDto.toGenres(): Genres {
    return Genres(
        genres = genres.map { it.toGenre() }.toList(),
    )
}