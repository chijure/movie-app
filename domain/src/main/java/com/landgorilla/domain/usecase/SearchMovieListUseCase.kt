package com.landgorilla.domain.usecase

import com.landgorilla.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import javax.inject.Inject

class SearchMovieListUseCase @Inject constructor(
    private val repository: MovieSearchRepository
) {
    operator fun invoke(search: String) = repository.searchMovies(search)
        .catch { exception ->
            Timber.e(exception, "Error fetching Movie list")
        }
}