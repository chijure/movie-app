package com.landgorilla.domain

import com.landgorilla.core_test.MockUtil
import com.landgorilla.domain.repository.MovieListRepository
import com.landgorilla.domain.usecase.FetchMovieListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test

class FetchMovieListUseCaseTest {
    private lateinit var useCase: FetchMovieListUseCase
    private val repository: MovieListRepository = mockk()

    @Before
    fun setup() {
        useCase = FetchMovieListUseCase(repository)
    }

    @Test
    fun `fetchMovieList returns list`() {
        // Given
        val expectedList = listOf(MockUtil.mockMovie())
        coEvery { repository.fetchMovieList(any()) } returns flow { emit(expectedList) }

        // When
        val result = useCase(1)

        // Then
        assertEquals(expectedList, result)
    }
}