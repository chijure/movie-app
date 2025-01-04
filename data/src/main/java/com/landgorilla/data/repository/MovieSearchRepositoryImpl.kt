package com.landgorilla.data.repository

import com.landgorilla.core.utils.DispatcherProvider
import com.landgorilla.data.local.dao.MovieDao
import com.landgorilla.data.mapper.MovieMapper
import com.landgorilla.data.remote.api.ApiResponseHandler
import com.landgorilla.data.remote.api.MovieApiClient
import com.landgorilla.domain.model.Movie
import com.landgorilla.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val apiClient: MovieApiClient,
    private val movieDao: MovieDao,
    private val apiResponseHandler: ApiResponseHandler,
    private val dispatcherProvider: DispatcherProvider
) : MovieSearchRepository {
    override fun searchMovies(query: String): Flow<List<Movie>> = flow {
        val cachedMovieList = movieDao.searchMovie(query)

        if (cachedMovieList.isNotEmpty()) {
            emit(cachedMovieList.map { MovieMapper.toDomain(it) })
        } else {
            val response = apiClient.searchMovieList(query)
            apiResponseHandler.handle(response) { data ->
                val movieList = data.results.map { MovieMapper.toDomain(it, 1) }
                movieDao.insertMovieList(movieList.map { MovieMapper.toEntity(it) })
                emit(movieList)
            }
        }
    }.catch { exception ->
        throw Exception("Exception in flow: ${exception.message}")
    }.flowOn(dispatcherProvider.io)
}