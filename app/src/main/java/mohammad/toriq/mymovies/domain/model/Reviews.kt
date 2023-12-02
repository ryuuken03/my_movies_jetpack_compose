package mohammad.toriq.mymovies.models


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class Reviews (
    var page: Long = 0,
    var reviews: List<Review> = listOf(),
    var totalPages: Long = 0,
    var totalResults: Long = 0,
)

fun Reviews.toReviewsDto(): ReviewsDto {
    return ReviewsDto(
        page = page,
        reviews = reviews.map { it.toReviewDto() }.toList(),
        totalPages = totalPages,
        totalResults = totalResults,
    )
}