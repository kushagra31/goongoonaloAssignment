package com.example.model

import com.example.model.models.ContinueMovieEntity
import com.example.model.models.NewMovieEntity
import com.example.model.models.TopMovieEntity
import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("TOP") val topMovies: ArrayList<ResultResponse>,
    @SerializedName("New") val newMovies: ArrayList<ResultResponse>,
    @SerializedName("Continue") val continueMovies: ArrayList<ResultResponse>
)

data class ResultResponse (
    @SerializedName("Order") val order: Int,
    @SerializedName("Title") val title: String? = null,
    @SerializedName("Thumbnail") val posterPath: String? = null
)

fun ResultResponse.toNewMovieEntity() = NewMovieEntity(
    order = order,
    title = title ?: "",
    posterPath = posterPath ?: ""
)

fun ResultResponse.toTopMovieEntity() = TopMovieEntity(
    order = order,
    title = title ?: "",
    posterPath = posterPath ?: ""
)

fun ResultResponse.toContinueMovieEntity(order: Int) = ContinueMovieEntity(
    order = order,
    title = title ?: "",
    posterPath = posterPath ?: ""
)


