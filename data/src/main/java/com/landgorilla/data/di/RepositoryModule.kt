package com.landgorilla.data.di

import com.landgorilla.data.repository.MovieListRepositoryImpl
import com.landgorilla.data.repository.MovieSearchRepositoryImpl
import com.landgorilla.domain.repository.MovieListRepository
import com.landgorilla.domain.repository.MovieSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        repository: MovieListRepositoryImpl
    ): MovieListRepository

    @Binds
    @Singleton
    abstract fun bindMovieSearchRepository(
        repository: MovieSearchRepositoryImpl
    ): MovieSearchRepository
}