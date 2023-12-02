package mohammad.toriq.mymovies.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mohammad.toriq.mymovies.domain.repository.Repository
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.data.source.remote.dto.APIService
import mohammad.toriq.mymovies.models.Genres
import mohammad.toriq.mymovies.models.Movie
import mohammad.toriq.mymovies.models.Movies
import mohammad.toriq.mymovies.models.Reviews
import mohammad.toriq.mymovies.models.Videos
import mohammad.toriq.mymovies.models.toGenres
import mohammad.toriq.mymovies.models.toMovie
import mohammad.toriq.mymovies.models.toMovies
import mohammad.toriq.mymovies.models.toReviews
import mohammad.toriq.mymovies.models.toVideos
import retrofit2.HttpException
import java.io.IOException

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class RepositoryImpl constructor(
    private val apiService: APIService
) : Repository
{
    override fun getMovies(category:String, language:String): Flow<Resource<Movies>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getMoviesFromAPI(category,language)))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    private suspend fun getMoviesFromAPI(category:String, language:String): Movies {
        val remoteMovies = apiService.getMovies(
            category = category,
            language = language,)
        return remoteMovies.toMovies()
    }

    override fun getDiscoverMovies(page: Int, withGenres : String?, language:String):Flow<Resource<Movies>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getDiscoverMoviesFromAPI(page,withGenres,language)))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    private suspend fun getDiscoverMoviesFromAPI(page: Int, withGenres : String?, language:String): Movies {
        val remoteMovies = apiService.getDiscoverMovies(
            page = page,
            withGenres = withGenres,
            language = language,)
        return remoteMovies.toMovies()
    }

    override fun getGenres(language:String):Flow<Resource<Genres>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getGenresFromAPI(language)))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    private suspend fun getGenresFromAPI(language:String): Genres {
        val remoteGenres = apiService.getGenres(
//            token = "bearer "+APIService.TOKEN,
//            language = language,
            )
        return remoteGenres.toGenres()
    }

    override fun getDetailMovie(id:Long):Flow<Resource<Movie>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getDetailMovieFromAPI(id)))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    private suspend fun getDetailMovieFromAPI(id:Long): Movie {
        val remoteMovie = apiService.getDetailMovie(
            id = id)
        return remoteMovie.toMovie()
    }

    override fun getMovieReviews(id:Long,page:Int):Flow<Resource<Reviews>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getMovieReviewsFromAPI(id,page)))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    private suspend fun getMovieReviewsFromAPI(id:Long,page:Int): Reviews {
        val remoteReviews = apiService.getMovieReviews(
            id = id,
            page = page,)
        return remoteReviews.toReviews()
    }

    override fun getMovieVideos(id:Long):Flow<Resource<Videos>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getMovieVideosFromAPI(id)))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    private suspend fun getMovieVideosFromAPI(id:Long): Videos {
        val remoteVideos = apiService.getMovieVideos(
            id = id,)
        return remoteVideos.toVideos()
    }
}