package com.landgorilla.domain.repository

import androidx.annotation.WorkerThread
import com.landgorilla.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieSearchRepository {
    @WorkerThread
    fun searchMovies(query: String): Flow<List<Movie>>
}