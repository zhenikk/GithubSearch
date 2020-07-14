package com.eugens.githubsearch.presentation.feature.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.eugens.githubsearch.domain.model.HistoryParams
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.usecase.GetHistoryUseCase
import com.eugens.githubsearch.presentation.base.Event
import com.eugens.githubsearch.presentation.utils.SimpleObserver


class HistoryPositionalDataSource(
    private val historyUseCase: GetHistoryUseCase?,
    private val loading: MutableLiveData<Event<Boolean>>
) : PositionalDataSource<Repository?>() {

    companion object {
        private const val TAG = "HistoryPositionalDataSource"
    }
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<Repository?>
    ) {
        Log.d(
            TAG,
            "loadInitial, requestedStartPosition = " + params.requestedStartPosition + ", requestedLoadSize = " + params.requestedLoadSize
        )

        loading.postValue(Event(true))
        historyUseCase?.execute(object : SimpleObserver<List<Repository>>() {
            override fun onNext(t: List<Repository>) {
                super.onNext(t)
                callback.onResult(t, 0)
                loading.postValue(Event(false))

            }

            override fun onError(exception: Throwable) {
                super.onError(exception)
                loading.postValue(Event(false))
            }

        }, HistoryParams(0, 10))
    }

    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<Repository?>
    ) {
        Log.d(
            TAG,
            "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize
        )

        loading.postValue(Event(true))
        historyUseCase?.execute(object : SimpleObserver<List<Repository>>() {
            override fun onNext(t: List<Repository>) {
                super.onNext(t)
                callback.onResult(t)
                loading.postValue(Event(false))
            }

            override fun onError(exception: Throwable) {
                super.onError(exception)
                loading.postValue(Event(false))
            }
        }, HistoryParams(params.startPosition, 10))
    }
}