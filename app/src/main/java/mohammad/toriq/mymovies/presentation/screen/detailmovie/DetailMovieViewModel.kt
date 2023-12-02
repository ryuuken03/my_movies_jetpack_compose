package mohammad.toriq.mymovies.presentation.screen.detailmovie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mohammad.toriq.mymovies.data.source.remote.Resource
import mohammad.toriq.mymovies.domain.usecase.GetDetailMovieUseCase
import mohammad.toriq.mymovies.domain.usecase.GetMovieVideosUseCase
import mohammad.toriq.mymovies.models.Video
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 01/12/2023
 */
@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase
) : ViewModel()  {


    private val _state = mutableStateOf(DetailMovieUiState())
    val state: State<DetailMovieUiState> = _state

    private val _stateVideo = mutableStateOf(DetailMovieVideoUiState())
    val stateVideo: State<DetailMovieVideoUiState> = _stateVideo

    var id : Long = 0

    var isInit = false

    fun getDetailMovie() {
        viewModelScope.launch {
            getDetailMovieUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            movie = null,
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            movie = result.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            movie = null,
                            isLoading = false
                        )

                    }
                }
            }.launchIn(this)
        }
    }

    fun getVideo(videos : List<Video>) : Video?{
        var types = listOf("Trailer","Teaser","Clip")
        var result:Video ?= null
        types.forEach {type ->
            if(result == null){
                videos.forEach { vid ->
                    if(vid.type!!.contains(type,true)){
                        result = vid
                    }
                }
            }
        }
        if(result == null){
            videos.forEach { vid ->
                if(result == null){
                    result = vid
                }
            }
        }
        return result
    }

    fun getDetailMovieVideos() {
        viewModelScope.launch {
            getMovieVideosUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _stateVideo.value = stateVideo.value.copy(
                            movieVideo = null
                        )
                    }
                    is Resource.Success -> {
                        var video = getVideo(result.data!!.videos)
                        _stateVideo.value = stateVideo.value.copy(
                            movieVideo = video,
                        )
                    }
                    is Resource.Error -> {
                        _stateVideo.value = stateVideo.value.copy(
                            movieVideo  = null
                        )

                    }
                }
            }.launchIn(this)
        }
    }
}