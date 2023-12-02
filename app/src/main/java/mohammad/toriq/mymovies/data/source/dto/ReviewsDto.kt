package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class ReviewsDto (
    @SerializedName("page")
    var page: Long = 0,
    @SerializedName("results")
    var reviews: List<ReviewDto> = listOf(),
    @SerializedName("total_pages")
    var totalPages: Long = 0,
    @SerializedName("total_results")
    var totalResults: Long = 0,
)

fun ReviewsDto.toReviews(): Reviews {
    return Reviews(
        page = page,
        reviews = reviews.map { it.toReview() }.toList(),
        totalPages = totalPages,
        totalResults = totalResults,
    )
}