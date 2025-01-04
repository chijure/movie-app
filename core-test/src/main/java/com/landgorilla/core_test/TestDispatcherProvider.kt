package com.landgorilla.core_test

import com.landgorilla.core.utils.DispatcherProvider
import kotlinx.coroutines.test.StandardTestDispatcher

object TestDispatcherProvider : DispatcherProvider(
    main = StandardTestDispatcher(),
    io = StandardTestDispatcher(),
    default = StandardTestDispatcher()
)