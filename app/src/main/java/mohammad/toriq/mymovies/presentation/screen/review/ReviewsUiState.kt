package mohammad.toriq.mymovies.presentation.screen.review

import mohammad.toriq.mymovies.models.Review

/***
 * Created By Mohammad Toriq on 01/12/2023
 */
data class ReviewsUiState (
    val reviewList: ArrayList<Review> = ArrayList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)