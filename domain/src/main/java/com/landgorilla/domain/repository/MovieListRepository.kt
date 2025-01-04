package com.landgorilla.domain.repository

import androidx.annotation.WorkerThread
import com.landgorilla.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Repository for fetching movie list
 */
interface MovieListRepository {
    @WorkerThread
    fun fetchMovieList(page: Int): Flow<List<Movie>>
}