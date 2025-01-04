package com.landgorilla.core_test

import timber.log.Timber

class TestTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        println("Timber Log: [$tag] $message")
    }
}
