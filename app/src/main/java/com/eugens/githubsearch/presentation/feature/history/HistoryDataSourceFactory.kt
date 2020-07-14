package com.eugens.githubsearch.presentation.feature.history

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.usecase.GetHistoryUseCase
import com.eugens.githubsearch.presentation.base.Event
import com.eugens.githubsearch.presentation.utils.extensions.getKoinInstance

class HistoryDataSourceFactory(private val isLoading: MutableLiveData<Event<Boolean>>) :
    DataSource.Factory<Int, Repository>() {
    private var historyUseCase: GetHistoryUseCase? = null

    override fun create(): HistoryPositionalDataSource {
        historyUseCase = getKoinInstance<GetHistoryUseCase>()
        return HistoryPositionalDataSource(historyUseCase, isLoading)
    }
}
