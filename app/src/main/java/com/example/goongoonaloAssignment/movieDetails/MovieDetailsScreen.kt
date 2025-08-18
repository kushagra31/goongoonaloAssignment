package com.example.goongoonaloAssignment.movieDetails

import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import com.example.customviews.ConfigurableText
import com.example.goongoonaloAssignment.movieDetails.CustomNavType.MovieDetailsScreenResponseType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Serializable
data class MoviesDetailsRoute(
    val movieDetails: MovieDetailsScreenResponse
)

fun NavController.navigateToMovieDetailsScreen(
    movieDetails: MovieDetailsScreenResponse,
    navOptions: NavOptions? = null
) =
    navigate(MoviesDetailsRoute(movieDetails), navOptions)

fun NavGraphBuilder.movieDetailsScreen() {
    composable<MoviesDetailsRoute>(
        typeMap = mapOf(typeOf<MovieDetailsScreenResponse>() to MovieDetailsScreenResponseType)
    ) { entry ->
        val movieDetail = entry.toRoute<MoviesDetailsRoute>().movieDetails
        MovieDetailsScreen(
            movieDetail = movieDetail
        )
    }
}

@Composable
private fun MovieDetailsScreen(
    movieDetail: MovieDetailsScreenResponse
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        val title = movieDetail.title
        val posterPath = movieDetail.posterPath
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AsyncImage(
                posterPath,
                "",
                modifier = Modifier.padding(end = 8.dp)
            )
            ConfigurableText(
                "$title",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

        }
    }
}

@Serializable
data class MovieDetailsScreenResponse(
    val order: Int,
    val title: String? = null,
    val posterPath: String? = null
)
object CustomNavType {

    val MovieDetailsScreenResponseType = object :
            NavType<MovieDetailsScreenResponse>(isNullableAllowed = false)

    {
        override fun get(bundle: Bundle, key: String): MovieDetailsScreenResponse? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): MovieDetailsScreenResponse {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: MovieDetailsScreenResponse): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: MovieDetailsScreenResponse) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}