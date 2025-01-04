package com.github.orioonyx.core_test.di

import com.landgorilla.core.di.DispatcherModule
import com.landgorilla.core.utils.DispatcherProvider
import com.landgorilla.core_test.TestDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherModule::class]
)

object TestDispatcherModule {
    @Provides
    @Singleton
    fun provideTestDispatcherProvider(): DispatcherProvider {
        return TestDispatcherProvider
    }
}
