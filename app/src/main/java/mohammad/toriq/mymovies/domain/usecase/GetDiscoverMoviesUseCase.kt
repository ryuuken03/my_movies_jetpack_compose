package mohammad.toriq.mymovies.domain.usecase

import kotlinx.coroutines.flow.Flow
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.domain.repository.Repository
import mohammad.toriq.mymovies.models.Movies

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class GetDiscoverMoviesUseCase(
    private val repository: Repository
) {
    operator fun invoke(page: Int, withGenres: String?, language: String,
    ): Flow<Resource<Movies>> {
        return repository.getDiscoverMovies(page,withGenres, language)
    }
}