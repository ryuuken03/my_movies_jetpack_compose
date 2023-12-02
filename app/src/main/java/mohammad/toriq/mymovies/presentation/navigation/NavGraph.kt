package mohammad.toriq.mymovies.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mohammad.toriq.mymovies.presentation.screen.detailmovie.DetailMovieScreen
import mohammad.toriq.mymovies.presentation.screen.detailmovie.VideoScreen
import mohammad.toriq.mymovies.presentation.screen.genres.GenresScreen
import mohammad.toriq.mymovies.presentation.screen.movies.MoviesScreen
import mohammad.toriq.mymovies.presentation.screen.review.ReviewsScreen
import mohammad.toriq.mymovies.presentation.screen.splash.SplashScreen

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
@Composable
fun NavGraph(activity: ComponentActivity, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(
                activity = activity,
                navController = navController
            )
        }
        composable(route = Screen.GenresScreen.route) {
            GenresScreen(
                activity = activity,
                navController = navController
            )
        }
        composable(route = Screen.MoviesScreen.route){
            var id = it.arguments?.getString("id") ?: "0"
            var title = it.arguments?.getString("title") ?: ""
            MoviesScreen(activity,navController,id.toLong(),title)
        }
        composable(route = Screen.DetailMovieScreen.route){
            var id = it.arguments?.getString("id") ?: "0"
            DetailMovieScreen(activity,navController,id.toLong())
        }
        composable(route = Screen.ReviewsScreen.route){
            var id = it.arguments?.getString("id") ?: "0"
            var title = it.arguments?.getString("title") ?: ""
            ReviewsScreen(activity,navController,id.toLong(),title)
        }
        composable(route = Screen.VideoScreen.route){
            var id = it.arguments?.getString("id") ?: ""
            var title = it.arguments?.getString("title") ?: ""
            VideoScreen(activity,navController,id,title)
        }
    }
}