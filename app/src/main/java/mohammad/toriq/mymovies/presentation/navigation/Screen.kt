package mohammad.toriq.mymovies.presentation.navigation

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object GenresScreen: Screen("genres_screen")
    object MoviesScreen: Screen("movies_screen/{id}/{title}"){
        fun sendData(id: String,title: String) = "movies_screen/$id/$title"
    }
    object DetailMovieScreen: Screen("detail_movie_screen/{id}"){
        fun sendData(id: String) = "detail_movie_screen/$id"
    }
    object ReviewsScreen: Screen("reviews_screen/{id}/{title}"){
        fun sendData(id: String, title:String) = "reviews_screen/$id/$title"
    }
    object VideoScreen: Screen("video_screen/{id}/{title}"){
        fun sendData(id: String, title:String) = "video_screen/$id/$title"
    }
}