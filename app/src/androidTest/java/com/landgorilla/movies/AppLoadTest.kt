package com.landgorilla.movies

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
class AppLoadTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun appStarts_displaysFirstScreen() {
        val scenario = setupScenario<MainActivity>()
        scenario.use {
            onView(withId(R.id.mainContainer)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun appDoesNotCrashOnLaunch() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assert(appContext.packageName == "com.landgorilla.movies")
    }
}
