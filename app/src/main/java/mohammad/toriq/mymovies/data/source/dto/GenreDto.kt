package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class GenreDto (
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("name")
    var name: String? = null,
)

fun GenreDto.toGenre(): Genre {
    return Genre(
        id = id,
        name = name,
    )
}