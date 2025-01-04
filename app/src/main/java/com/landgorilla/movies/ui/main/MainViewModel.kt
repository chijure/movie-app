package com.landgorilla.movies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landgorilla.domain.model.Movie
import com.landgorilla.domain.usecase.FetchMovieListUseCase
import com.landgorilla.domain.usecase.SearchMovieListUseCase
import com.landgorilla.movies.utils.ERROR_LOADING_MOVIE_LIST
import com.landgorilla.movies.utils.EspressoIdlingResource
import com.landgorilla.movies.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchMoviesListUseCase: FetchMovieListUseCase,
    private val searchMoviesListUseCase: SearchMovieListUseCase
) : ViewModel() {
    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> = _movieList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _toastMessage = MutableStateFlow<Event<String>?>(null)
    val toastMessage: StateFlow<Event<String>?> = _toastMessage

    private val _isFetchFailed = MutableStateFlow(false)
    val isFetchFailed: StateFlow<Boolean> = _isFetchFailed

    private var currentPageIndex = 0

    init {
        Timber.d("MainViewModel initialized")
    }

    fun fetchNextMovieList() {
        if (_isLoading.value) return
        currentPageIndex++
        fetchMovieList(currentPageIndex)
    }

    fun searchMovies(query: String) {
        if (_isLoading.value) return
        currentPageIndex = 0
        _movieList.value = emptyList()
        searchMovieList(query)
    }

    private fun fetchMovieList(page: Int) = viewModelScope.launch {
        withIdlingResource {
            fetchMoviesListUseCase(page)
                .handleFetchResult()
        }
    }

    private fun searchMovieList(query: String) = viewModelScope.launch {
        withIdlingResource {
            searchMoviesListUseCase(query)
                .handleFetchResult()
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
        _isFetchFailed.value = !isLoading && _movieList.value.isEmpty()
    }

    private fun updateMovieList(newList: List<Movie>) {
        _movieList.value += newList
    }

    private fun handleFetchError(exception: Throwable) {
        Timber.e(exception, ERROR_LOADING_MOVIE_LIST)
        _toastMessage.value = Event(ERROR_LOADING_MOVIE_LIST)
        _isFetchFailed.value = true
    }

    private suspend fun Flow<List<Movie>>.handleFetchResult() {
        this.onStart { updateLoadingState(true) }
            .catch { handleFetchError(it) }
            .collect { updateMovieList(it) }
        updateLoadingState(false)
    }

    private inline fun withIdlingResource(block: () -> Unit) {
        EspressoIdlingResource.increment()

        try {
            block()
        } finally {
            EspressoIdlingResource.decrement()
        }
    }
}