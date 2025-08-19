package com.example.goongoonaloAssignment.moviesList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil3.compose.SubcomposeAsyncImage
import com.example.customviews.ConfigurableText
import com.example.goongoonaloAssignment.movieDetails.MovieDetailsScreenResponse
import com.example.model.ResultResponse
import kotlinx.serialization.Serializable

@Serializable
data object MoviesListRoute

fun NavController.navigateToMoviesListScreen(navOptions: NavOptions? = null) =
    navigate(MoviesListRoute, navOptions)

fun NavGraphBuilder.moviesListScreen(
    onMovieClick: (MovieDetailsScreenResponse) -> Unit,
    movieClickDestination: NavGraphBuilder.() -> Unit
) {
    composable<MoviesListRoute> {
        MoviesListScreen(onMovieClick = onMovieClick)
    }
    movieClickDestination()
}

@Composable
private fun MoviesListScreen(
    onMovieClick: (MovieDetailsScreenResponse) -> Unit,
    moviesViewModel: MoviesViewModel = hiltViewModel<MoviesViewModel, MoviesViewModel.Factory> { factory ->
        factory.create(0)
    }
) {
    val movies = moviesViewModel.moviesList.collectAsState()
    val movieDetailsState = movies.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (movies.value.topMovies.isNotEmpty()) {
            LazyColumn {
                item {
                    MovieList("New on MyTube", movieDetailsState.newMovies) {
                        moviesViewModel.addtoContinueMovies(it.toResultResponse())
                        onMovieClick(it)
                    }
                    MovieList("Top 10 in india today", movieDetailsState.topMovies) {
                        moviesViewModel.addtoContinueMovies(it.toResultResponse())
                        onMovieClick(it)
                    }
                    MovieList("Continue watching", movieDetailsState.continueMovies) {
                        moviesViewModel.addtoContinueMovies(it.toResultResponse())
                        onMovieClick(it)
                    }
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun MovieList(
    title: String,
    movieList: ArrayList<ResultResponse>,
    onMovieClick: (MovieDetailsScreenResponse) -> Unit
) {
    Column {
        ConfigurableText(
            title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        LazyRow{
            items(count = movieList.size) { index ->
                val usersData = movieList[index]
                usersData?.let {
                    val title = it.title ?: ""
                    val posterPath = it.posterPath ?: ""

                    Box(
                        modifier = Modifier
                            .padding(
                                16.dp
                            )
                            .clickable {
                                onMovieClick(usersData.asExternalModel())
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SubcomposeAsyncImage(
                                posterPath,
                                "",
                                modifier = Modifier
                                    .size(height = 154.dp, width = 108.dp)
                                    .padding(end = 8.dp),
                                loading = { CircularProgressIndicator(Modifier.size(16.dp)) }
                            )
                            ConfigurableText(
                                title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

fun ResultResponse.asExternalModel() = MovieDetailsScreenResponse(
    order = order,
    title = title ?: "",
    posterPath = posterPath ?: ""
)

fun MovieDetailsScreenResponse.toResultResponse() = ResultResponse(
    order, title, posterPath
)