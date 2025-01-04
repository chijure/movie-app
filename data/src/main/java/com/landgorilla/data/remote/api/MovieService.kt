package com.landgorilla.data.remote.api

import com.landgorilla.data.remote.model.response.MovieResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun fetchMovieList(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "es-ES",
    ): ApiResponse<MovieResponse>

    @GET("search/movie")
    suspend fun searchMovieList(
        @Query("query") query: String
    ): ApiResponse<MovieResponse>

}