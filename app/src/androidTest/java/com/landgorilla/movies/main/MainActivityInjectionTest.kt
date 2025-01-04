package com.landgorilla.movies.main

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.landgorilla.core_test.TestUtils.setupScenario
import com.landgorilla.movies.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityInjectionTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun verifyViewModelAndBindingInitialization() {
        val scenario = setupScenario<MainActivity>(
            lifecycleState = Lifecycle.State.CREATED
        )
        scenario.use { verifyActivityState(it) }
    }

    private fun verifyActivityState(scenario: androidx.test.core.app.ActivityScenario<MainActivity>) {
        scenario.onActivity { activity ->
            assertThat(activity.viewModel).isNotNull()
            assertThat(activity.binding.adapter).isNotNull()
            assertThat(activity.binding.lifecycleOwner).isEqualTo(activity)
        }
    }
}
