package mohammad.toriq.mymovies.domain.usecase

import kotlinx.coroutines.flow.Flow
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.domain.repository.Repository
import mohammad.toriq.mymovies.models.Movie

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class GetDetailMovieUseCase(
    private val repository: Repository
) {
    operator fun invoke(id: Long,
    ): Flow<Resource<Movie>> {
        return repository.getDetailMovie(id)
    }
}