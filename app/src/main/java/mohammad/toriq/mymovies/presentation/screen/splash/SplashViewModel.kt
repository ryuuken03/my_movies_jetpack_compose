package mohammad.toriq.mymovies.presentation.screen.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 30/11/2023
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel()  {

    private val _state = mutableStateOf(SplashUiState())
    val state: State<SplashUiState> = _state

    init {
        loading()
    }

    fun setState(data:Boolean){
        _state.value = state.value.copy(
            isLoading = data,
        )
    }

    fun loading() {
        viewModelScope.launch {
            setState(true)
            delay(1000)
            setState(false)
        }
    }
}