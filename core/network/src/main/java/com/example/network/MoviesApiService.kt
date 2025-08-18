package com.example.network

import com.example.model.MoviesResponse
import retrofit2.http.GET

interface MoviesApiService {
    @GET("exec")
    suspend fun getMovies(
        ): MoviesResponse
}
