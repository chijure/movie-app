package com.landgorilla.movies.main

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.landgorilla.core_test.MockUtil
import com.landgorilla.core_test.TestUtils.setupScenario
import com.landgorilla.domain.repository.MovieListRepository
import com.landgorilla.domain.usecase.FetchMovieListUseCase
import com.landgorilla.movies.R
import com.landgorilla.movies.ui.main.MainActivity
import com.landgorilla.movies.ui.main.MovieAdapter
import com.landgorilla.movies.utils.EspressoIdlingResource
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.awaitility.kotlin.await
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityInteractionTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    val MovieListRepository: MovieListRepository = mockk()

    @BindValue
    val fetchMovieListUseCase: FetchMovieListUseCase = FetchMovieListUseCase(MovieListRepository)

    private val mockMovieList = MockUtil.generateMockMovieList(50) {
        this.copy(name = "movie", url = "https://image.tmdb.org/t/p/w500")
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        Intents.init()
        setupMockRepositories()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
        Intents.release()
    }

    @Test
    fun clickOnMovieItem_navigatesToDetailActivity() {
        // TODO
    }

    @Test
    fun scrollToLastItem_displaysLastItemInView() {
        val scenario = setupScenario<MainActivity>(lifecycleState = Lifecycle.State.RESUMED)
        scenario.use {
            onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<MovieAdapter.MovieViewHolder>(49))
            onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun infiniteScroll_loadsAdditionalData() {
        coEvery { MovieListRepository.fetchMovieList(2) } returns flowOf(
            MockUtil.generateMockMovieList(
                50,
                startIndex = 51
            )
        )

        val scenario = setupScenario<MainActivity>(lifecycleState = Lifecycle.State.RESUMED)
        scenario.use {
            onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<MovieAdapter.MovieViewHolder>(49))
            await.atMost(10, TimeUnit.SECONDS).untilAsserted {
                onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
            }
        }
    }

    private fun setupMockRepositories() {
        coEvery { MovieListRepository.fetchMovieList(any()) } returns flowOf(mockMovieList)
    }
}
