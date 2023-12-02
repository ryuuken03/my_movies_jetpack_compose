package mohammad.toriq.mymovies.models

import com.google.gson.annotations.SerializedName


/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class ReviewDto (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("author")
    var author: String? = null,
    @SerializedName("author_details")
    var authorDetails: AuthorDto,
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    @SerializedName("url")
    var url: String? = null,
)

fun ReviewDto.toReview(): Review {
    return Review(
        id = id,
        author = author,
        authorDetails = authorDetails.toAuthor(),
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        url = url,
    )
}