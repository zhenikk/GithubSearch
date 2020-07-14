package com.eugens.githubsearch.domain.usecase.base

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver


abstract class UseCase<T, Params>(private val schedulersProvider: SchedulersProvider) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun execute(disposableObserver: DisposableObserver<T>?) {
        execute(disposableObserver, null)
    }


    fun execute(disposableObserver: DisposableObserver<T>?, params: Params?) {
        requireNotNull(disposableObserver) { "disposableObserver must not be null" }
        val observable =
            this.createObservableUseCase(params).subscribeOn(schedulersProvider.backgroundThread())
                .observeOn(schedulersProvider.uiThread())

        val observer = observable.subscribeWith(disposableObserver)
        compositeDisposable.add(observer)
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    protected abstract fun createObservableUseCase(params: Params?): Observable<T>
}

