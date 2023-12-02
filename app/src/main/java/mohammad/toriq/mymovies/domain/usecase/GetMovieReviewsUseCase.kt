package mohammad.toriq.mymovies.domain.usecase

import kotlinx.coroutines.flow.Flow
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.domain.repository.Repository
import mohammad.toriq.mymovies.models.Reviews

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class GetMovieReviewsUseCase(
    private val repository: Repository
) {
    operator fun invoke(id: Long, page: Int,
    ): Flow<Resource<Reviews>> {
        return repository.getMovieReviews(id,page)
    }
}