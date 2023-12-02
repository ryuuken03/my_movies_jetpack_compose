@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mymovies.presentation.screen.splash

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mohammad.toriq.mymovies.R
import mohammad.toriq.mymovies.presentation.navigation.Screen

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
@Composable
fun SplashScreen (
    activity: ComponentActivity,
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel()
){
    val state = splashViewModel.state.value
    if(!state.isLoading){
//        Toast.makeText(activity,"GenreScreen",Toast.LENGTH_SHORT).show()
        splashViewModel.setState(true)
        Log.d("OkCheck","GenreScreen")
        navController.navigate(Screen.GenresScreen.route){
            popUpTo(Screen.SplashScreen.route){
                inclusive = true
            }
        }
    }
    initSplash()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun initSplash(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box() {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.primary)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment  = Alignment.CenterHorizontally,
            )
            {

                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white),
                    modifier = modifier,
                )
            }
        }
    }
}