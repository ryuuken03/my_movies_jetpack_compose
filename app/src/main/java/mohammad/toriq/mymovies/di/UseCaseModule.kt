package mohammad.toriq.mymovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohammad.toriq.mymovies.domain.repository.Repository
import mohammad.toriq.mymovies.domain.usecase.GetDetailMovieUseCase
import mohammad.toriq.mymovies.domain.usecase.GetDiscoverMoviesUseCase
import mohammad.toriq.mymovies.domain.usecase.GetGenresUseCase
import mohammad.toriq.mymovies.domain.usecase.GetMovieReviewsUseCase
import mohammad.toriq.mymovies.domain.usecase.GetMovieVideosUseCase
import mohammad.toriq.mymovies.domain.usecase.GetMoviesUseCase
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(repository: Repository): GetMoviesUseCase =
        GetMoviesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetDiscoverMoviesUseCase(repository: Repository): GetDiscoverMoviesUseCase =
        GetDiscoverMoviesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetGenresUseCase(repository: Repository): GetGenresUseCase =
        GetGenresUseCase(repository)

    @Provides
    @Singleton
    fun provideGetDetailMovieUseCase(repository: Repository): GetDetailMovieUseCase =
        GetDetailMovieUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMovieReviewsUseCase(repository: Repository): GetMovieReviewsUseCase =
        GetMovieReviewsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMovieVideosUseCase(repository: Repository): GetMovieVideosUseCase =
        GetMovieVideosUseCase(repository)
}