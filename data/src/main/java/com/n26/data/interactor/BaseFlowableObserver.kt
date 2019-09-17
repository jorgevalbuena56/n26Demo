package com.n26.data.interactor

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * Abstract class for a UseCase that returns an instance of a [SingleObserver] base class to define
 */
open class BaseFlowableObserver<T> : SingleObserver<T> {
    override fun onSubscribe(d: Disposable) { }
    override fun onSuccess(t: T) { }
    override fun onError(exception: Throwable) { }
}