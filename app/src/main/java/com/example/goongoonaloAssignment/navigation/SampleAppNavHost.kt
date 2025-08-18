package com.example.goongoonaloAssignment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.goongoonaloAssignment.SampleAppState
import com.example.goongoonaloAssignment.movieDetails.movieDetailsScreen
import com.example.goongoonaloAssignment.movieDetails.navigateToMovieDetailsScreen
import com.example.goongoonaloAssignment.moviesList.moviesListScreen

@Composable
fun SampleAppNavHost(
    appState: SampleAppState,
    startDestination: Any,
    modifier: Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        moviesListScreen(
            navController::navigateToMovieDetailsScreen,
        ) {
            movieDetailsScreen()
        }

    }
}