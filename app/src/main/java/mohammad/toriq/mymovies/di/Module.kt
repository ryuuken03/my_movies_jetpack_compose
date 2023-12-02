package mohammad.toriq.mymovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohammad.toriq.mymovies.data.repository.RepositoryImpl
import mohammad.toriq.mymovies.domain.repository.Repository
import mohammad.toriq.mymovies.data.source.remote.dto.APIService
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    @Singleton
    fun provideRepositoryImpl(
        apiService: APIService,
    ): Repository = RepositoryImpl(apiService)

}