package com.example.goongoonaloAssignment.navigation

import com.example.goongoonaloAssignment.movieDetails.MoviesDetailsRoute
import com.example.goongoonaloAssignment.moviesList.MoviesListRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val titleText: String,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    MOVIE_LIST(
        titleText = "Movie List",
        route = MoviesListRoute::class,
        baseRoute = MoviesListRoute::class,
    ),
    MOVIE_DETAIL(
        titleText = "Movie Detail",
        route = MoviesDetailsRoute::class,
        baseRoute = MoviesListRoute::class,
    ),
}