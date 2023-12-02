package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class AuthorDto(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("avatar_path")
    var avatarPath: String? = null,
    @SerializedName("rating")
    var rating: Double? = null,
)

fun AuthorDto.toAuthor(): Author {
    return Author(
        name = name,
        username = username,
        avatarPath = avatarPath,
        rating = rating,
    )
}