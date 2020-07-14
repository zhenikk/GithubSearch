package com.eugens.githubsearch.presentation.feature.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.model.SearchParams
import com.eugens.githubsearch.domain.usecase.SearchUseCase
import com.eugens.githubsearch.presentation.base.Event
import com.eugens.githubsearch.presentation.utils.SimpleObserver

class SearchPositionalDataSource(
    private val searchUseCase: SearchUseCase?,
    private val searchRequest: String,
    private val loading: MutableLiveData<Event<Boolean>>
) :
    PositionalDataSource<Repository?>() {
    companion object {
        private const val TAG = "MyPositionalDataSource"
    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<Repository?>
    ) {
        Log.d(
            TAG,
            "loadInitial, requestedStartPosition = " + params.requestedStartPosition + ", requestedLoadSize = " + params.requestedLoadSize
        )

        if (searchRequest.isNotBlank()) {
            loading.postValue(Event(true))
            searchUseCase?.execute(object : SimpleObserver<List<Repository>>() {
                override fun onNext(t: List<Repository>) {
                    super.onNext(t)
                    callback.onResult(t, 0)
                    loading.postValue(Event(false))

                }

                override fun onError(exception: Throwable) {
                    super.onError(exception)
                    loading.postValue(Event(false))
                }

            }, SearchParams(searchRequest, 0))
        } else callback.onResult(arrayListOf(), 0)
    }

    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<Repository?>
    ) {
        Log.d(
            TAG,
            "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize
        )

        if (searchRequest.isNotBlank()) {
            loading.postValue(Event(true))
            searchUseCase?.execute(object : SimpleObserver<List<Repository>>() {
                override fun onNext(t: List<Repository>) {
                    super.onNext(t)
                    callback.onResult(t)
                    loading.postValue(Event(false))
                }

                override fun onError(exception: Throwable) {
                    super.onError(exception)
                    loading.postValue(Event(false))
                }
            }, SearchParams(searchRequest, params.startPosition))
        } else callback.onResult(arrayListOf())
    }
}
