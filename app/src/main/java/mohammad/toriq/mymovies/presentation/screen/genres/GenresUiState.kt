package mohammad.toriq.mymovies.presentation.screen.genres

import mohammad.toriq.mymovies.models.Genre

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
data class GenresUiState (
    val genreList: ArrayList<Genre> = ArrayList(),
    val isLoading: Boolean = false
)