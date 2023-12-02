package mohammad.toriq.mymovies.presentation.screen.movies

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.domain.usecase.GetDiscoverMoviesUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase
) : ViewModel()  {

    private val _state = mutableStateOf(MoviesUiState())
    val state: State<MoviesUiState> = _state

    var page = 1
    var isRefresh = false
    var isMax = false
    var isInit = false
    var genre:String ?= null

    fun getMovies() {
        viewModelScope.launch {
            getDiscoverMoviesUseCase(page = page,withGenres = genre,"en-Us").onEach { result ->
                var list = state.value.movieList
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            movieList = list,
                            isLoading = true,
                            errorMessage = ""
                        )
                    }
                    is Resource.Success -> {
                        if(page == 1){
                            isInit = true
                        }
                        if(result.data?.movies !=null){
                            result.data.movies.forEach {
                                list.add(it)
                            }
                        }
                        if(page < result.data!!.totalPages){
                            page++
                        }else{
                            isMax = true
                        }
                        Log.d("OkCheck","page next:"+page.toString())
                        _state.value = state.value.copy(
                            movieList = list,
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            movieList = list,
                            isLoading = false,
                            errorMessage = "Failed Connected To API Server"
                        )
                        isMax = true

                    }
                }
            }.launchIn(this)
        }
    }

    fun loadMore(){
        Log.d("OkCheck","loadMore:"+page.toString())
        getMovies()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("OkCheck","refresh")
            isRefresh = true
            isMax = false
            page = 1
            _state.value = state.value.copy(
                movieList = ArrayList(),
                isLoading = false,
                errorMessage = ""
            )
            getMovies()
            isRefresh = false
        }

    }
}