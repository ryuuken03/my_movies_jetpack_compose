package mohammad.toriq.mymovies.domain.usecase

import kotlinx.coroutines.flow.Flow
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.domain.repository.Repository
import mohammad.toriq.mymovies.models.Genres

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class GetGenresUseCase(
    private val repository: Repository
) {
    operator fun invoke(language: String,
    ): Flow<Resource<Genres>> {
        return repository.getGenres(language)
    }
}