package com.eugens.githubsearch.domain.usecase

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import com.eugens.githubsearch.domain.model.HistoryParams
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.repository.GithubSearchRepository
import com.eugens.githubsearch.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Observable

class GetHistoryUseCase(
    private val githubSearchRepository: GithubSearchRepository,
    schedulersProvider: SchedulersProvider
) : UseCase<List<Repository>, HistoryParams>(
    schedulersProvider
) {
    override fun createObservableUseCase(params: HistoryParams?): Observable<List<Repository>> {
        return githubSearchRepository.getHistory(params)
    }
}