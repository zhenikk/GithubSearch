package com.eugens.githubsearch.presentation.feature.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.usecase.SearchUseCase
import com.eugens.githubsearch.presentation.base.Event
import com.eugens.githubsearch.presentation.utils.extensions.getKoinInstance

class SearchDataSourceFactory(private val isLoading: MutableLiveData<Event<Boolean>>) :
    DataSource.Factory<Int, Repository>() {
    private var query = ""
    private var searchUseCase: SearchUseCase? = null

    override fun create(): SearchPositionalDataSource {
        searchUseCase = getKoinInstance<SearchUseCase>()
        return SearchPositionalDataSource(searchUseCase, query, isLoading)
    }

    fun cancelSearch() {
        searchUseCase?.dispose()
        query = ""
    }

    fun search(text: String) {
        searchUseCase?.dispose()
        query = text
    }
}
