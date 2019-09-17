package com.n26.n26demo

import android.support.test.espresso.IdlingResource

object EspressoIdlingResource {

    private val mIdlingResource = FetchingIdlingResource()

    val idlingResource: IdlingResource
        get() = mIdlingResource

    fun setFetchBeginning() {
        mIdlingResource.beginFetching()
    }

    fun setFetchDone() {
        mIdlingResource.doneFetching()
    }
}