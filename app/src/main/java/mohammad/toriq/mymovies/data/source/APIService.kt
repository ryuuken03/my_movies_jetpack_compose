package mohammad.toriq.mymovies.data.source.remote.dto

import mohammad.toriq.mymovies.models.GenresDto
import mohammad.toriq.mymovies.models.MovieDto
import mohammad.toriq.mymovies.models.MoviesDto
import mohammad.toriq.mymovies.models.ReviewsDto
import mohammad.toriq.mymovies.models.VideosDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
interface APIService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";
        const val TOKEN: String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZDhiZTZlOTE5YTdiMjMwMmM5MjlhYzg3NDIwODM0MSIsInN1YiI6IjY1MmY5MDI2YTgwMjM2MDBmZDJkODdhNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ap13eLyCoYdiBdoxl_NC4zIMqqwDoKoBfuoYBHNWdU8"
    }

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path(value = "category", encoded = true) category: String,
        @Query(value = "language", encoded = true) language: String,
    ): MoviesDto

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query(value = "page", encoded = true) page: Int,
        @Query(value = "with_genres", encoded = true) withGenres: String?,
        @Query(value = "language", encoded = true) language: String,
    ): MoviesDto

    @GET("genre/movie/list")
    suspend fun getGenres(
//        @Query(value = "language", encoded = true) language: String,
    ): GenresDto

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path(value = "id", encoded = true) id: Long,
    ): MovieDto


    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path(value = "id", encoded = true) id: Long,
        @Query(value = "page", encoded = true) page: Int,
    ): ReviewsDto


    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path(value = "id", encoded = true) id: Long,
    ): VideosDto

}