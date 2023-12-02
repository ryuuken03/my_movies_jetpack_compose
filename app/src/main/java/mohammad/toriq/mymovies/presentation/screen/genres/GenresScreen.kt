@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mymovies.presentation.screen.genres

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mohammad.toriq.mymovies.R
import mohammad.toriq.mymovies.presentation.navigation.Screen

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
@Composable
fun GenresScreen (
    activity: ComponentActivity,
    navController: NavController,
    genreViewModel: GenresViewModel = hiltViewModel()
){
    if(!genreViewModel.isInit){
        genreViewModel.isInit = true
        genreViewModel.getGenres()
    }
    initGenres(
        activity,
        navController,
        genreViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun initGenres(
    activity: ComponentActivity,
    navController: NavController,
    genreViewModel: GenresViewModel,
    modifier: Modifier = Modifier) {
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
                            text = "Genres Movie",
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                )
            }
        },
        content = {
            var isLoading = genreViewModel.state.value.isLoading
            var genres = genreViewModel.state.value.genreList
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(5.dp)
                    .fillMaxSize()) {

                if(!isLoading){
                    if(genres.size == 0){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Genre not Found",
                                color = colorResource(id = R.color.primary),
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    else{

                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(128.dp),
                        ) {
                            items(genres.size) { index ->
                                OutlinedButton(
                                    modifier = Modifier.padding(all = 5.dp),
                                    border = BorderStroke(1.dp, colorResource(id = R.color.primary)),
                                    shape = RoundedCornerShape(4),
                                    onClick = fun(){
                                        Log.d("OKCheck","genre id:"+genres[index].id.toString())
                                        navController.navigate(
                                            Screen.MoviesScreen
                                                .sendData(genres[index].id.toString(),genres[index].name!!)
                                        )
                                    }
                                ){
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp)
                                        ,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ){
                                        Text(
                                            text = genres[index].name!!,
                                            fontSize = 14.sp,
                                            color = colorResource(id = R.color.primary),
                                            textAlign = TextAlign.Center,
                                        )
                                        Icon(
                                            Icons.Filled.ArrowForward,
                                            modifier = Modifier.size(20.dp),
                                            tint = colorResource(id = R.color.primary),
                                            contentDescription = "",
                                        )
                                    }
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