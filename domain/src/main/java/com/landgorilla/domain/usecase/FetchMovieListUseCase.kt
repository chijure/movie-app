package com.landgorilla.domain.usecase

import com.landgorilla.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import javax.inject.Inject

class FetchMovieListUseCase @Inject constructor(
    private val repository: MovieListRepository
) {
    operator fun invoke(page: Int) = repository.fetchMovieList(page)
        .catch { exception ->
            Timber.e(exception, "Error fetching Movie list")
        }
}