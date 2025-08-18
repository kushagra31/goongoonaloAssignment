package com.example.model

data class MovieDetailResponse(
    val order: Int,
    val title: String? = null,
    val posterPath: String? = null,
)