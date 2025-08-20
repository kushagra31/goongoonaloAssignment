package com.example.model.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.ResultResponse

@Entity(tableName = "topmovies")
data class TopMovieEntity(
    @PrimaryKey
    val title: String,
    val order: Int,
    val posterPath: String,
    var lastRefreshed: Long = System.currentTimeMillis() // For cache invalidation logic
)

@Entity(tableName = "newmovies")
data class NewMovieEntity(
    @PrimaryKey
    val title: String,
    val order: Int,
    val posterPath: String,
    var lastRefreshed: Long = System.currentTimeMillis() // For cache invalidation logic
)

@Entity(tableName = "continuemovies")
data class ContinueMovieEntity(
    @PrimaryKey
    val title: String,
    val order: Int,
    val posterPath: String,
    var lastRefreshed: Long = System.currentTimeMillis() // For cache invalidation logic
)

fun NewMovieEntity.asExternalModel() = ResultResponse(
    order = order,
    title = title,
    posterPath = posterPath
)
fun TopMovieEntity.asExternalModel() = ResultResponse(
    order = order,
    title = title,
    posterPath = posterPath
)
fun ContinueMovieEntity.asExternalModel() = ResultResponse(
    order = order,
    title = title,
    posterPath = posterPath
)
