package mohammad.toriq.mymovies.domain.repository

import kotlinx.coroutines.flow.Flow
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.models.Genres
import mohammad.toriq.mymovies.models.Movie
import mohammad.toriq.mymovies.models.Movies
import mohammad.toriq.mymovies.models.Reviews
import mohammad.toriq.mymovies.models.Videos

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
interface Repository {
    fun getMovies(
        category: String,
        language: String,
    ): Flow<Resource<Movies>>

    fun getDiscoverMovies(
        page: Int,
        withGenres: String?,
        language: String,
    ): Flow<Resource<Movies>>

    fun getGenres(
        language: String,
    ): Flow<Resource<Genres>>

    fun getDetailMovie(
        id: Long,
    ): Flow<Resource<Movie>>

    fun getMovieReviews(
        id: Long,
        page: Int,
    ): Flow<Resource<Reviews>>

    fun getMovieVideos(
       id: Long,
    ): Flow<Resource<Videos>>
}