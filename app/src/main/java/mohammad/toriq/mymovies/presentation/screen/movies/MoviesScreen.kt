@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mymovies.presentation.screen.movies

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import mohammad.toriq.mymovies.R
import mohammad.toriq.mymovies.data.source.remote.dto.APIService
import mohammad.toriq.mymovies.presentation.navigation.Screen
import mohammad.toriq.mymovies.util.Constants
import mohammad.toriq.mymovies.util.Util

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
@Composable
fun MoviesScreen (
    activity: ComponentActivity,
    navController: NavController,
    id: Long,
    title: String,
    moviesViewModel: MoviesViewModel = hiltViewModel()
){
    Log.d("OKCheck","id:"+id.toString())
    if(!moviesViewModel.isInit){
        moviesViewModel.isInit = true
        moviesViewModel.genre = id.toString()
        moviesViewModel.getMovies()
    }
    initMovies(
        activity,
        navController,
        title,
        moviesViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun initMovies(
    activity: ComponentActivity,
    navController: NavController,
    title: String,
    moviesViewModel: MoviesViewModel,
    modifier: Modifier = Modifier) {
    BackHandler {
        navController.navigateUp()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Surface(
                shadowElevation = 3.dp
            ){
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = colorResource(id = R.color.primary),
                        titleContentColor = colorResource(id = R.color.white),
                    ),
                    title = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }) {
                            Image(
                                imageVector = Icons.Filled.ArrowBack,
                                colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        },
        content = {
            var isLoading = moviesViewModel.state.value.isLoading
            var movies = moviesViewModel.state.value.movieList
            var isRefreshing = moviesViewModel.isRefresh
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(5.dp)
                    .fillMaxSize()) {
//
                if(!isLoading || movies.size > 0){
                    val listState = rememberLazyGridState()
                    SwipeRefresh(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        onRefresh = {
                            moviesViewModel.refresh()
                        }
                    ) {
                        if(movies.size == 0){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "Movie not Found",
                                    color = colorResource(id = R.color.primary),
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }else{
                            var desiredItemMinHeight by remember {
                                mutableStateOf(0.dp)
                            }

                            val density = LocalDensity.current
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                columns = GridCells.Adaptive(128.dp),
                                state = listState,
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(movies.size) { index ->
                                    var movie = movies[index]
                                    OutlinedButton(
                                        modifier = Modifier
                                            .padding(all = 3.dp)
                                            .onPlaced {
                                                with(density) {
                                                    if (desiredItemMinHeight < it.size.height.toDp()) {
                                                        desiredItemMinHeight = it.size.height.toDp()
                                                    }
                                                }
                                            }
                                            .defaultMinSize(minHeight = desiredItemMinHeight)
                                        ,
                                        contentPadding = PaddingValues(),
                                        border = BorderStroke(0.dp, colorResource(id = R.color.primary)),
                                        shape = RoundedCornerShape(4),
                                        onClick = fun(){
                                        }
                                    ){
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable(
                                                    onClick = fun() {
                                                        navController.navigate(
                                                            Screen.DetailMovieScreen
                                                                .sendData(movies[index].id.toString())
                                                        )
                                                    }
                                                )
                                                .defaultMinSize(minHeight = desiredItemMinHeight),
                                        ){
                                            AsyncImage(
                                                model = APIService.IMAGE_BASE_URL+movie.imageUrl,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                alignment = Alignment.Center,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(250.dp)
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 5.dp, vertical = 10.dp),
                                                text = movie.title!!,
                                                fontSize = 14.sp,
                                                color = colorResource(id = R.color.primary),
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(5.dp),
                                                text = Util.convertDate(movie.releaseDate,
                                                    Constants.DATE_OUT_FORMAT_DEF2,
                                                    Constants.DATE_OUT_FORMAT_DEF3),
                                                fontSize = 12.sp,
                                                color = colorResource(id = R.color.grey),
                                            )
                                        }
                                    }
                                }
                            }
                            listState.OnBottomReached (buffer = 2){
                                if(!moviesViewModel.isMax){
                                    moviesViewModel.loadMore()
                                }
                            }
                        }
                    }
                }else{
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.primary)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun LazyGridState.OnBottomReached(
    buffer : Int = 0,
    loadMore : () -> Unit
){
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                return@derivedStateOf true
            lastVisibleItem.index >=  layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect { if (it) loadMore() }
    }
}