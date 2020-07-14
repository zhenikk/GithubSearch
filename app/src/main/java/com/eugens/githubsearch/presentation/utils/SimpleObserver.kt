package com.eugens.githubsearch.presentation.utils

import io.reactivex.rxjava3.observers.DisposableObserver


open class SimpleObserver<T> : DisposableObserver<T>() {
    override fun onNext(t: T) {
        // no-op by default.
    }

    override fun onComplete() {
        // no-op by default.
    }

    override fun onError(exception: Throwable) {
    }
}