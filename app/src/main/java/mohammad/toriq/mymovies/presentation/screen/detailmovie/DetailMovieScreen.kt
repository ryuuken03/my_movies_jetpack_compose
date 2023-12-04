@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mymovies.presentation.screen.detailmovie

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import mohammad.toriq.mymovies.R
import mohammad.toriq.mymovies.data.source.remote.dto.APIService
import mohammad.toriq.mymovies.presentation.navigation.Screen
import mohammad.toriq.mymovies.util.Constants
import mohammad.toriq.mymovies.util.Util
import mohammad.toriq.mymovies.models.Video
import java.util.UUID

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
@Composable
fun DetailMovieScreen (
    activity: ComponentActivity,
    navController: NavController,
    id: Long,
    detailMovieViewModel: DetailMovieViewModel = hiltViewModel()
){

    Log.d("OKCheck","id:"+id.toString())
    if(!detailMovieViewModel.isInit){
        detailMovieViewModel.isInit = true
        detailMovieViewModel.id = id
        detailMovieViewModel.getDetailMovie()
        detailMovieViewModel.getDetailMovieVideos()
    }
    initDetailMovie(
        activity,
        navController,
        detailMovieViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun initDetailMovie(
    activity: ComponentActivity,
    navController: NavController,
    detailMovieViewModel: DetailMovieViewModel,
    modifier: Modifier = Modifier) {

    BackHandler {
        navController.navigateUp()
    }
    val showDialog =  remember { mutableStateOf(false) }

    var isLoading = detailMovieViewModel.state.value.isLoading
    var movie = detailMovieViewModel.state.value.movie
    var video = detailMovieViewModel.stateVideo.value.movieVideo

    if(showDialog.value){
        showDetailMovieVideo(activity = activity, video = video!!,
            navController = navController, showDialog)
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
                            text = "Detail Movie",
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
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()) {
//
                if(!isLoading){
                    if(movie != null) {
                        Column(
                            modifier = Modifier,
                        ) {
                            Row (
                                modifier = Modifier
                                    .background(color = colorResource(id = R.color.primary2))
                            ){
                                AsyncImage(
                                    model = APIService.IMAGE_BASE_URL + movie.imageUrl,
                                    contentDescription = null,
                                    alignment = Alignment.Center,
                                    modifier = Modifier.weight(1f)
                                )
                                Column (
                                    modifier = Modifier
                                        .weight(2f)
                                        .padding(5.dp)
                                ){
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 5.dp),
                                        text = movie.title!!,
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.white),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    var genre = ""
                                    movie.genres.forEach {
                                        var isComma = true
                                        if(genre.equals("")){
                                            isComma = false
                                            genre = "Genre : "
                                        }
                                        if(isComma){
                                            genre += ", "
                                        }
                                        genre += it.name

                                    }
                                    if(!genre.equals("")){
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp),
                                            text = genre,
                                            fontSize = 13.sp,
                                            color = colorResource(id = R.color.white),
                                        )
                                    }
                                    if(movie.releaseDate != null){
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp),
                                            text = "Release : "+Util.convertDate(movie.releaseDate!!,
                                                Constants.DATE_OUT_FORMAT_DEF2,
                                                Constants.DATE_OUT_FORMAT_DEF4),
                                            fontSize = 13.sp,
                                            color = colorResource(id = R.color.white),
                                        )
                                    }
                                    if(movie.runtime > 0){
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp),
                                            text = "Durasi : "+Util.getShowTime(movie.runtime),
                                            fontSize = 13.sp,
                                            color = colorResource(id = R.color.white),
                                        )
                                    }
                                    if(movie.voteAverage > 0){
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp),
                                            text = "Rating : "+movie.voteAverage,
                                            fontSize = 13.sp,
                                            color = colorResource(id = R.color.white),
                                        )
                                    }
                                }
                            }

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 15.dp,
                                        start = 10.dp,
                                        end = 10.dp
                                    ),
                                text = "Description",
                                fontSize = 18.sp,
                                color = colorResource(id = R.color.primary),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 10.dp),
                                text = movie.desc!!,
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.grey),
                            )
                            Row (

                            ){
                                if(video!= null){
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .padding(5.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(id = R.color.primary2)
                                        ),
                                        shape = RoundedCornerShape(10),
                                        onClick = {
                                            showDialog.value = true
                                        },
                                        contentPadding = PaddingValues()
                                    ) {

                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            text = "Show "+video.type!!,
                                            fontSize = 14.sp,
                                            color = colorResource(id = R.color.white),
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                OutlinedButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(5.dp),
                                    shape = RoundedCornerShape(10),
                                    border = BorderStroke(1.dp, colorResource(id = R.color.primary2)),
                                    onClick = {
                                        navController.navigate(
                                            Screen.ReviewsScreen
                                                .sendData(movie.id.toString(), movie.title!!)
                                        )
                                    },
                                    contentPadding = PaddingValues()
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        text = "Reviews",
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.primary2),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                        }
                    }else{
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Detail Movie not Found",
                                color = colorResource(id = R.color.primary),
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
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
private var isFullscreen = false

@Composable
fun showDetailMovieVideo(
    activity: ComponentActivity,
    video: Video,
    navController: NavController,
    openDialogCustom: MutableState<Boolean>) {

    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        AndroidView(factory = {
            var view = YouTubePlayerView(it)
            var fullView = FrameLayout(it)

            var youTubePlayerHome: YouTubePlayer?= null

            val iFramePlayerOptions = IFramePlayerOptions.Builder()
                .controls(1)
                .fullscreen(1)
                .build()

            view.enableAutomaticInitialization = false

            isFullscreen = false

            view.addFullscreenListener(object : FullscreenListener {
                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                    isFullscreen = true
                    openDialogCustom.value = false
                    navController.navigate(
                        Screen.VideoScreen
                            .sendData(video.key!!, "Video "+video.type!!)
                    )
                }

                override fun onExitFullscreen() {
                    isFullscreen = false
                }
            })
            view.initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayerHome = youTubePlayer
                    super.onReady(youTubePlayer)
                    youTubePlayer.loadVideo(video.key!!, 0f)
                }
            }, iFramePlayerOptions)


            activity.onBackPressedDispatcher.addCallback(
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (isFullscreen) {
                            if(youTubePlayerHome!=null){
                                youTubePlayerHome!!.toggleFullscreen()
                            }
                        }
                    }
                }
            )
            activity.lifecycle.addObserver(view)
            view
        })
    }

}