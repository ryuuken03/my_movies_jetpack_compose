package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class MovieDto (
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("overview")
    var desc: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("popularity")
    var popularity: Double = 0.0,
    @SerializedName("backdrop_path")
    var imageUrlBackDrop: String? = null,
    @SerializedName("poster_path")
    var imageUrl: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Float = 0f,
    @SerializedName("vote_count")
    var voteCount: Long = 0,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("runtime")
    var runtime: Int = 0,
    @SerializedName("budget")
    var budget: Long = 0,
    @SerializedName("genres")
    var genres: List<GenreDto> = listOf(),
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("status")
    var status: String? = null,
)

fun MovieDto.toMovie(): Movie {
    return Movie(
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
        genres = genres.map { it.toGenre() }.toList(),
        homepage = homepage,
        status = status,
    )
}