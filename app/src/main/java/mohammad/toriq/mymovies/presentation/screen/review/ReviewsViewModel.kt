package mohammad.toriq.mymovies.presentation.screen.review

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
import mohammad.toriq.mymovies.domain.usecase.GetDetailMovieUseCase
import mohammad.toriq.mymovies.domain.usecase.GetMovieReviewsUseCase
import mohammad.toriq.mymovies.domain.usecase.GetMovieVideosUseCase
import mohammad.toriq.mymovies.models.Video
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 01/12/2023
 */
@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel()  {


    private val _state = mutableStateOf(ReviewsUiState())
    val state: State<ReviewsUiState> = _state

    var id : Long = 0
    var page = 1
    var isRefresh = false
    var isMax = false
    var isInit = false

    fun getReviews() {
        viewModelScope.launch {
            getMovieReviewsUseCase(id,page).onEach { result ->
                var list = state.value.reviewList
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            reviewList = list,
                            isLoading = true,
                            errorMessage = ""
                        )
                    }
                    is Resource.Success -> {
                        if(page == 1){
                            isInit = true
                        }
                        if(result.data?.reviews !=null){
                            result.data.reviews.forEach {
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
                            reviewList = list,
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            reviewList = list,
                            isLoading = false,
                            errorMessage = "Failed Connected To API Server"
                        )

                    }
                }
            }.launchIn(this)
        }
    }
    fun loadMore(){
        Log.d("OkCheck","loadMore:"+page.toString())
        getReviews()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("OkCheck","refresh")
            isRefresh = true
            isMax = false
            page = 1
            _state.value = state.value.copy(
                reviewList = ArrayList(),
                isLoading = false,
                errorMessage = ""
            )
            getReviews()
            isRefresh = false
        }

    }
}