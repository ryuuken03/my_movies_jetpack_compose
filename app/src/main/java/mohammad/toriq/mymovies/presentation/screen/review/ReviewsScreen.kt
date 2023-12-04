@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mymovies.presentation.screen.review

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import mohammad.toriq.mymovies.util.NoRippleInteractionSource
import mohammad.toriq.mymovies.util.Util

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
@Composable
fun ReviewsScreen (
    activity: ComponentActivity,
    navController: NavController,
    id: Long,
    title: String,
    reviewsViewModel: ReviewsViewModel = hiltViewModel()
){

    Log.d("OKCheck","id:"+id.toString())
    if(!reviewsViewModel.isInit){
        reviewsViewModel.isInit = true
        reviewsViewModel.id = id
        reviewsViewModel.getReviews()
    }
    initReviews(
        activity,
        navController,
        title,
        reviewsViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun initReviews(
    activity: ComponentActivity,
    navController: NavController,
    title: String,
    reviewsViewModel: ReviewsViewModel,
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
                            text = "Reviews Movie",
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
            var isLoading = reviewsViewModel.state.value.isLoading
            var reviews = reviewsViewModel.state.value.reviewList
            var isRefreshing = reviewsViewModel.isRefresh
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(5.dp)
                    .fillMaxSize()) {
//
                if(!isLoading || reviews.size > 0){
                    val listState = rememberLazyListState()
                    SwipeRefresh(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        onRefresh = {
                            reviewsViewModel.refresh()
                        }
                    ) {
                        if(reviews.size == 0){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "Reviews not Found",
                                    color = colorResource(id = R.color.primary),
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }else{
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                state = listState,
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(reviews.size) { index ->
                                    var review = reviews[index]
                                    var isExpanded by remember { mutableStateOf(false) }
                                    OutlinedButton(
                                        modifier = Modifier
                                            .padding(all = 5.dp),
                                        interactionSource = remember { NoRippleInteractionSource() },
                                        contentPadding = PaddingValues(),
                                        border = BorderStroke(1.dp, colorResource(id = R.color.primary)),
                                        shape = RoundedCornerShape(4.dp),
                                        onClick = fun(){
                                            isExpanded = !isExpanded
                                        }
                                    ){
                                        Column(
                                            modifier = Modifier
                                                .padding(all = 5.dp)
                                                .fillMaxWidth()
                                        ){
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ){
                                                if(review.authorDetails.avatarPath!=null){
                                                    AsyncImage(
                                                        model = APIService.IMAGE_BASE_URL+review.authorDetails.avatarPath!!,
                                                        contentDescription = null,
                                                        contentScale = ContentScale.Crop,
                                                        alignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .clip(CircleShape)
                                                            .size(35.dp)
                                                    )
                                                }else{
                                                    var color = colorResource(id = R.color.grey2)
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(15.dp)
                                                            .drawBehind {
                                                                drawCircle(
                                                                    color = color,
                                                                    radius = this.size.maxDimension
                                                                )
                                                            },
                                                        text = review.author!!.substring(0,1),
                                                    )
//                                                    Spacer(modifier = Modifier.width(5.dp))
                                                }
                                                Column {
                                                    Text(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(
                                                                top = 5.dp,
                                                                start = 5.dp,
                                                                end = 5.dp
                                                            ),
                                                        text = review.author!!,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = colorResource(id = R.color.primary),
                                                    )
                                                    Text(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(
                                                                top = 5.dp,
                                                                start = 5.dp,
                                                                end = 5.dp
                                                            ),
                                                        text = "on "+Util.convertDate(
                                                            review.createdAt!!,
                                                            Constants.DATE_OUT_FORMAT_DEF1,
                                                            Constants.DATE_OUT_FORMAT_DEF4),
                                                        fontSize = 12.sp,
                                                        color = colorResource(id = R.color.grey),
                                                    )
                                                }
                                            }
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(5.dp),
                                                text = review.content!!,
                                                fontSize = 12.sp,
                                                color = colorResource(id = R.color.primary),
                                                maxLines = if(isExpanded) Int.MAX_VALUE else 4,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 10.dp, horizontal = 5.dp),
                                                text = if(isExpanded) "Sembunyikan" else "Selengkapnya",
                                                fontSize = 12.sp,
                                                textAlign = TextAlign.End,
                                                color = colorResource(id = R.color.blue1),
                                            )
                                        }
                                    }
                                }
                            }
                            listState.OnBottomReached (buffer = 2){
                                if(!reviewsViewModel.isMax){
                                    reviewsViewModel.loadMore()
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
fun LazyListState.OnBottomReached(
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