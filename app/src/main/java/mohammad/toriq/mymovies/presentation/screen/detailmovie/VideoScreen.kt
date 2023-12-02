@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mymovies.presentation.screen.detailmovie

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/***
 * Created By Mohammad Toriq on 30/11/2023
 */

@Composable
fun LockScreenOrientation(activity: ComponentActivity,
                          orientation: Int) {
    DisposableEffect(orientation) {
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun VideoScreen (
    activity: ComponentActivity,
    navController: NavController,
    id: String,
    title: String
){
    val systemUiController: SystemUiController = rememberSystemUiController()

    systemUiController.isStatusBarVisible = false // Status bar
    systemUiController.isNavigationBarVisible = false // Navigation bar
    systemUiController.isSystemBarsVisible = false // Status & Navigation bars

    LockScreenOrientation(activity = activity, orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .background(color = Color.Black)
                    .fillMaxSize()) {
                AndroidView(
                    modifier = Modifier.padding(50.dp),
                    factory = {
                        var view = YouTubePlayerView(it)
                        view.addYouTubePlayerListener(
                            object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    super.onReady(youTubePlayer)
                                    youTubePlayer.loadVideo(id, 0f)
                                }
                            }
                        )
                        view
                })
            }
        }
    )
}
