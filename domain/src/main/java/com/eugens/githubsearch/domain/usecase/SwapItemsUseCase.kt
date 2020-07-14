package com.eugens.githubsearch.domain.usecase

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.repository.GithubSearchRepository
import com.eugens.githubsearch.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Observable

class SwapItemsUseCase(
    private val githubSearchRepository: GithubSearchRepository,
    schedulersProvider: SchedulersProvider
) :
    UseCase<Boolean, Pair<Repository, Repository>>(schedulersProvider) {
    override fun createObservableUseCase(params: Pair<Repository, Repository>?): Observable<Boolean> {
        return githubSearchRepository.swapItems(params?.first, params?.second)
    }
}