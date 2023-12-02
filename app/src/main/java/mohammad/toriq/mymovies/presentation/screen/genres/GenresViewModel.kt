package mohammad.toriq.mymovies.presentation.screen.genres

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.domain.usecase.GetGenresUseCase
import mohammad.toriq.mymovies.models.Genre
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel()  {

    private val _state = mutableStateOf(GenresUiState())
    val state: State<GenresUiState> = _state

    var isInit = false
    var language = "en-Us"

    fun getGenres() {
        viewModelScope.launch {
            getGenresUseCase(language).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            genreList = arrayListOf(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        var list = ArrayList<Genre>()
                        result.data!!.genres.forEach {
                            list.add(it)
                        }
                        _state.value = state.value.copy(
                            genreList = list,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            genreList = arrayListOf(),
                            isLoading = false
                        )

                    }
                }
            }.launchIn(this)
        }
    }
}