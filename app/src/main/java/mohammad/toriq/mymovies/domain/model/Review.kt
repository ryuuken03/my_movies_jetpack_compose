package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Review (
    var id: String? = null,
    var author: String? = null,
    var authorDetails: Author,
    var content: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var url: String? = null,
)

fun Review.toReviewDto(): ReviewDto {
    return ReviewDto(
        id = id,
        author = author,
        authorDetails = authorDetails.toAuthorDto(),
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        url = url,
    )
}