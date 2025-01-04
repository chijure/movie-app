package com.landgorilla.core_test

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider

object TestUtils {
    inline fun <reified T : androidx.activity.ComponentActivity> setupScenario(
        intentConfig: Intent.() -> Unit = {},
        lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED
    ): ActivityScenario<T> {
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), T::class.java).apply(intentConfig)
        return ActivityScenario.launch<T>(intent).apply {
            moveToState(lifecycleState)
        }
    }
}