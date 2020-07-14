package com.eugens.githubsearch.presentation.feature.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.usecase.DeleteUseCase
import com.eugens.githubsearch.domain.usecase.MarkAsViewedUseCase
import com.eugens.githubsearch.domain.usecase.SwapItemsUseCase
import com.eugens.githubsearch.presentation.base.Event
import com.eugens.githubsearch.presentation.utils.SimpleObserver
import ru.terrakok.cicerone.Router
import java.util.concurrent.Executors

class HistoryViewModel(
    private val markAsViewedUseCase: MarkAsViewedUseCase,
    private val swapUseCase: SwapItemsUseCase,
    private val deleteUseCase: DeleteUseCase
) : ViewModel() {

    private val isLoading = MutableLiveData<Event<Boolean>>()

    private var config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()

    private var dataSourceFactory = HistoryDataSourceFactory(isLoading)

    var pagedList = LivePagedListBuilder(dataSourceFactory, config)
        .setFetchExecutor(Executors.newSingleThreadExecutor())
        .build()


    fun markAsViewed(repository: Repository) {
        markAsViewedUseCase.execute(object : SimpleObserver<Boolean>() {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                if (t) {
                    pagedList.value?.dataSource?.invalidate()
                }
            }
        }, repository)
    }

    fun swapItems(from: Repository, to: Repository) {
        swapUseCase.execute(object : SimpleObserver<Boolean>() {
        }, Pair(from, to))
    }

    fun deleteItem(repository: Repository) {
        deleteUseCase.execute(object : SimpleObserver<Boolean>() {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                pagedList.value?.dataSource?.invalidate()
            }
        }, repository)
    }

}