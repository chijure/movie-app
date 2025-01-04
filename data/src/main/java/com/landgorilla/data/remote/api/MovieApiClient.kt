package com.landgorilla.data.remote.api

import com.landgorilla.data.remote.model.response.MovieResponse
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class MovieApiClient @Inject constructor(
    private val movieService: MovieService
) {
    suspend fun fetchMovieList(page: Int): ApiResponse<MovieResponse> {
        return movieService.fetchMovieList(page)
    }

    suspend fun searchMovieList(query: String): ApiResponse<MovieResponse> {
        return movieService.searchMovieList(query)
    }
}