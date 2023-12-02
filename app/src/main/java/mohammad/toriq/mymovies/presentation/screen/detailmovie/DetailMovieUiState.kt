package mohammad.toriq.mymovies.presentation.screen.detailmovie

import mohammad.toriq.mymovies.models.Genre
import mohammad.toriq.mymovies.models.Movie

/***
 * Created By Mohammad Toriq on 01/12/2023
 */
data class DetailMovieUiState (
    val movie: Movie?= null,
    val isLoading: Boolean = false
)