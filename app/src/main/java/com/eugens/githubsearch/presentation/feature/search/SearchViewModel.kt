package com.eugens.githubsearch.presentation.feature.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.eugens.githubsearch.presentation.base.Event
import com.eugens.githubsearch.presentation.navigation.Screens
import ru.terrakok.cicerone.Router
import java.util.concurrent.Executors

class SearchViewModel() :
    ViewModel() {
    val isLoading = MutableLiveData<Event<Boolean>>()

    private var config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(30)
        .build()

    private var dataSourceFactory = SearchDataSourceFactory(isLoading)

    var pagedList = LivePagedListBuilder(dataSourceFactory, config)
        .setFetchExecutor(Executors.newSingleThreadExecutor())
        .build()


    fun search(query: String) {
        dataSourceFactory.search(query)
        pagedList.value?.dataSource?.invalidate()
    }


    fun cancelSearch() {
        isLoading.postValue(Event(false))
        dataSourceFactory.cancelSearch()
        pagedList.value?.dataSource?.invalidate()
    }
}



