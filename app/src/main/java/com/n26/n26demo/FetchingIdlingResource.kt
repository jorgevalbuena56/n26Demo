package com.n26.n26demo

import android.support.test.espresso.IdlingResource.ResourceCallback
import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean


class FetchingIdlingResource : IdlingResource, FetcherListener {
    @Volatile
    private var mCallback: ResourceCallback? = null

    private var idle = true

    // Idleness is controlled with this boolean.
    private val mIsIdleNow = AtomicBoolean(true)

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback) {
        mCallback = callback
    }

    override fun doneFetching() {
        idle = true
        mCallback?.onTransitionToIdle()
    }

    override fun beginFetching() {
        idle = false
    }
}