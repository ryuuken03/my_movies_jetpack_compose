package mohammad.toriq.mymovies.presentation.screen.movies

import mohammad.toriq.mymovies.models.Movie

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
data class MoviesUiState (
    val movieList: ArrayList<Movie> = ArrayList(),
    val isLoading: Boolean = false,
    val errorMessage : String = ""
)