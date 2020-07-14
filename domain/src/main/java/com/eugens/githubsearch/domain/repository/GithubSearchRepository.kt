package com.eugens.githubsearch.domain.repository

import com.eugens.githubsearch.domain.model.HistoryParams
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.model.SearchParams
import io.reactivex.rxjava3.core.Observable

interface GithubSearchRepository {
    fun search(
        searchParams: SearchParams?
    ): Observable<List<Repository>>

    fun getHistory(historyParams: HistoryParams?): Observable<List<Repository>>
    fun markAsViewed(params: Repository?): Observable<Boolean>
    fun swapItems(first: Repository?, second: Repository?): Observable<Boolean>
    fun deleteItem(item: Repository?): Observable<Boolean>
}
