package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Author(
    var name: String? = null,
    var username: String? = null,
    var avatarPath: String? = null,
    var rating: Double? = null,
)

fun Author.toAuthorDto(): AuthorDto {
    return AuthorDto(
        name = name,
        username = username,
        avatarPath = avatarPath,
        rating = rating,
    )
}