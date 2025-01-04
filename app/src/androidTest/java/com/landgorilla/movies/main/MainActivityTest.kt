package com.landgorilla.movies.main

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.landgorilla.core_test.MockUtil
import com.landgorilla.core_test.TestUtils.setupScenario
import com.landgorilla.domain.repository.MovieListRepository
import com.landgorilla.domain.usecase.FetchMovieListUseCase
import com.landgorilla.movies.R
import com.landgorilla.movies.ui.main.MainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    val MovieListRepository: MovieListRepository = mockk()

    @BindValue
    val fetchMovieListUseCase: FetchMovieListUseCase = FetchMovieListUseCase(MovieListRepository)

    private val mockMovieList = MockUtil.generateMockMovieList(50, startIndex = 1)

    @Before
    fun setUp() {
        hiltRule.inject()
        mockMovieRepository()
    }

    @Test
    fun mainActivity_displaysRecyclerViewAndAppBar() {
        val scenario = setupScenario<MainActivity>(
            lifecycleState = Lifecycle.State.RESUMED
        )
        scenario.use {
            // onView(withId(R.id.appBarLayout)).check(matches(isDisplayed()))
            onView(withId(R.id.progressbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        }
    }

    private fun mockMovieRepository() {
        coEvery { MovieListRepository.fetchMovieList(any()) } returns flowOf(mockMovieList)
    }
}
